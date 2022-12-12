package co.joji.news.core.repositories.newsprovider.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    private String status;
    private int totalResults;
    private NewsItem[] items;
}
