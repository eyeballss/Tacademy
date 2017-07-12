package me.blog.eyeballss.myfirebasetest.model;

/**
 * Created by Tacademy on 2017-07-12.
 */

public class User {
    String email;
    String token;
    String nickname;
    String cellular;
    long regDate;
    String profile;

    public User(){}

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", nickname='" + nickname + '\'' +
                ", cellular='" + cellular + '\'' +
                ", regDate='" + regDate + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }

    public User(String email, String token, String nickname, String cellular, long regDate, String profile) {
        this.email = email;
        this.token = token;
        this.nickname = nickname;
        this.cellular = cellular;
        this.regDate = regDate;
        this.profile = profile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCellular() {
        return cellular;
    }

    public void setCellular(String cellular) {
        this.cellular = cellular;
    }

    public long getRegDate() {
        return regDate;
    }

    public void setRegDate(long regDate) {
        this.regDate = regDate;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
