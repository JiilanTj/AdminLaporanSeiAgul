package com.seiagul.adminelapor.api;

import com.seiagul.adminelapor.model.login.Login;
import com.seiagul.adminelapor.model.lurah.Lurah;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterfaceDua {

    @FormUrlEncoded
    @POST("loginLurah.php")
    Call<Lurah> loginResponseLurah(
            @Field("email") String email,
            @Field("password") String password
    );
}
