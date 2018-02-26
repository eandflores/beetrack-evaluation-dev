package com.beetrack.evaluation.repository.persistence;

import com.beetrack.evaluation.model.Article;

import java.util.List;

/**
 * Created by edgardo on 2/22/18.
 */

public interface ArticlesPersistenceService {
    void saveArticles(List<Article> articles);
    List<Article> getArticles();
    void addArticleToFavorites(int articleId);
    void deleteArticleFromFavorites(int articleId);
    List<Article> getFavorites();
    void deleteArticles();
}
