package co.joji.news.repositories.newsapi.models;

import lombok.Data;

@Data
public class SearchResult {
    private String status;
    private int totalResults;
    private  Article[] articles;

}
