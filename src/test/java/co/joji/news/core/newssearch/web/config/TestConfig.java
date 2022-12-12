package co.joji.news.core.newssearch.web.config;


import co.joji.news.core.config.NewsAppConfiguration;
import co.joji.news.core.newssearch.web.Utils.TestHelper;
import co.joji.news.core.repositories.newsprovider.NewsAgencyRepo;
import co.joji.news.core.services.SearchService;
import co.joji.news.repositories.newsapi.NewsapiRepo;
import co.joji.news.repositories.newsapi.models.SearchResult;
import org.mockito.Mock;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;

@Profile("test")
@Configuration
public class TestConfig {

    @Autowired
    NewsAppConfiguration newsAppConfiguration;

    @Bean
    @Primary
    public NewsAgencyRepo newsAgencyRepo() {
        return Mockito.mock(NewsAgencyRepo.class);
    }

   @Primary
   @Bean
   public Clock fixedClock() {
        return Clock.fixed(
                Instant.parse("2022-12-07T10:05:23.653Z"),
                ZoneId.of("Europe/Prague"));
    }

}
