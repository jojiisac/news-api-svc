package co.joji.news.core.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@ConfigurationProperties("news")
public class NewsAppConfiguration {
    SearchIntervalConfiguration interval;
    NewsSourceConfiguration source;
    int cacheLife = 10;
    boolean offlineMode = false;
//    boolean showOldDataOnFailure = false;
}
