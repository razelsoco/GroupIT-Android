package com.singtel.groupit.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lanna on 6/20/16.
 *
 */

public class RetrofitHelper {

    public GroupITService newGroupITService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GroupITService.ENDPOINT)
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GroupITService.class);
    }

}
