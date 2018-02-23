package com.beetrack.evaluation.network.retrofit;


import com.beetrack.evaluation.network.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ArticlesRetrofitClient {

  private ArticlesRetrofitService articlesRetrofitService;

  public ArticlesRetrofitClient() {
    initRetrofit();
  }

  private void initRetrofit() {
    Retrofit retrofit = retrofitBuilder();
    articlesRetrofitService = retrofit.create(getArticlesServiceClass());
  }

  private Retrofit retrofitBuilder() {
    return new Retrofit.Builder().baseUrl(Constants.ARTICLES_API)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(getOkHttpClient())
        .build();
  }

  private OkHttpClient getOkHttpClient() {
    OkHttpClient.Builder client = new OkHttpClient.Builder();
    ApiInterceptor apiInterceptor = new ApiInterceptor();
    client.addInterceptor(apiInterceptor);
    return client.build();
  }


  private Class<ArticlesRetrofitService> getArticlesServiceClass() {
    return ArticlesRetrofitService.class;
  }

  protected ArticlesRetrofitService getArticlesService() {
    return articlesRetrofitService;
  }
}

