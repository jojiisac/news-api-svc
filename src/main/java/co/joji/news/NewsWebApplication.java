package co.joji.news;

import co.joji.news.core.config.NewsAppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(NewsAppConfiguration.class)
public class NewsWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsWebApplication.class, args);
	}

}
