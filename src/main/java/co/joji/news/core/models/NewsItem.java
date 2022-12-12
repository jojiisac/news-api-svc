package co.joji.news.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.net.URI;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsItem {
    String title;
    String address;
}
