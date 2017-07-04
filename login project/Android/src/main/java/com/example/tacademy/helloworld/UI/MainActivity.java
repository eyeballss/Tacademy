package com.example.tacademy.helloworld.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.helloworld.Model.User;
import com.example.tacademy.helloworld.NET.Net;
import com.example.tacademy.helloworld.Utility.MyLogger;
import com.example.tacademy.helloworld.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity ; 화면단위, 하나의 화면은 하나의 액티비티가 담당한다.
 * 기본세팅
 * onCreate() ; 액티비티가 런칭이 된후 매니저가 호출하는 첫번째 메소드
 *              초기화 진행;로드가 많이 걸리지 않는 작업 위주 => 화면이 빨리 떠야 한다.(고객 편의)
 *              반드시 해야 할 부분 : 화면 세팅(setContentView) 진행
 *
 */
public class MainActivity extends AppCompatActivity {


    EditText editText_email;
    EditText editText_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 화면 세팅
        setContentView(R.layout.activity_main);
        // 조작해야 하는 화면의 요소들을 획득한다.
        //View view = findViewById(R.id.loginTitle);
        // view를 TextView 타입으로 형변환 한다.
        //TextView loginTitle = (TextView)view;
        // xml로 구성한 요소를 자바 코드로 끌어 당기는 부분
        TextView loginTitle = (TextView)findViewById(R.id.loginTitle);
        loginTitle.setText("로그인");
        // x버튼을 자바코드에서 제어할수 있게 객체를 구하세요
//        Button button = (Button)findViewById(R.id.button);
//        button.setText("종료");


         editText_email = (EditText)findViewById(R.id.editText_email);
         editText_password = (EditText)findViewById(R.id.editText_password);
    }
    public void onExit(View view){
        finish();
        //MyLogger.i("T", "종료 버튼을 눌렀다");
        // Context를 원하는 자리에는 액티비티 객체를 넣으면 된다
        // 통상 액티비티 코드안에서 액티비티 객체는 this 표현 (자기 자신을 의미)
        //Toast.makeText(this, "버튼 클릭", Toast.LENGTH_SHORT).show();
    }

    public void onLogin(View view){
        MyLogger.log("T","로그인 버튼 누름");
//        Toast.makeText(this, "누름!", Toast.LENGTH_SHORT).show();
        login();
    }

    private void login() {
        String email = editText_email.getText().toString();
        String pwd = editText_password.getText().toString();

        if(TextUtils.isEmpty(email)){
                editText_email.setError("이메일을 입력하세요.");
            }
        if(TextUtils.isEmpty(pwd)){
                editText_password.setError("비밀번호를 입력하세요.");
            }

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)) {

            Call<List<User>> res = Net.getInstance().getMemberFactoryIm().login(email, pwd);
            res.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null){ //null 뿐 아니라 오류 값이 들어올 때도 처리해줘야 함.
                            List<User> users = response.body(); //body()는, json 으로 컨버팅되어 객체에 담겨 지정되로 리턴됨.
                            //여기서는 지정을 Call<지정타입> 이므로 List<User> 가 리턴타입이 됨.


                            try{

                                MyLogger.log("Main 통신 받음", response.body().get(0).toString());


                                Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
                                startActivity(intent);
                            }catch(IndexOutOfBoundsException e){
                                Toast.makeText(MainActivity.this, "아이디 혹은 비밀번호가 잘못 되었습니다.", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            MyLogger.log("Main 통신", "실패 1 response 내용이 없음");
                        }
                    }else{
                        MyLogger.log("Main 통신", "실패 2 서버 에러");
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    MyLogger.log("Main 통신", "실패 3 통신 에러 "+t.getLocalizedMessage());
                }
            });




        }

    }

    public void onJoin(View view){
        MyLogger.log("T","회원가입 버튼 누름");

        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }



}

















