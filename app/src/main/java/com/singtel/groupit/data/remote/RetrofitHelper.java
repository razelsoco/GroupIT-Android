package com.singtel.groupit.data.remote;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lanna on 6/20/16.
 *
 */

public class RetrofitHelper {

    public GroupITService newGroupITService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GroupITService.ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getRequestHeader())
                .build();
        return retrofit.create(GroupITService.class);
    }

    private OkHttpClient getRequestHeader() {
        return new OkHttpClient().newBuilder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
    }

}
