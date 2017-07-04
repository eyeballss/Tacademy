package com.example.tacademy.helloworld.NET;

import com.example.tacademy.helloworld.Model.User;

/**
 * Created by Tacademy on 2017-07-03.
 */


public class Res_join {

    int code; //서버로부터의 응답 코드. 404, 500, 200 등.
    String msg; //서버로부터의 응답 메세지.
    User user; //가입한 유저 정보.

}
