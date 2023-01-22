package com.example.db_project;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("users")
    Call<List<userRemote>> getAllUsers();

    @GET("posts")
    Call<List<postRemote>> getAllPosts();

    @GET("reactions")
    Call<List<reactionRemote>> getAllReactions();

    @GET("users")
    Call<List<userRemote>> getUsersAfterTime(@Query("stamp") String timestamp);

    @GET("posts")
    Call<List<postRemote>> getPostsAfterTime(@Query("stamp") String timestamp);

    @GET("reactions")
    Call<List<reactionRemote>> getReactionsAfterTime(@Query("stamp") String timestamp);

    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYXBwMjAyMiJ9.iEPYaqBPWoAxc7iyi507U3sexbkLHRKABQgYNDG4Awk"
    })
    @POST("users")
    Call<ResponseBody> insertUser(@Body userRemote userRemoteInstance);

    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYXBwMjAyMiJ9.iEPYaqBPWoAxc7iyi507U3sexbkLHRKABQgYNDG4Awk"
    })
    @POST("posts")
    Call<ResponseBody> insertPost(@Body postRemote postRemoteInstance);

    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYXBwMjAyMiJ9.iEPYaqBPWoAxc7iyi507U3sexbkLHRKABQgYNDG4Awk"
    })
    @POST("reactions")
    Call<ResponseBody> insertReaction(@Body reactionRemote reactionRemoteInstance);

    @GET("posts")
    Call<List<postRemote>> getPostFromUser(@Query("user_id") String id);

    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYXBwMjAyMiJ9.iEPYaqBPWoAxc7iyi507U3sexbkLHRKABQgYNDG4Awk"
    })
    @DELETE("reactions")
    Call<ResponseBody> deleteReactionFromUser(@Query("user_id") String id);

    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYXBwMjAyMiJ9.iEPYaqBPWoAxc7iyi507U3sexbkLHRKABQgYNDG4Awk"
    })
    @DELETE("reactions")
    Call<ResponseBody> deleteReactionFromPostFromUser(@Query("post_id") String id);

    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYXBwMjAyMiJ9.iEPYaqBPWoAxc7iyi507U3sexbkLHRKABQgYNDG4Awk"
    })
    @DELETE("posts")
    Call<ResponseBody> deletePostFromUser(@Query("user_id") String id);

    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYXBwMjAyMiJ9.iEPYaqBPWoAxc7iyi507U3sexbkLHRKABQgYNDG4Awk"
    })
    @DELETE("users")
    Call<ResponseBody> deleteUser(@Query("id") String id);


}