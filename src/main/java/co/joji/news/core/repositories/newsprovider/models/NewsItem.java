package co.joji.news.core.repositories.newsprovider.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsItem {
    private String title;
    private String description;
    private String url;
    private OffsetDateTime publishedAt;
    private String content;
}
