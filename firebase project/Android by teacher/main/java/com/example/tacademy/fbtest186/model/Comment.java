package com.example.tacademy.fbtest186.model;

/**
 * Created by Tacademy on 2017-07-13.
 */

public class Comment
{
    String nickname, comment;
    long regdate;

    public Comment() {
    }

    public Comment(String nickname, String comment, long regdate) {
        this.nickname = nickname;
        this.comment = comment;
        this.regdate = regdate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getRegdate() {
        return regdate;
    }

    public void setRegdate(long regdate) {
        this.regdate = regdate;
    }
}
