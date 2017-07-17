package com.example.tacademy.fbtest186.model;

/**
 * Created by Tacademy on 2017-07-10.
 */

public class ChatModel
{
    String email;
    String msg;
    long regDate;

    // 기본 생성자는 반드시 만든다
    public ChatModel() {
    }

    public ChatModel(String email, String msg, long regDate) {
        this.email = email;
        this.msg = msg;
        this.regDate = regDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getRegDate() {
        return regDate;
    }

    public void setRegDate(long regDate) {
        this.regDate = regDate;
    }
}
