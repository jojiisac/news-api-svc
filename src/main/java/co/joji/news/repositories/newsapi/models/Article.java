package co.joji.news.repositories.newsapi.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class Article {
    private Source source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private OffsetDateTime publishedAt;
    private String content;
}
