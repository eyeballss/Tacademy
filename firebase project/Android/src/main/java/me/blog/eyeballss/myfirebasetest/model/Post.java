package me.blog.eyeballss.myfirebasetest.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tacademy on 2017-07-12.
 */

public class Post {
    String title;
    String content;
    String nickname;
    long regDate;
    String email;
    Map<String,Boolean> like  = new HashMap<String, Boolean>();
    int likeCount;

    public Post(){}

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

    public long getRegDate() {
        return regDate;
    }

    public void setRegDate(long regDate) {
        this.regDate = regDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", nickname='" + nickname + '\'' +
                ", regDate=" + regDate +
                ", email='" + email + '\'' +
                ", like=" + like +
                ", likeCount=" + likeCount +
                '}';
    }

    //세팅된 post 데이터를 여러 게시판에 동시 기재하기 위하여 업로드 할 정보를 map 방식으로 설정.
    public Map<String , Object> toMap(){

        Map<String , Object> map = new HashMap<String, Object>();

        map.put("title", this.title);
        map.put("content", this.content);
        map.put("nickname", this.nickname);
        map.put("regDate", this.regDate);
        map.put("email", this.email);
//                                                map.put(this.like);
        map.put("likeCount", this.likeCount);

        return map;
    }

    public Post(String title, String content, String nickname, String email) {
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.regDate = System.currentTimeMillis();
        this.email = email;
//        this.like = like;
//        this.likeCount = likeCount;
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
