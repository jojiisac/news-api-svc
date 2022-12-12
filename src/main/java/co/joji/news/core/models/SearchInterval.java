package co.joji.news.core.models;

import lombok.Value;

@Value
public class SearchInterval {
    int duration;
    IntervalType intervalType;
}
