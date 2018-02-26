package com.beetrack.evaluation.interactor;

import com.beetrack.evaluation.model.Article;
import com.beetrack.evaluation.model.Articles;
import com.beetrack.evaluation.repository.network.client.ArticlesNetworkService;
import com.beetrack.evaluation.repository.persistence.ArticlesPersistenceClient;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by mbot on 2/21/18.
 */


public class ArticlesInteractor {

    private ArticlesNetworkService articlesNetworkService;
    private ArticlesPersistenceClient articlesCache;

    public ArticlesInteractor(ArticlesNetworkService articlesNetworkService, ArticlesPersistenceClient articlesCache) {
        this.articlesNetworkService = articlesNetworkService;
        this.articlesCache      = articlesCache;
    }

    public Observable<Articles> getArticles() {
        return articlesNetworkService.getArticles();
    }

    public void saveArticles(List<Article> articles) {
        articlesCache.saveArticles(articles);
    }

    public List<Article> getArticlesCache() {
        return articlesCache.getArticles();
    }

    public void addArticleToFavorites(int articleId) {
        articlesCache.addArticleToFavorites(articleId);
    }

    public void deleteArticleFromFavorites(int articleId) {
        articlesCache.deleteArticleFromFavorites(articleId);
    }

    public List<Article> getFavorites() {
        return articlesCache.getFavorites();
    }

    public void deleteArticles() {
        articlesCache.deleteArticles();
    }
}
