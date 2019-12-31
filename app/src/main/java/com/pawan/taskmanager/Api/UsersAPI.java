package com.pawan.taskmanager.Api;

import com.pawan.taskmanager.Model.Users;
import com.pawan.taskmanager.ServerResponse.ImageResponse;
import com.pawan.taskmanager.ServerResponse.SignupResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UsersAPI {
    @POST("uers/signup")
    Call<SignupResponse> registerUser(@Body Users users);

    @FormUrlEncoded
    @POST("users/login")
    Call<SignupResponse> checkUser(@Field("username") String username, @Field("password") String password);

    @Multipart
    @POST("upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody img);
}
