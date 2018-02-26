package com.beetrack.evaluation.repository.network.retrofit;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class ApiInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        Request request = original.newBuilder().method(original.method(), original.body()).build();
        return chain.proceed(request);
    }
}