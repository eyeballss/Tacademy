package me.blog.eyeballss.myfirebasetest.fragment_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import me.blog.eyeballss.myfirebasetest.R;
import me.blog.eyeballss.myfirebasetest.model.Post;

/**
 * Created by Tacademy on 2017-07-13.
 * Recycler view의 구성요소 중, 커스텀 item 과 연결되어 item의 구성원을 멤버로 갖고(findViewById)
 * 데이터를 멤버에 설정하는 역할.
 */

//얘는 리사이클러의 홀더 자체가 클래스로 만들어져 있다.
    //이 홀더의 역할은, 재사용을 위하여,
    //클라이언트로부터의 정보를 담은 Post라는 녀석의 정보를 담는 것(...)
public class PostViewHolder extends RecyclerView.ViewHolder {

    TextView content, title, likeCount, nickname;
    ImageView profile;
    public ImageButton like;

    //item 객체(view)를 만들어서 생성자에 전달. 즉, 우리는 직접 item의 view를 inflate 하지 않아도 됨.
    public PostViewHolder(View itemView) {
        super(itemView);

        content = itemView.findViewById(R.id.TextView_PieceOfContent);
        title = itemView.findViewById(R.id.TextView_Title);
        likeCount = itemView.findViewById(R.id.TextView_LikeCount);
        nickname = itemView.findViewById(R.id.TextView_UserNickName);
        profile = itemView.findViewById(R.id.ImageView_Profile);
        like = itemView.findViewById(R.id.ImageButton_like);
    }

    //데이터를 넣어 세팅하는 메소드
    public void toBind(Post post, View.OnClickListener listener){
        content.setText(post.getContent());
        nickname.setText(post.getNickname());
        title.setText(post.getTitle());
        likeCount.setText(String.valueOf(post.getLikeCount()));

        like.setOnClickListener(listener);
    }
}
