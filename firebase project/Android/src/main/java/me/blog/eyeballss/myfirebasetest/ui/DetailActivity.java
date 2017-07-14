package me.blog.eyeballss.myfirebasetest.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import me.blog.eyeballss.myfirebasetest.R;
import me.blog.eyeballss.myfirebasetest.fragment_holder.CommentViewHolder;
import me.blog.eyeballss.myfirebasetest.fragment_holder.PostViewHolder;
import me.blog.eyeballss.myfirebasetest.model.Comment;
import me.blog.eyeballss.myfirebasetest.model.Post;
import me.blog.eyeballss.myfirebasetest.model.User;

import static android.R.attr.password;

public class DetailActivity extends RootActivity {

    String key;

    EditText comments_input;
    TextView content, title, likeCount, nickname;
    ImageView profile;
    public ImageButton like;


    //자 이제 덧글을 보여줄 리사이클러뷰를 만들어보자.
    //recyclerview, 데이터, cell, viewholder, adapter, 방향성(종류) 가 필요함.
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<Comment, CommentViewHolder> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        setRecycler();


        setFloatingActionButton();
    }

    private void setRecycler() {
        adapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(
                Comment.class,
                R.layout.recycler_item_detail_comment,
                CommentViewHolder.class,
                getDatabaseReference().child("comments").child(key).limitToFirst(1000)) {
            @Override
            protected void populateViewHolder(CommentViewHolder viewHolder, Comment model, int position) {
                //binding 작업. 아구를 맞춰준다고...(?)
                viewHolder.toBind(model);
            }
        };//까지 어댑터 세팅

        //방향 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        //어댑터 붙임
        recyclerView.setAdapter(adapter);
    }

    private void init() {
        key = getIntent().getStringExtra("key");

        comments_input = (EditText) findViewById(R.id.comments_input);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        content = (TextView) findViewById(R.id.TextView_PieceOfContent);
        title = (TextView) findViewById(R.id.TextView_Title);
        likeCount = (TextView) findViewById(R.id.TextView_LikeCount);
        nickname = (TextView) findViewById(R.id.TextView_UserNickName);
        profile = (ImageView) findViewById(R.id.ImageView_Profile);
        like = (ImageButton) findViewById(R.id.ImageButton_like);

    }

    //덧글 쓰는 메소드으으으으으으
    public void onSendComment_Proto(View view){
        //댓글을 쓰기 위해선
        //유효성 검사를 하고
        //컨테이너에 담음.(Comment class)
        Comment comment = new Comment(System.currentTimeMillis(), "comment", "nickname");
        getDatabaseReference().child("comments").child(key).push().setValue(comment);
    }



    public void onSendComment(View view){

        final String msg = comments_input.getText().toString();
        if(TextUtils.isEmpty(msg) ) {
            comments_input.setError("입력후 전송하세요");
            return;
        }

        getDatabaseReference().child("users").child(getUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //검사 결과 받아서 해당 값 확인. 그릇에 담아서 , 담겨진 값을 확인하자.
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user == null){ // 회원 정보가 없다는 의미가 되겠다.
                    Toast.makeText(DetailActivity.this, "회원이 아니무니다", Toast.LENGTH_SHORT).show();
                    finish();
                }else{

                    Comment comment = new Comment(System.currentTimeMillis(), msg, getUser().getEmail().split("@")[0]);
                    getDatabaseReference().child("comments").child(key).push().setValue(comment).addOnCompleteListener(DetailActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(DetailActivity.this, "덧글 입력 완료", Toast.LENGTH_SHORT).show();
                                comments_input.setText("");
                            }else{
                                Toast.makeText(DetailActivity.this, "덧글 입력 실패", Toast.LENGTH_SHORT).show();
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







    private void setFloatingActionButton() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
