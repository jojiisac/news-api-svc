package co.joji.news.core.models.request;

import co.joji.news.core.models.SearchInterval;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SearchRequest {
    SearchInterval searchInterval;
    String searchParam;
}
