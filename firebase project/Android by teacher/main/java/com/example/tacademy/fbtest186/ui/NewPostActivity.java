package com.example.tacademy.fbtest186.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tacademy.fbtest186.R;
import com.example.tacademy.fbtest186.model.Post;
import com.example.tacademy.fbtest186.model.User;
import com.example.tacademy.fbtest186.util.U;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NewPostActivity extends RootActivity {
    EditText postTitle, postContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        postTitle = (EditText)findViewById(R.id.postTitle);
        postContent = (EditText)findViewById(R.id.postContent);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 작성한 글을 게시판에 업로드한다. (실시간 디비)
                // 3. 사용자가 유효한가 체크 => 사용자들이 존재하는 가지에서 존재여부를 검사 => 선행으로 유저 정보 저장 필요
                // 값검사용 리스너 => addListenerForSingleValueEvent
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("users").child(getUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // 검사 결과를 받아서 해당 값을 확인할수 잇는 그릇에 담았다
                        User user = dataSnapshot.getValue(User.class);
                        if( user == null ){ // 회원이 아닙
                            Toast.makeText(NewPostActivity.this, "회원이 아닙니다. 로그인 혹은 가입후 이용해주세요.", Toast.LENGTH_LONG).show();
                            finish();
                        }else{
                            submit();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
    }
    public void submit()
    {
        // 0. 사전 준비 : 유저 정보를 담는 그릇과, 글 자체를 담는 그릇 설계가 필요
        // 1. 작성내용 검사
        if( !isVaild() ) return;
        showPD();
        // 2. 그릇에 담는다
        Post  post = new Post(
                this.postTitle.getText().toString(),
                this.postContent.getText().toString(),
                getUser().getEmail().split("@")[0],
                getUser().getEmail()
        );
        // 넣고자 하는데이터가 여기 저기 라면 즉, 2개 이상이면 setValue로 보다는 update 개념으로 처리하는게 좀더 났다
        // => 채팅 채널상에서 보이는 최신글과 채팅 창 안에서 보이는 최신글이 항상 일치하는 것이 대표적이 케이스이다.
        // => /
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        // => 키 획득
        String key = databaseReference.child("bbs").push().getKey();
        // 데이터를 쉽게 넣기위해 map으로 획득
        Map<String, Object> data    = post.toMap();
        // 업데이트 할 내용들을 생성
        Map<String, Object> updates = new HashMap<>();
        updates.put( "/bbs/"+key,  data); // 전체 게시물에 내용 추가
        updates.put( "/mybbs/"+ getUser().getUid()+"/"+key, data );
        // 업데이트
        // 4. 디비에 추가 혹은 업데이트 혹은 전송
        databaseReference.updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if( databaseError != null )
                    U.getInstance().log(databaseError.getMessage());
                else{
                    Toast.makeText(NewPostActivity.this, "글 등록되었습니다. ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                stopPD();
            }
        });

    }
    public boolean isVaild()
    {
        String postTitle    = this.postTitle.getText().toString();
        String postContent  = this.postContent.getText().toString();
        if(TextUtils.isEmpty(postTitle)) {
            this.postTitle.setError("제목을 입력하세요");
            return false;
        }
        if(TextUtils.isEmpty(postContent)) {
            this.postContent.setError("내용을 입력하세요");
            return false;
        }
        return true;
    }

}
















