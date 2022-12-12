package co.joji.news.core.models.response;

import lombok.Data;


/*
Interval group
Count of entries in each group
List of entries within each group

 */
@Data
public class SearchResponse {
    IntervalGroup[] items;
}
