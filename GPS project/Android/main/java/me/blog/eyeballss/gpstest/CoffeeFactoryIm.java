package me.blog.eyeballss.gpstest;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tacademy on 2017-07-20.
 * 커피 전문점 정보를 요청하는 API 통신 메소드 정의
 */

public interface CoffeeFactoryIm {

    //모든 커피 전문점
    @GET("all")
    Call<ResCoffeeStoresModel> all();

    //특정 커피 전문점
    @GET("coffeeStores")
    Call<ResCoffeeStoresModel> coffee(@Query("t") String t);

}
