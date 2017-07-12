package me.blog.eyeballss.myfirebasetest.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import me.blog.eyeballss.myfirebasetest.R;

public class NewPostActivity extends AppCompatActivity {

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

                //버튼을 누르면 사용자가 작성한 게시물을 업로드함.
                submit();


//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void submit() {
        String title = postTitle.getText().toString();
        String content = postContent.getText().toString();

        //작성 내용 검사
        //그릇에 담음
        //해당 사용자가 권한이 있는지 검증 - 사용자들이 존재하는 db에서 존재 여부를 검사! - 따라서 유저들 정보를 모은 db를 세팅해야 함.
        //사용자 검사까지 모두 끝났으면 내용을 db에 올림.


    }

    private void init() {
        postTitle = (EditText) findViewById(R.id.EditText_PostTitle);
        postContent = (EditText) findViewById(R.id.EditText_PostContent);
    }

}
