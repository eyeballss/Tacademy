package com.example.tacademy.helloworld.NET;

import com.example.tacademy.helloworld.Model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Tacademy on 2017-06-30.
 */

public interface MemberFactoryIm {
    //통신 담당 메소드 구현.
    @GET("login")
    Call<List<User>> login(@Query("userEmail") String email, @Query("userPwd") String password);

    @POST("join")
    Call<Res_join> join(@Body Req_join user); //보낼때는 Req로 보내고 받을 때는 Res로 받음.

    @GET("dupl")
    Call<String> dupl(@Query("userEmail") String email);
}
