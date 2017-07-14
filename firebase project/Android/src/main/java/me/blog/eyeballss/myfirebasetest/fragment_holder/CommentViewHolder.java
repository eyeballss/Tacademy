package me.blog.eyeballss.myfirebasetest.fragment_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import me.blog.eyeballss.myfirebasetest.R;
import me.blog.eyeballss.myfirebasetest.model.Comment;

/**
 * Created by Tacademy on 2017-07-14.
 */

//comment 정보를 표현하는 셀에 대응하는 클래스
public class CommentViewHolder extends RecyclerView.ViewHolder {
    ImageView comment_profile;
    TextView comment_nickname;
    TextView comment_value;

    //cell에 들어있는 뷰를 선언
    public CommentViewHolder(View itemView) {
        super(itemView);
        //선언 후 초기화.

        comment_profile = itemView.findViewById(R.id.ImageView_Comment_Profile);
        comment_value = itemView.findViewById(R.id.TextView_Comment_Value);
        comment_nickname= itemView.findViewById(R.id.TextView_Comment_Nickname);

    }

    public void toBind(Comment comment){
        //데이터를 뷰에 세팅함.

        comment_nickname.setText(comment.getNickname());
        comment_value.setText(comment.getComment());

    }
}
