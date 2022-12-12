package co.joji.news.repositories.newsapi;

import co.joji.news.core.config.NewsSourceConfiguration;

import co.joji.news.core.constants.RepoConstants;
import co.joji.news.core.repositories.newsprovider.NewsAgencyRepo;
import co.joji.news.core.repositories.newsprovider.models.News;
import co.joji.news.core.repositories.newsprovider.models.NewsItem;

import co.joji.news.repositories.newsapi.models.Article;
import co.joji.news.repositories.newsapi.models.SearchResult;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.logging.Level;

@Log
public class NewsapiRepo implements NewsAgencyRepo {

    NewsSourceConfiguration configuration;

    @Autowired
    RestTemplate restTemplate;

    public NewsapiRepo(NewsSourceConfiguration newsSourcepConfiguration) {
        this.configuration = newsSourcepConfiguration;

    }


    public News SearchNews(
            String query, LocalDateTime
            fromDate,
            LocalDateTime toDate) {

        UriComponents url = getUrl(query, fromDate, toDate);
        ResponseEntity<SearchResult> responseEntity;

        responseEntity = getSearchResponse(url);

        SearchResult searchResult = responseEntity.getBody();
        News news = MapResult(searchResult);
        return news;
    }

    private News MapResult(SearchResult searchResult) {

        NewsItem[] newsItems = new NewsItem[searchResult.getTotalResults()];
        var articles = searchResult.getArticles();
        int counter = 0;
        for (Article article : articles) {

            newsItems[counter++] = new NewsItem(article.getTitle(), article.getDescription(), article.getUrl(), article.getPublishedAt(), article.getContent());
        }
        News news = new News(searchResult.getStatus(), searchResult.getTotalResults(), newsItems);
        return news;

    }

    private ResponseEntity<SearchResult> getSearchResponse(UriComponents uriComponents) {
        ResponseEntity<SearchResult> responseEntity;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        String uri = uriComponents.toUri().toString();
        responseEntity = restTemplate.getForEntity(uri, SearchResult.class);


        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.log(Level.SEVERE, "New API failed with follwoing status code ");
            throw new RestClientException(responseEntity.toString());

        }
        return responseEntity;
    }

    private UriComponents getUrl(String query, LocalDateTime fromDate, LocalDateTime toDate) {
        var url = UriComponentsBuilder
                .fromUriString(this.configuration.getAddress())
                .queryParam(RepoConstants.APIKEY, configuration.getAuthKey())
                .queryParam(RepoConstants.FROM, fromDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .queryParam(RepoConstants.TO, toDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .queryParam(RepoConstants.QUERY, query)

                .build();
        return url;
    }
}
