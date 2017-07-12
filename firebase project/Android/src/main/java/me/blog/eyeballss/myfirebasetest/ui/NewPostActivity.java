package me.blog.eyeballss.myfirebasetest.ui;

import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import me.blog.eyeballss.myfirebasetest.R;
import me.blog.eyeballss.myfirebasetest.model.Post;
import me.blog.eyeballss.myfirebasetest.model.User;

import static android.R.attr.password;

public class NewPostActivity extends RootActivity {

    EditText postTitle;
    EditText postContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
        setfloatingActionButton();

    }

    private void setfloatingActionButton() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                ///


                //addChildEventListener, addListenerForSingleValueEvent, addValueEventListener 등 단발성 리스너들은 쓰고나서 반드시 지워야 할 것.
                //addListenerForSingleValueEvent : 값 검사 리스너.
                getDatabaseReference().child("users").child(getUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    //검사 결과 받아서 해당 값 확인. 그릇에 담아서 , 담겨진 값을 확인하자.
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if(user == null){ // 회원 정보가 없다는 의미가 되겠다.
                            Toast.makeText(NewPostActivity.this, "회원이 아니무니다", Toast.LENGTH_SHORT).show();
                        }else{
                            //버튼을 누르면 사용자가 작성한 게시물을 업로드함.
                            submit();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                ///





//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void submit() {
        showPd();
        String title = postTitle.getText().toString();
        String content = postContent.getText().toString();


        //작성 내용 검사
        if(!isValid(title,content)) return;
        //그릇에 담음
        Post post = new Post(title, content, getUser().getEmail().split("@")[0], getUser().getEmail());
        //넣고 싶은 데이터의 분류가 다양하다면, 그래서 여러 분류를 갖게 된다면  setValue 보다는 update 해서 거기도 올리고 여기도 올리는게 좋음.
          //즉, 모든 분류에 자료를 넣는거다.
        //나는, 여기서의 update는 일반적인 '갱신'의 의미도 있고, '여러 군데 동시에 map으로 올림' 이란 뜻도 있다고 해석함.
        //여기서는 채팅 채널 상에서 보이는 최신 글 , 채팅 창 안에서 보이는 최신글이

        //push 등으로 만든 임의의 값(키)를 가져오는 방법. getKEY!!!!!!!!! 이 키가 채팅방의 고유 id가 될 것임.
        String key = getDatabaseReference().child("bbs").push().getKey();
        //데이터를 쉽게 얻기 위해 map을 획득
        Map<String, Object> data = post.toMap();
        //업데이트 할 내용들을 생성
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("/bbs/"+key, data); //[ "/bbs/"+key ]가 경로가 됨! map의 key에 해당하는 부분이 PATH이고 data 부분이 db path에 들어가는 내용임.
        updates.put("/bbs/"+getUser().getUid()+"/"+key, data);
        //setValue 하면 리스너를 각각 달아야하지만, update 하면 한 번에 여러개를 할 수 있다.
        getDatabaseReference().updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError ==null){
                    Toast.makeText(NewPostActivity.this, "글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Log.e("NewPost", "ERR!");
                }
                stopPd();
            }
        });

        //해당 사용자가 권한이 있는지 검증 - 사용자들이 존재하는 db에서 존재 여부를 검사! - 따라서 유저들 정보를 모은 db를 세팅해야 함.
        //사용자 검사까지 모두 끝났으면 내용을 db에 올림.



    }

    public boolean isValid(String e,String p){
        if(TextUtils.isEmpty(e)){
            postTitle.setError("제목을 넣으세요.");
            return false;
        }
        if(TextUtils.isEmpty(p)){
            postContent.setError("내용이 없습니다.");
            return false;
        }
        return true;
    }

    private void init() {
        postTitle = (EditText) findViewById(R.id.EditText_PostTitle);
        postContent = (EditText) findViewById(R.id.EditText_PostContent);
    }

}
