package co.joji.news.core.config;

import co.joji.news.core.models.IntervalType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "interval")
public class SearchIntervalConfiguration {
    Integer defaultDuration = 10;
    Integer minDuration = 1;
    Integer maxDuration = 100;
    IntervalType defaultInterval = IntervalType.HOURS;
}
