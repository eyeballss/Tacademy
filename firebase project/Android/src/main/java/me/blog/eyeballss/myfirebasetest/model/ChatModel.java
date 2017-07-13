package me.blog.eyeballss.myfirebasetest.model;

/**
 * Created by Tacademy on 2017-07-10.
 */

//채팅할 때 필요한 모델
//채팅창에 보여주기 위해 email, msg, date가 필요함. check는 읽었는지 아닌지 확인
public class ChatModel {
    String email;
    String msg;
    long regDate;
    boolean check;

    //디포오오오오올트
    public ChatModel(){}

    public ChatModel(String email, String msg, long regDate, boolean check) {
        this.email = email;
        this.msg = msg;
        this.regDate = regDate;
        this.check=check;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
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
