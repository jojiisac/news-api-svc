package co.joji.news.web.controllers;


import co.joji.news.core.config.NewsAppConfiguration;
import co.joji.news.core.models.IntervalType;
import co.joji.news.core.models.SearchInterval;
import co.joji.news.core.models.request.SearchRequest;
import co.joji.news.core.models.response.SearchResponse;
import co.joji.news.core.services.SearchService;
import co.joji.news.web.log.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/news/api/v1/")
public class SearchController implements Constants {

    @Autowired
    SearchService searchService ;
    @Autowired NewsAppConfiguration configuration;



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestParam(required = true) String q,
                                                 @RequestParam(defaultValue = StringUtils.EMPTY, required = false) String N,
                                                 @RequestParam(required = false, defaultValue = StringUtils.EMPTY) String interval) {
        ResponseEntity<SearchResponse> responseEntity = null;
        logApiStart();

        try {

            SearchResponse searchResponse = null;
            SearchRequest searchRequest = null;

            ValidateRequest(N, interval);


            searchRequest = createSearchRequest(q, N, interval);


            searchResponse = getSearchResponse(searchRequest);

            responseEntity = ResponseEntity.ok(searchResponse);


        } catch (IllegalArgumentException ex) {

            logApiError(ex);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage());
        } catch (Exception ex) {
            logApiError(ex);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
        logApiend();
        return responseEntity;
    }

    private void logApiError(Exception ex) {
        log.error(ERROR_IN_SEARCH_API,ex);
    }

    private void logApiend() {
        log.info(ENDING_SEARCH_API);
    }

    private static void logApiStart() {

        MDC.put(REQUEST_ID, String.valueOf(UUID.randomUUID()));
        log.info( STARTING_SEARCH_API);
    }

    private void ValidateRequest(String N, String interval) {

        boolean isIntervaltypeValid;

        Integer duration = 0;

        if (StringUtils.isEmpty(N) && StringUtils.isEmpty(interval)) {
            // empty values goes with default configiration
            return;
        }

        try {
            duration = Integer.parseInt (N);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("N is not a valid integer", exception);
        }
        Validate.inclusiveBetween(configuration.getInterval().getMinDuration(), configuration.getInterval().getMaxDuration(), duration);
        isIntervaltypeValid = EnumUtils.isValidEnumIgnoreCase(IntervalType.class, interval);
        if (!isIntervaltypeValid) {
            throw new IllegalArgumentException("Interval is not in valid form ");
        }
    }

    private SearchResponse getSearchResponse(SearchRequest searchRequest) {

        SearchResponse searchResponse;
        searchResponse = searchService.search(searchRequest);
        return searchResponse;
    }


    private SearchRequest createSearchRequest(String searchParam,
                                              String N,
                                              String interval) {
        SearchInterval searchInterval;
        IntervalType intervalType = IntervalType.HOURS;
        int duration;
        if (StringUtils.isEmpty(N) && StringUtils.isEmpty(interval)) {
            // empty values goes with default configiration
            intervalType = configuration.getInterval().getDefaultInterval();
            duration = configuration.getInterval().getDefaultDuration();
        } else {

            intervalType = IntervalType.valueOf(interval);
            duration = Integer.parseInt(N);
        }


        searchInterval = new SearchInterval(duration, intervalType);
        SearchRequest searchRequest = new SearchRequest(searchInterval, searchParam);
        return searchRequest;
    }
}
