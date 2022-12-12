package co.joji.news.core.services;

import co.joji.news.core.config.NewsAppConfiguration;
import co.joji.news.core.constants.LogConstants;
import co.joji.news.core.models.CacheStatus;
import co.joji.news.core.models.CachedData;
import co.joji.news.core.models.IntervalType;
import co.joji.news.core.models.SearchInterval;
import co.joji.news.core.models.response.IntervalGroup;
import co.joji.news.core.models.response.SearchResponse;
import co.joji.news.core.models.request.SearchRequest;
import co.joji.news.core.repositories.newsprovider.NewsAgencyRepo;
import co.joji.news.core.repositories.newsprovider.models.News;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.directory.SearchResult;
import java.time.*;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class SearchServiceImpl implements SearchService, LogConstants {


    @Autowired
    NewsAppConfiguration configuration;

    @Autowired
    NewsAgencyRepo newsAgencyRepo;
    @Autowired
    CachingService cachingService;

    @Autowired
    Clock clock;

    public SearchResponse search(SearchRequest searchRequest) {
        logSearchStart();
        SearchResponse newsSearchResult = null;
        try {
            newsSearchResult = getNews(searchRequest);
        } catch (Exception exception) {
            logSearchError(exception);
            throw exception;
        }
        logSearchEnd();
        return newsSearchResult;

    }

    private SearchResponse getNews(SearchRequest searchRequest) {

        CacheStatus cacheStatus = CacheStatus.MISS;
        SearchResponse searchResponse = null;
        Pair<CacheStatus, SearchResponse> cacheResult = null;

        if (configuration.isOfflineMode()) {
            cacheResult = getCachedResults(searchRequest);
            cacheStatus = cacheResult.getLeft();
        }

        if (cacheStatus == CacheStatus.NOTSTALE) {
            // cache usable , use the cached data for perf boost
            logCacheUse();
            searchResponse = cacheResult.getRight();
        } else {
            // Cache data is not usable  so go for live query
            logReadLiveData();
            try {
                searchResponse = getNewsFromSource(searchRequest);
                if (configuration.isOfflineMode()) {
                    logCacheWrite();
                    // Over write only in case of stale data (offline mode only causes stale data )
                    cachingService.PutData(searchResponse, searchRequest, configuration.isOfflineMode());
                }

            } catch (Exception exception) {

                logNewsAPIError(exception);
                // if live call fails and we have cache  present even its older than allowed duration , we can present it
                logFailBackOnError();
                cacheResult = getCachedResults(searchRequest);

                if (cacheResult != null) {
                    searchResponse = cacheResult.getRight();
                }
            }

        }

        return searchResponse;
    }


    private Pair<CacheStatus, SearchResponse> getCachedResults(SearchRequest searchRequest) {

        Pair<CacheStatus, SearchResult> result = null;
        CachedData cachedData = null;
        CacheStatus cacheStatus = CacheStatus.OFF;
        SearchResponse searchResponse = null;
        cachedData = cachingService.GetData(searchRequest);
        if (cachedData == null) {
            cacheStatus = CacheStatus.MISS;
            logCacheMiss();
        } else {
            searchResponse = cachedData.getData();
            LocalDateTime cachedTime = cachedData.getCachedTime();
            LocalDateTime cacheStaleTime = LocalDateTime.now().minus(Duration.ofMinutes(configuration.getCacheLife()));
            cacheStatus = (cachedTime.isAfter(cacheStaleTime)) ? CacheStatus.NOTSTALE : CacheStatus.STALE;
        }
        return Pair.of(cacheStatus, searchResponse);
    }


    private LocalDateTime getStartDateTine() {

        Instant instant = clock.instant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        return localDateTime;

    }

    private SearchResponse getNewsFromSource(SearchRequest searchRequest) {
        News news;
        SearchResponse searchResponse;
        LocalDateTime toDate = getStartDateTine();
        LocalDateTime fromDate = calculateStartTime(searchRequest.getSearchInterval(), toDate);

        // read from live src
        news = newsAgencyRepo.SearchNews(searchRequest.getSearchParam(), fromDate, toDate);
        // map to final form
        searchResponse = MapToResponse(news, searchRequest.getSearchInterval(), toDate);
        return searchResponse;
    }

    private SearchResponse MapToResponse(News sortedNews, SearchInterval searchInterval, LocalDateTime toDateTime) {

        int totalNewsCount = sortedNews.getTotalResults();
        SearchResponse mappedResposne = new SearchResponse();
        IntervalGroup[] intervalGroups = new IntervalGroup[searchInterval.getDuration()];
        IntervalGroup intervalGroup = null;


        for (int counter = 0; counter < searchInterval.getDuration(); counter++) {
            intervalGroup = new IntervalGroup();
            intervalGroup.setIntervalType(searchInterval.getIntervalType());
            if (counter == 0) {
                intervalGroup.setEndTime(toDateTime);
            } else {
                intervalGroup.setEndTime(substractTimeperiod(toDateTime, counter, searchInterval.getIntervalType()));
            }
            intervalGroup.setStartTime(substractTimeperiod(toDateTime, counter + 1, searchInterval.getIntervalType()));

            intervalGroups[counter] = intervalGroup;
            LocalDateTime startDateTime = intervalGroup.getStartTime();
            LocalDateTime endDateTime = intervalGroup.getEndTime();
            var newsItemStream = Arrays.stream(sortedNews.getItems()).filter(newsItem -> newsItem.getPublishedAt().toLocalDateTime().isAfter(startDateTime) && newsItem.getPublishedAt().toLocalDateTime().isBefore(endDateTime))
                    .map(newsItem -> new co.joji.news.core.models.NewsItem(newsItem.getTitle(), newsItem.getUrl()));

            var newsItemList = newsItemStream.collect(Collectors.toList());
            intervalGroup.setNews(newsItemList);
            intervalGroup.setCount(newsItemList.size());

        }

        mappedResposne.setItems(intervalGroups);
        return mappedResposne;

    }


    private LocalDateTime calculateStartTime(SearchInterval searchInterval, LocalDateTime refDateTime) {

        LocalDateTime fromDateTime = substractTimeperiod(refDateTime, searchInterval.getDuration(), searchInterval.getIntervalType());

        return fromDateTime;
    }

    private LocalDateTime substractTimeperiod(LocalDateTime refDatetime, int multiplier, IntervalType intervalType) {

        LocalDateTime result = null;
        switch (intervalType) {
            case MINUTES:
                result = refDatetime.minus(Duration.ofMinutes(multiplier));
                break;
            case HOURS:
                result = refDatetime.minus(Duration.ofHours(multiplier));
                break;
            case DAYS:
                result = refDatetime.minus(Duration.ofDays(multiplier));
                break;
            case WEEKS:
                result = refDatetime.minusWeeks(multiplier);
                break;
            case MONTHS:
                result = refDatetime.minusMonths(multiplier);
                break;
            case YEARS:
                result = refDatetime.minusYears(multiplier);
                break;
        }
        return result;
    }

    private void logSearchError(Exception exception) {
        log.error(ERROR_IN_SEARCH, exception);
    }

    private void logSearchStart() {
        log.info(STARTING_SEARCH);
    }

    private void logSearchEnd() {
        log.info(ENDING_SEARCH);
    }

    private void logNewsAPIError(Exception exception) {
        log.error(NEWS_LIVE_API_FAILS, exception);
    }

    private void
    logFailBackOnError() {
        log.info(FAILING_BACKTO_CACHE_ON_ERROR);
    }

    private void logCacheWrite() {
        log.info(WRITING_CACHE);
    }

    private void logCacheUse() {
        log.info(USING_CACHE);
    }

    private void logReadLiveData() {
        log.info(READ_LIVE_DATA);
    }

    private void logCacheMiss() {
        log.info(CACHE_MISSES);
    }

}
