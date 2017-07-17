package com.example.tacademy.fbtest186.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tacademy.fbtest186.R;
import com.example.tacademy.fbtest186.model.User;
import com.example.tacademy.fbtest186.util.U;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * FireBase를 이용한 인증 처리
 * - 익명 인증
 * - 이메일, 비번 인증
 * - 익명인증의 이메일, 비번 계정으로 전환 처리
 * - 추후
 * - 전번 인증, sms 인증(1회성 비번)
 * - 쇼셜 인증 (차후 확인 : 구글, 페북)
 * - 기타 국내 인증 (카톡,네이버)
 */
public class LoginActivity extends RootActivity {

    // 인증 관련 라이브러리 객체 획득
    FirebaseAuth firebaseAuth;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 초기화
        firebaseAuth = FirebaseAuth.getInstance();
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 익명 로그인한 유저 객체 획득 => 로그아웃을 하지 않앗다면 객체(세션) 획득 가능
        //FirebaseUser user = getUser();
        if (getUser() != null) {
            Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_LONG).show();
            goChatService();
        }
    }
    public void goChatService(){
        //startActivity(new Intent(this, SimpleChatActivity.class));
        startActivity(new Intent(this, BBSActivity.class));
        finish();
    }
    public void  initUI()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 익명 로그인을 수행해라
                if( getUser() != null ){    // 로그인중인데 눌렀다
                    firebaseAuth.signOut(); // 로그아웃
                    Toast.makeText(LoginActivity.this, "로그아웃 성공", Toast.LENGTH_LONG).show();
                }else{      // 로그아웃 중인데 눌렀다
                    anonymouslySignUp();
                }

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });
        // 버튼 이벤트 : 익명계정과 이메일 비번을 연결
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onAnLinkEmail();
            }
        });
        // 이메일로 가입
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onEmailSignUp();
            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onEmailJoin();
            }
        });
        email    = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
    }
    public void anonymouslySignUp()
    {
        showPD();
        // 익명 로그인을 진행하고 그 결과를 비동기적으로 받아서 결과를 리스너 객체를 통해 전달한다.
        firebaseAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful() ){
                    // 성공 => 회원 정보 획득
                    FirebaseUser user = getUser();//firebaseAuth.getCurrentUser();
                    if( user != null){
                        U.getInstance().log( user.getUid() );
                        U.getInstance().log( user.getEmail() );
                    }
                    Toast.makeText(LoginActivity.this, "익명계정 생성 성공", Toast.LENGTH_LONG).show();
                    goChatService();
                }else{
                    // 실패
                    Toast.makeText(LoginActivity.this, "익명계정 생성 실패", Toast.LENGTH_LONG).show();
                }
                stopPD();
            }
        });
    }
    // 익명계정으로부터 이메일 전환 관련
    // 1. 이메일, 비번 입력
    // 2. 유효성 검사(TextUtil.isEmpty()) EditText.setError()
    // 3. FB Auth 중 ?메소드를 통해서 처리!! -> 비동기 ->
    public void onAnLinkEmail(){
        if( !isVaild() )  return;
        // 익명계정과 이메일비번을 연결하는 구간
        String email    = this.email.getText().toString();
        String password = this.password.getText().toString();
        // 이메일 인증 공급자로부터 이메일비번을 제공하여 인증 자격을 획득함 : EmailAuthProvider.getCredential
        getUser().linkWithCredential(EmailAuthProvider.getCredential(email, password))
                 .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if( task.isSuccessful() ){
                             // 연결이 성공되엇으니, 허가된 서비로 이동
                             U.getInstance().log("연결 완료");
                             Toast.makeText(LoginActivity.this, "이메일연결 성공", Toast.LENGTH_LONG).show();
                             goChatService();
                         }else{
                             // 실패 사유 보여주기
                             Toast.makeText(LoginActivity.this, "이메일연결 실패", Toast.LENGTH_LONG).show();
                         }
                     }
                 });
    }
    // 이메일 비번으로부터 가입 및 로그인
    // 1. 이메일, 비번 입력
    // 2. 유효성 검사(TextUtil.isEmpty()) EditText.setError()
    // 3. FB Auth 중 ?메소드를 통해 처리!! -> 비동기 ->
    public void onEmailSignUp(){
        if( !isVaild() )  return;
        // 이메일 링크 성공하신 분들은
        // 1. 로그아웃(로그인시에만 수행)
        // 2. 이메일, 비번 입력후 로그인 버튼하나 추가하여
        // 3. 클릭시 아래 함수를 호출하여 로그인되게 처리
        showPD();
        String email    = this.email.getText().toString();
        String password = this.password.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if( task.isSuccessful() ){
                                Toast.makeText(LoginActivity.this, "이메일 로그인 성공", Toast.LENGTH_LONG).show();
                                goChatService();
                            }else{
                                Toast.makeText(LoginActivity.this, "이메일 로그인 실패", Toast.LENGTH_LONG).show();
                            }
                            stopPD();
                        }
                    });
    }
    public boolean isVaild()
    {
        String email    = this.email.getText().toString();
        String password = this.password.getText().toString();
        if(TextUtils.isEmpty(email)) {
            this.email.setError("이메일을 입력하세요");
            return false;
        }
        if(TextUtils.isEmpty(password)) {
            this.password.setError("비밀번호를 입력하세요");
            return false;
        }
        return true;
    }
    public void onEmailJoin()
    {
        if( !isVaild() )  return;
        // 참고
        showPD();
        String email    = this.email.getText().toString();
        String password = this.password.getText().toString();
        // 이메일 비번으로 회원가입 ==================================================================
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if( task.isSuccessful() ){
                                // 실시간 디비에 회원 정보 삽입
                                insertUserInfo();
//                                goChatService();
//                                // 이메일 검증 ======================================================
//                                // 차후 적용 검토
//                                getUser().sendEmailVerification()
//                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                if( task.isSuccessful() ){
//                                                    Toast.makeText(LoginActivity.this, "정상이메일 가입완료", Toast.LENGTH_LONG).show();
//                                                }else{
//                                                    Toast.makeText(LoginActivity.this, "비정상이메일", Toast.LENGTH_LONG).show();
//                                                }
//                                                stopPD();
//                                            }
//                                        });
//                                // =================================================================
                            }else{
                                Toast.makeText(LoginActivity.this, "가입실패", Toast.LENGTH_LONG).show();
                                stopPD();
                            }
                        }
                    });
        // =========================================================================================
        // 비번 초기화
        //firebaseAuth.sendPasswordResetEmail();
    }
    public void insertUserInfo(){
        // 1. 그릇에 데이터 담기 kkk@k.com
        User user = new User(
                getUser().getEmail(),
                getUser().getEmail().split("@")[0],
                "",
                "",
                System.currentTimeMillis()
                );
        // 2. 디비 경로 에맞춰서 경로를 정의하고 데이터를 추가
        FirebaseDatabase firebaseDatabase   = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(); // => /
        databaseReference.child("users")
                         .child(getUser()
                         .getUid())
                         .setValue(user)
                         .addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 // 3. 성공과 실패 여부에 따라 이동
                                 if( task.isSuccessful() ){
                                     Toast.makeText(LoginActivity.this, "회원가입및 로그인 성공", Toast.LENGTH_LONG).show();
                                     goChatService();
                                 }else{

                                 }
                             }
                         });

    }
}

















