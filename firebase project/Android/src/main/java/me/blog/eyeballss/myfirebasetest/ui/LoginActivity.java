package me.blog.eyeballss.myfirebasetest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.blog.eyeballss.myfirebasetest.R;
import me.blog.eyeballss.myfirebasetest.model.User;

/*
firebase로 인증 기능 만들기
 - 익명 인증
 - 이메일,비번 인증
 - 전번 인증
 - social media 인증
 - 익명에서 이메일,비번 및 social media 로 인증 전환
 - 전화, 문자 인증(1회성)
 - 기타 인증(카톡, 네이버 등)
 */
public class LoginActivity extends RootActivity {

    LoginActivity self;
    FirebaseAuth firebaseAuth;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        self=this;
        email= (EditText) findViewById(R.id.EditText_Email);
        password = (EditText) findViewById(R.id.EditText_Password);
        initFirebaseAuth();
        initToolbar();
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = getUser();
        if(user==null) return;
        Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
        goToChatService();

    }

    private boolean result;
    private boolean signInAnonymously(){
        showPd();
        result = false;
        firebaseAuth.signInAnonymously()
                //익명 로그인 서버 처리가 완료된 후
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //성공했을 때
                        if(task.isSuccessful()){
                            FirebaseUser user = getUser();
                            if(user==null) return;
                            Log.d("LoginActivity", "익명 계정 생성 성공 : "+user.getUid());
                            Toast.makeText(self, "익명 계정 생성 성공", Toast.LENGTH_SHORT).show();
//                            goToChatService();
                            result = true;

                            //실패했을 때
                        }else{
                            Toast.makeText(self, "익명 계정 생성 실패", Toast.LENGTH_SHORT).show();
                            result = false;
                        }
                        stopPd();
                    }
                });

        return result;
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인이 안 되어 있을 때
                if(getUser()==null){
                    if(!signInAnonymously()) {
                        Toast.makeText(self, "익명으로 로그인 하지 못하였습니다.", Toast.LENGTH_SHORT).show();
                    }

                    //로그인.
                    Toast.makeText(self, "익명으로 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                    goToChatService();

                }
                //로그인 되어있을 때
                else{
                    firebaseAuth.signOut(); //로그아웃.
                    Toast.makeText(self, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

                }

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void initFirebaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance();

    }



    //이메일 패스워드로 계정 만들기
    public void onCreateEmailNPassword(View view){
        HashMap<String, String> info = (HashMap<String, String>) isValid();
        if(info==null) return;

        firebaseAuth.createUserWithEmailAndPassword(info.get("email"), info.get("password")).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    getUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                //사실 sendEmailVerification으로 비정상 이메일을 걸러야하는데 거르질 못 하고 있음.
                                Toast.makeText(self, "이메일로 계정 생성 성공", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(self, "비정상적인 이메일입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(self, "이메일로 계정 생성 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    //이메일로 로그인 할 수 있게 하자.
    public void onSignUpByEmailNPassword(View view){
        HashMap<String, String> info = (HashMap<String, String>) isValid();
        if(info==null) return;

        showPd();

        firebaseAuth.signInWithEmailAndPassword(info.get("email"),info.get("password")).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("LoginActivity", "email로 로그인 성공");
                    //이메일로 로그인할 때 db에 회원 정보를 저장!
                    insertUserInfo();
                    goToChatService();

                }
                else{
                    Log.d("LoginActivity", "email로 로그인 실패");
                }
                stopPd();
            }
        });
    }

    //익명 계정을 이메일로 로그인 할 수 있게 하자.
    public void onSignUpFromAnoToEmail(View view){
        HashMap<String, String> info = (HashMap<String, String>) isValid();
        if(info==null) return;

        getUser().linkWithCredential(EmailAuthProvider.getCredential(info.get("email"),info.get("password"))).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){ // 익명을 이메일로 로그인 링크 처리가 성공했다면
                    Log.d("LoginActivity", "익명에서 이메일로 링크 성공");
                }else{ //실패했다면
                    Log.d("LoginActivity", "익명에서 이메일로 링크 실패");

                }
            }
        });
    }

    public Map isValid(){
        String e =email.getText().toString();
        String p =password.getText().toString();
        if(TextUtils.isEmpty(e) || !e.contains("@")){
            email.setError("이메일을 똑바로 넣으세요.");
            return null;
        }
        if(TextUtils.isEmpty(password.getText().toString()) || password.length()<6){
            password.setError("비밀번호를 6자리 이상 넣으세요.");
            return null;
        }

        Map<String,String> map = new HashMap<String,String>();
        map.put("email", e);
        map.put("password", p);
        return map;
    }

    private void insertUserInfo() {

        User userInfo = new User(getUser().getEmail(), "token", getUser().getEmail().split("@")[0],"",System.currentTimeMillis(),"");


        //여기서 push를 쓰지 않는 이유 ,
        //push는 users 내에 임의의 값을 갖는 방을 만들고 각각의 방마다 고유 데이터(userinfo)를 넣기 위함인데,
        //여기선 우리가 자체적으로 임의의 값을 갖고 있기 때문에 push를 하지 않아도 됨.
        //마치 임의의 값의 방이 primary key 인 마냥.
        //push 라는 이름의 카테고리가 하나 더 있다고 생각해도 되겠다. /bbs/push/user 이렇게.
        getDatabaseReference().child("users").child(getUser().getUid()).setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(self, "유저 정보 db 삽입 성공!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(self, "유저 정보 db 삽입 실패!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //비번 초기화
    public void onResetPasswordByEmail(View view){
        String e =email.getText().toString();
        if(TextUtils.isEmpty(e) || !e.contains("@")){
            email.setError("이메일을 똑바로 넣으세요.");
            return;
        }

        firebaseAuth.sendPasswordResetEmail(e);
        Toast.makeText(self, "[ "+e+" ]로\n비번 초기화 메일을 보냈습니다.", Toast.LENGTH_SHORT).show();
    }

    public void goToChatService(){
//        Intent intent = new Intent(this, SimpleChatActivity.class);
        Intent intent = new Intent(this, BBSActivity.class);
        startActivity(intent);
        finish();
    }
}


/*
이메일 비번으로 회원 가입
firebaseAuth.createUserWithEmailAndPassword();
이메일 검증(이메일이 이미 있는지 없는지?)
currentUser().sendEmailVerification();
비번 초기화
firebaseAuth.sendPasswordResetEmail();
 */