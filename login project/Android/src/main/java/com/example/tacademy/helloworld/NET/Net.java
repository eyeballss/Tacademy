package com.example.tacademy.helloworld.NET;

import com.example.tacademy.helloworld.Utility.Const;

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
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public Retrofit getRetrofit() {
        return retrofit;
    }

    MemberFactoryIm memberFactoryIm;

    public MemberFactoryIm getMemberFactoryIm(){
        if(memberFactoryIm ==null){
            memberFactoryIm = retrofit.create(MemberFactoryIm.class);
        }

        return memberFactoryIm;
    }
}
