package com.example.tacademy.fbtest186.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.fbtest186.R;
import com.example.tacademy.fbtest186.holder.CommentViewHolder;
import com.example.tacademy.fbtest186.model.Comment;
import com.example.tacademy.fbtest186.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DetailActivity extends RootActivity {
    // UI관련 변수 ==================================================================================
    EditText comments_input;
    RecyclerView recylerView;
    ImageView profile;
    TextView nickname, likeCount, title, content;
    ImageButton likeBtn;
    // 글 관련 변수 =================================================================================
    String key;
    // 리스팅 관련 변수 =============================================================================
    FirebaseRecyclerAdapter<Comment, CommentViewHolder> adapter;
    // RecyclerView, 데이터, cell, ViewHolder, Adapter, 방향성(종류)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        key = getIntent().getStringExtra("key");

        comments_input = (EditText)findViewById(R.id.comments_input);
        recylerView    = (RecyclerView)findViewById(R.id.recylerView);

        nickname    = (TextView)findViewById(R.id.nickname);
        likeCount   = (TextView)findViewById(R.id.likeCount);
        title       = (TextView)findViewById(R.id.title);
        content     = (TextView)findViewById(R.id.content);
        profile     = (ImageView)findViewById(R.id.profile);
        likeBtn     = (ImageButton)findViewById(R.id.likeBtn);
        // =========================================================================================
        // 리스팅 관련 처리
        adapter     = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(
                Comment.class,
                R.layout.cell_comment_layout,
                CommentViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("comments").child(key).limitToFirst(1000)
        ){
            @Override
            protected void populateViewHolder(CommentViewHolder viewHolder, Comment model, int position) {
                // binding
                viewHolder.toBind(model);
            }
        };
        // 방향
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recylerView.setLayoutManager(linearLayoutManager);
        // 연동
        recylerView.setAdapter(adapter);

    }
    public void onSendComment(View view)
    {
        // 댓글 쓰기
        // 1. 유효성 검사
        String msg = comments_input.getText().toString();
        if(TextUtils.isEmpty(msg) ) {
            comments_input.setError("입력후 전송하세요");
            return;
        }
        // 2. model => 그릇을 준비 => 그릇에 담는다
        final Comment comment = new Comment(getUser().getEmail().split("@")[0], msg, System.currentTimeMillis());
        // 3. 글스기
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(getUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // 검사 결과를 받아서 해당 값을 확인할수 잇는 그릇에 담았다
                User user = dataSnapshot.getValue(User.class);
                if( user == null ){ // 회원이 아닙
                    Toast.makeText(DetailActivity.this, "회원이 아닙니다. 로그인 혹은 가입후 이용해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    FirebaseDatabase.getInstance().getReference().child("comments").child(key).push().setValue(comment)
                    .addOnCompleteListener(DetailActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(DetailActivity.this, "댓글 입력 완료", Toast.LENGTH_SHORT).show();
                                comments_input.setText("");
                            }else{
                                Toast.makeText(DetailActivity.this, "댓글 입력 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void onSendComment1(View view)
    {
        // 댓글 쓰기
        // 1. 유효성 검사
        // 2. model => 그릇을 준비 => 그릇에 담는다
        Comment comment = new Comment("k","내용", System.currentTimeMillis());
        // 3. 글스기
        FirebaseDatabase.getInstance().getReference().child("comments").child(key).push().setValue(comment);
    }

}














