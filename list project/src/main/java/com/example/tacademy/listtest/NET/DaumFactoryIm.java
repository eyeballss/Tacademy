package com.example.tacademy.listtest.NET;

import com.example.tacademy.listtest.Model.DaumSearchImageModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Tacademy on 2017-06-30.
 */

public interface DaumFactoryIm {
    //통신 담당 메소드 구현.
    @GET("search/image")
    Call<DaumSearchImageModel> searchImg(@QueryMap Map<String, String> options);

//    @POST("join")
//    Call<com.example.tacademy.listtest.NET.Res_join> join(@Body com.example.tacademy.listtest.NET.Req_join user); //보낼때는 Req로 보내고 받을 때는 Res로 받음.
//
//    @GET("dupl")
//    Call<String> dupl(@Query("userEmail") String email);
}
