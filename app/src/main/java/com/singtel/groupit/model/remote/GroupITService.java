package com.singtel.groupit.model.remote;


import com.singtel.groupit.model.ArticlesResponse;
import com.singtel.groupit.model.TestContactsResponse;
import com.singtel.groupit.model.TestTemplatesResponse;
import com.singtel.groupit.model.TestUserResponse;
import com.singtel.groupit.model.domain.AccountInfo;
import com.singtel.groupit.model.domain.Note;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lanna on 6/20/16.
 *
 */

public interface GroupITService {

    /*
        ENDPOINT
     */
    String MOCKABLE_ENDPOINT_TEST = "http://demo1023649.mockable.io"; // dev test // raz: demo6174646, lan: demo1023649
    String SERVER_STAGING = "http://groupit-staging.ap-southeast-1.elasticbeanstalk.com"; // staging

    String ENDPOINT = SERVER_STAGING;

    /*
        Api Constants
     */
    byte READ_TIME_OUT_IN_SECOND = 5;
    byte CONNECTION_TIME_OUT_IN_SECOND = 5;


    /*
        Factory / Retrofit Setup
     */
    class Factory {
        public static GroupITService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GroupITService.ENDPOINT)
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getRequestHeader())
                    .build();
            return retrofit.create(GroupITService.class);
        }

        private static OkHttpClient getRequestHeader() {
            return new OkHttpClient().newBuilder()
                    .readTimeout(READ_TIME_OUT_IN_SECOND, TimeUnit.SECONDS)
                    .connectTimeout(CONNECTION_TIME_OUT_IN_SECOND, TimeUnit.SECONDS)
                    .build();
        }
    }


    /*
        API Defines
     */

    /**
     * AccountInfo
     */
    @FormUrlEncoded
    @POST("/token")
    Observable<AccountInfo> getToken(
            @Field("username") String username,
            @Field("password") String password,
            @Field("grant_type") String grantType
    );


    /**
     * Return a list of articles.
     */
//    @GET("/main")
    @GET("http://demo1023649.mockable.io/main") // test
    Observable<ArticlesResponse> getTopStories();


    /**
     * Return a list of Inbox Notes.
     */
    @GET("/api/notes/inbox")
//    @Headers({"Transfer-Encoding: chunked"})
//    @GET("http://demo1023649.mockable.io/inbox") // test
    Observable<List<Note>> getInbox(@Header("Authorization") String authorization); // authorization: Bearer {{access_token}}


    /**
     * Return a list of Sent Notes.
     */
    @GET("/api/Notes/sent")
//    @GET("http://demo1023649.mockable.io/sentnotes") // test
    Observable<List<Note>> getSentNotes(@Header("Authorization") String authorization);


    @GET("/user")
    Observable<TestUserResponse> getUser();

    @GET("/contacts")
    Observable<TestContactsResponse> getContacts();

    @GET("/templates")
    Observable<TestTemplatesResponse> getTemplates();
}
