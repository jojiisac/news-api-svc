package co.joji.news.core.services;

import co.joji.news.core.models.CachedData;
import co.joji.news.core.models.request.SearchRequest;
import co.joji.news.core.models.response.SearchResponse;

public interface CachingService {

    public  CachedData GetData(SearchRequest searchRequest);


    void PutData(SearchResponse data, SearchRequest searchRequest, boolean overrideCache);
}

