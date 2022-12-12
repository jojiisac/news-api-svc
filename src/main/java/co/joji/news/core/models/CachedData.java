package co.joji.news.core.models;


import co.joji.news.core.models.response.SearchResponse;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class CachedData {
    SearchResponse data;
    LocalDateTime cachedTime;
}
