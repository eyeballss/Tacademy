package me.blog.eyeballss.myfirebasetest.model;

/**
 * Created by Tacademy on 2017-07-13.
 */

public class Comment {
    long regDate;
    String comment;
    String nickname;

    public Comment(){}

    public Comment(long regDate, String comment, String nickname) {
        this.regDate = regDate;
        this.comment = comment;
        this.nickname = nickname;
    }

    public long getRegDate() {
        return regDate;
    }

    public void setRegDate(long regDate) {
        this.regDate = regDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
