package co.joji.news.core.config;


import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "source")
public class NewsSourceConfiguration {
    String address;
    String authKey;
    boolean offLineMode = false;
}
