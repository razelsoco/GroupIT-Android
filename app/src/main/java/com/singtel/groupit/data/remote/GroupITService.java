package com.singtel.groupit.data.remote;


import com.singtel.groupit.model.TestResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by lanna on 6/20/16.
 *
 */

public interface GroupITService {

    String ENDPOINT = " http://demo1023649.mockable.io"; // test

    /**
     * Return a list of articles.
     */
    @GET("/main")
    Observable<TestResponse> getTopStories();

}
