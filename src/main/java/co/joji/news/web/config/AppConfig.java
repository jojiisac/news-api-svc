package co.joji.news.web.config;


import co.joji.news.core.config.NewsAppConfiguration;
import co.joji.news.core.repositories.newsprovider.NewsAgencyRepo;
import co.joji.news.core.services.CachingService;
import co.joji.news.core.services.CachingServiceImpl;

import co.joji.news.core.services.SearchService;
import co.joji.news.core.services.SearchServiceImpl;
import co.joji.news.repositories.newsapi.NewsapiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
@ConfigurationProperties
@ComponentScan(basePackageClasses = {NewsAppConfiguration.class, SearchServiceImpl.class})

public class AppConfig {

    @Autowired
    NewsAppConfiguration newsAppConfiguration;

    @Bean
    public SearchService getSearchService() {
        return new SearchServiceImpl();
    }



    @Bean
    CachingService getCachingService() {
        return new CachingServiceImpl();
    }

    @Bean
    RestTemplate getRestTemplate() {

        return new RestTemplate();
    }





    @ConditionalOnProperty(
            value="repo",
            havingValue = "true",
            matchIfMissing = false)
    @Bean
    public Clock GetClock() {
        return Clock.systemUTC();
    }



    @ConditionalOnProperty(
            value="repo",
            havingValue = "true",
            matchIfMissing = false)
    @Bean
    public NewsAgencyRepo newsAgencyRepo() {

        return new NewsapiRepo(newsAppConfiguration.getSource());

    }
}
