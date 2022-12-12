package co.joji.news.core.services;

import co.joji.news.core.models.CachedData;
import co.joji.news.core.models.request.SearchRequest;
import co.joji.news.core.models.response.SearchResponse;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.HashMap;


public class CachingServiceImpl  implements CachingService {
    // Hash map is chosen as there will more reads and less writes
    // a thread safe implementation may not be required
     AbstractMap<String,CachedData> cacheStore = new HashMap<String, CachedData>();

    public static final String SEARCH_KEY_PATTERN = "term:%s_interval:%s_duration%s ";

    @Override
    public CachedData GetData(SearchRequest searchRequest) {
        return cacheStore.get(Getkey(searchRequest));
    }

    @Override
    public void PutData(SearchResponse data, SearchRequest searchRequest, boolean overrideCache){
        CachedData cachedData = new CachedData(data, LocalDateTime.now());
        String key= Getkey(searchRequest);
        if (overrideCache) {
           cacheStore.put(key,cachedData);
        }else {
            cacheStore.putIfAbsent(key,cachedData);
        }
    }
    private String Getkey(SearchRequest searchRequest) {
        return String.format(SEARCH_KEY_PATTERN,searchRequest.getSearchParam(),searchRequest.getSearchInterval().getIntervalType().toString(),   searchRequest.getSearchInterval().getDuration() );
    }
}
