package com.seiagul.adminelapor.api;


import com.seiagul.adminelapor.model.login.Login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("loginAdmin.php")
    Call<Login> loginResponse(
            @Field("email") String email,
            @Field("password") String password
    );






}
