package me.blog.eyeballss.gpstest;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tacademy on 2017-07-20.
 */

public class Net {
    private static Net ourInstance = new Net();

    public static Net getInstance() {
        return ourInstance;
    }

    private Net() {
    }

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://ec2-13-59-72-237.us-east-2.compute.amazonaws.com:3000")
            .addConverterFactory(GsonConverterFactory.create()) //응답 데이터를 자동으로 Json 처리 해준다.
            .build();

    public Retrofit getRetrofit() {
        return retrofit;
    }

    CoffeeFactoryIm apiIm;

    public CoffeeFactoryIm getApiIm(){
        if(apiIm ==null){
            apiIm = retrofit.create(CoffeeFactoryIm.class);
        }
        return apiIm;
    }
}
