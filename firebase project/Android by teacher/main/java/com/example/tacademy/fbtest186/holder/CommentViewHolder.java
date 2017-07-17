package com.example.tacademy.fbtest186.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.fbtest186.R;
import com.example.tacademy.fbtest186.model.Comment;

/**
 * 코멘트 정보를 표현하는 셀에 대응하는 클레스
 */
public class CommentViewHolder extends RecyclerView.ViewHolder
{
    // cell에 들어있는 뷰를 선언
    ImageView comment_profile;
    TextView comment_nickname, comment_value;
    public CommentViewHolder(View itemView) {
        super(itemView);
        // 초기화 => findViewbyId()
        comment_profile     = (ImageView)itemView.findViewById(R.id.comment_profile);
        comment_nickname    = (TextView)itemView.findViewById(R.id.comment_nickname);
        comment_value       = (TextView)itemView.findViewById(R.id.comment_value);
    }
    public void toBind(Comment comment)
    {
        // 사용 => 데이터를 뷰에 세팅한다.
        //comment_profile
        comment_nickname.setText(comment.getNickname());
        comment_value.setText(comment.getComment());
    }
}
