package com.blogspot.yourfavoritekaisar.crudmakanan.data.remote;

import com.blogspot.yourfavoritekaisar.crudmakanan.model.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("loginuser.php")
    Call<LoginResponse> loginUser(
      @Field("username") String username,
      @Field("password") String password
    );
}