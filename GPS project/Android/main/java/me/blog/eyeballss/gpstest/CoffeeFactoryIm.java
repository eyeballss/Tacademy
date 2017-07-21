package me.blog.eyeballss.gpstest;

import org.w3c.dom.Node;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    @GET("coffee")
    Call<ResCoffeeStoresModel> coffee(@Query("t") String t);

    @POST("coffee")
    Call<ResCoffeeStoresModel> coffeeDist(@Body DistModel user);


}
