package com.beetrack.evaluation.interactor;

import com.beetrack.evaluation.model.Article;
import com.beetrack.evaluation.model.Articles;
import com.beetrack.evaluation.network.client.ArticlesService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by mbot on 2/21/18.
 */


public class ArticlesInteractor {

    private ArticlesService articlesService;

    public ArticlesInteractor(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    public Observable<Articles> getArticles() {
        return articlesService.getArticles();
    }

}
