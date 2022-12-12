package co.joji.news.core.repositories.newsprovider;

import co.joji.news.core.repositories.newsprovider.models.News;


import java.time.LocalDateTime;

public interface NewsAgencyRepo {
    News SearchNews(String query, LocalDateTime fromDate , LocalDateTime toDate);
}
