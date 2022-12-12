package co.joji.news.core.newssearch.web;

import co.joji.news.core.config.NewsAppConfiguration;
import co.joji.news.core.models.response.SearchResponse;
import co.joji.news.core.newssearch.web.Utils.TestHelper;
import co.joji.news.core.repositories.newsprovider.NewsAgencyRepo;
import co.joji.news.core.repositories.newsprovider.models.News;
import co.joji.news.core.services.SearchService;
import co.joji.news.repositories.newsapi.models.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.time.*;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockConstructionWithAnswer;
import static org.mockito.Mockito.mockStatic;


@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NewsSearchWebApplicationTests {

    public static final String URLPATTERN = "http://localhost:%s/news/api/v1/search/?q=%s&N=%s&interval=%s";

    public static final String HTTP_LOCALHOST = "http://localhost:";
    public static final String FILE_NAME_PATTERN = "%s_%s_%s_%sResponse.json";
    @Autowired
    NewsAppConfiguration configuration;

    @Autowired
    SearchService searchService;
    @LocalServerPort
    private int port;


    @Autowired
    NewsAgencyRepo newsAgencyRepo;

    @Test

    public void search_car_MINUTES_11() throws Exception {
        searchData("car", "MINUTES", "11");
    }

    @Test
    public void search_ddd_HOURS_11() throws Exception {
        searchData("ddd", "HOURS", "11");
    }


    @Test
    public void search_ddd_DAYS_2() throws Exception {
        searchData("ddd", "DAYS", "2");
    }

    @Test
    public void search_ddd_WEEKS_1() throws Exception {
        searchData("ddd", "WEEKS", "1");
    }

    @Test
    public void search_dddd_MONTHS_1() throws Exception {
        searchData("dddd", "MONTHS", "1");
    }

    @Test
    public void search_dddd_YEARS_1() throws Exception {
        searchData("dddd", "YEARS", "1");
    }

    @Test
    public void search_invalid_input_BADPARAMinterval() throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        String tempString;

        String url = "http://localhost:" + port + "/news/api/v1/search/?q=abc%s&N=12&interval=BADPARAMinterval";

        ResponseEntity<String> actualResult = null;
        HttpStatus statusCode = HttpStatus.OK;
        try {
            actualResult = restTemplate.getForEntity(url, String.class);

        } catch (
                HttpClientErrorException httpClientErrorException) {
            statusCode = httpClientErrorException.getStatusCode();

        } catch (RestClientException restClientException) {

        }
        Assertions.assertEquals(statusCode, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void search_invalid_input_BADPARAMDuration() throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        String tempString;

        String url = "http://localhost:" + port + "/news/api/v1/search/?q=abc%s&N=hhddts&interval=WEEK";

        ResponseEntity<String> actualResult = null;
        HttpStatus statusCode = HttpStatus.OK;
        try {
            actualResult = restTemplate.getForEntity(url, String.class);

        } catch (
                HttpClientErrorException httpClientErrorException) {
            statusCode = httpClientErrorException.getStatusCode();

        } catch (RestClientException restClientException) {

        }
        Assertions.assertEquals(statusCode, HttpStatus.BAD_REQUEST);
    }

    

    private void searchData(String term, String interval, String duration) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        String tempString;
        tempString = String.format(FILE_NAME_PATTERN, term, interval, duration, "Repository");


        News mockedData = TestHelper.getObjectFromjson(
                tempString, News.class);

        Mockito.when(newsAgencyRepo.SearchNews(any(String.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(mockedData);

        tempString = String.format(FILE_NAME_PATTERN, term, interval, duration, "App");
        var expectedResult = TestHelper.getObjectFromjson(tempString, SearchResponse.class);

        tempString = String.format(URLPATTERN, port, term, duration, interval);
        var actualResult = restTemplate.getForObject(tempString
                , SearchResponse.class);

        Assertions.assertEquals(expectedResult, actualResult);
    }
}


