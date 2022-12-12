package co.joji.news.core.services;

import co.joji.news.core.models.response.SearchResponse;
import co.joji.news.core.models.request.SearchRequest;

public interface SearchService {
    public SearchResponse search(SearchRequest searchRequest);
}
