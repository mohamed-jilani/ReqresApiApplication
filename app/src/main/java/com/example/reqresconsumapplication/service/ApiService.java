package com.example.reqresconsumapplication.service;

import com.example.reqresconsumapplication.modèle.User;
import com.example.reqresconsumapplication.modèle.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface ApiService {
    @GET("api/users")
    Call<UserResponse> getUsers(@Query("page") int page);

    @GET("api/users/{id}")
    Call<User> getUser(@Path("id") int userId);

    @POST("api/users")
    Call<User> createUser(@Body User user);
    @PUT("api/users/{id}")
    Call<User> updateUser(@Path("id") int userId, @Body User user);

    @DELETE("api/users/{id}")
    Call<Void> deleteUser(@Path("id") int userId);
}
