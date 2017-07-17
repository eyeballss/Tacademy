package com.example.tacademy.fbtest186.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 새글을 담는 그릇
 */
public class Post
{
    String title, content, nickname, email;
    long regdate = System.currentTimeMillis();
    // <좋아요 누른 사람의 이메일 혹은 닉네임등 고유값, true/false>
    Map<String, Boolean> like = new HashMap<>();
    // 좋아요수
    int likeCount;

    public Post() {
    }

    public Post(String title, String content, String nickname, String email) {
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.email = email;
    }

    // 세팅된 Post를 여러 게시판에 동시에 기재하기 위해 업로드할 정보를 map 방식으로 설정한다.
    public Map<String, Object> toMap()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("content", content);
        map.put("nickname", nickname);
        map.put("email", email);
        map.put("regdate", regdate);
        map.put("likeCount", likeCount);
        return map;
    }


    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", regdate=" + regdate +
                ", like=" + like +
                ", likeCount=" + likeCount +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getRegdate() {
        return regdate;
    }

    public void setRegdate(long regdate) {
        this.regdate = regdate;
    }

    public Map<String, Boolean> getLike() {
        return like;
    }

    public void setLike(Map<String, Boolean> like) {
        this.like = like;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
