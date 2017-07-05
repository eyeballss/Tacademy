package com.example.tacademy.listtest.NET;

import com.example.tacademy.listtest.Utility.Const;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tacademy on 2017-06-30.
 */
public class Net {
    private static Net ourInstance = new Net();

    public static Net getInstance() {
        return ourInstance;
    }

    private Net() {
    }

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Const.NetowrkConst.USE_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create()) //응답 데이터를 자동으로 Json 처리 해준다.
            .build();

    public Retrofit getRetrofit() {
        return retrofit;
    }

    DaumFactoryIm memberFactoryIm;

    public DaumFactoryIm getMemberFactoryIm(){
        if(memberFactoryIm ==null){
            memberFactoryIm = retrofit.create(DaumFactoryIm.class);
        }
        return memberFactoryIm;
    }
}
