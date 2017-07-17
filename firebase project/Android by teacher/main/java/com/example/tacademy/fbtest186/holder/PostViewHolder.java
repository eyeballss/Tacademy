package com.example.tacademy.fbtest186.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.fbtest186.R;
import com.example.tacademy.fbtest186.model.Post;

/**
 * RecyclerView의 구성요소중 커스텀셀과 연괄되어 셀의 구성원을 맴버로 가지고 (FindViewById)
 * 데이터를 맴버에 설정하는 역활을 담당한다.
 */
public class PostViewHolder extends RecyclerView.ViewHolder
{
    // (실습) 커스텀셀의 뷰들을  맴버 변수로 두고 생성자 안에서 초기화 한다
    ImageView profile;
    TextView nickname, likeCount, title, content;
    public ImageButton likeBtn;
    // 커스텀셀 객체(View)를 만들어서 생성자에 전달된다
    public PostViewHolder(View itemView) {
        super(itemView);
        nickname    = (TextView)itemView.findViewById(R.id.nickname);
        likeCount   = (TextView)itemView.findViewById(R.id.likeCount);
        title       = (TextView)itemView.findViewById(R.id.title);
        content     = (TextView)itemView.findViewById(R.id.content);
        profile     = (ImageView)itemView.findViewById(R.id.profile);
        likeBtn     = (ImageButton)itemView.findViewById(R.id.likeBtn);
    }
    // 데이터를 뷰에 설정
    public void toBind(Post post, View.OnClickListener clickListener)
    {
        nickname.setText(  post.getNickname() );
        likeCount.setText( ""+post.getLikeCount() );
        title.setText(     post.getTitle());
        content.setText(   post.getContent() );
        likeBtn.setOnClickListener(clickListener);
    }

}










