package com.example.client1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAPI {

    @POST("posts/")
    Call<UserInfo> post_posts(@Body UserInfo post);

    @PATCH("posts/{pk}/")
    Call<UserInfo> patch_posts(@Path("pk") int pk, @Body UserInfo post);

    @DELETE("posts/{pk}/")
    Call<UserInfo> delete_posts(@Path("pk") int pk);

    @GET("users/")
    Call<List<UserInfo>> getAllUsers();

    @GET("posts/{pk}/")
    Call<UserInfo> get_post_pk(@Path("pk") int pk);
}
