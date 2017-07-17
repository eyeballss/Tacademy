package com.example.tacademy.fbtest186.model;

/**
 * 회원 인증시 필요 정보를 디비에 가입하여 별도 관리
 * 사용자 유효성 및 차후 푸시 전송에 사용됨
 */
public class User {
    String email, nickname, token, hp;
    long regdate;

    public User() {
    }

    public User(String email, String nickname, String token, String hp, long regdate) {
        this.email = email;
        this.nickname = nickname;
        this.token = token;
        this.hp = hp;
        this.regdate = regdate;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", token='" + token + '\'' +
                ", hp='" + hp + '\'' +
                ", regdate=" + regdate +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public long getRegdate() {
        return regdate;
    }

    public void setRegdate(long regdate) {
        this.regdate = regdate;
    }
}
