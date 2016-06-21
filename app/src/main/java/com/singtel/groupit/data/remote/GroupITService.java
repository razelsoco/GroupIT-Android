package com.singtel.groupit.data.remote;

import android.database.Observable;

import java.util.List;

import retrofit2.http.GET;

/**
 * Created by lanna on 6/20/16.
 *
 */

public interface GroupITService {

    String ENDPOINT = " http://demo1023649.mockable.io"; // test

    /**
     * Return a list of the latest post IDs.
     */
    @GET("/main")
    Observable<List<Long>> getTopStories();

}
