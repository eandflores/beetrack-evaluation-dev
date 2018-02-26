package com.beetrack.evaluation.repository.network.client;

import com.beetrack.evaluation.model.Articles;

import io.reactivex.Observable;

/**
 * Created by edgardo on 2/22/18.
 */

public interface ArticlesNetworkService {
    Observable<Articles> getArticles();
}
