package com.beetrack.evaluation.network.client;

import com.beetrack.evaluation.model.Articles;
import com.beetrack.evaluation.network.retrofit.ArticlesRetrofitClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by edgardo on 2/22/18.
 */

public class ArticlesClient extends ArticlesRetrofitClient implements ArticlesService {

    @Override public Observable<Articles> getArticles() {
        return getArticlesService().getArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}

