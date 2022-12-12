package co.joji.news.core.models.response;

import co.joji.news.core.models.IntervalType;
import co.joji.news.core.models.NewsItem;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class IntervalGroup {
    IntervalType intervalType;
    LocalDateTime startTime;
    LocalDateTime endTime;
    int count;
    List<NewsItem> news;
}
