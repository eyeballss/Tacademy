package com.example.tacademy.helloworld.NET;

import com.example.tacademy.helloworld.Model.User;

/**
 * Created by Tacademy on 2017-07-03.
 */

//Req_join 을 패킷에 넣어서 보내는 것임.
public class Req_join {

    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
