package com.example.tacademy.helloworld.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.helloworld.Model.User;
import com.example.tacademy.helloworld.NET.Net;
import com.example.tacademy.helloworld.NET.Req_join;
import com.example.tacademy.helloworld.NET.Res_join;
import com.example.tacademy.helloworld.R;
import com.example.tacademy.helloworld.Utility.MyLogger;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.password;
import static com.example.tacademy.helloworld.R.id.editText_email;
import static com.example.tacademy.helloworld.R.id.editText_password;


/*
서버는 /join 으로.
post 방식으로 보내보자.
 */
public class JoinActivity extends AppCompatActivity {

    EditText editText_get_email;
    EditText editText_get_password;
    TextView term;

    String email;
    String pwd ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        editText_get_email = (EditText)findViewById(R.id.editText_get_email);
        editText_get_password = (EditText)findViewById(R.id.editText_get_password);
        term = (TextView)findViewById(R.id.term);







    }

    Call<Res_join> res;
    public void onMadeAccount(View view){
        if(!duplication){
            Toast.makeText(this, "중복 체크 하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        email = editText_get_email.getText().toString();
        pwd = editText_get_password.getText().toString();

        if(TextUtils.isEmpty(email)){
            editText_get_email.setError("이메일을 입력하세요.");
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            editText_get_password.setError("비밀번호를 입력하세요.");
            return;
        }

            getInfo();

            //이제 컨테이너에 담자.
            Req_join req_join = new Req_join();
            req_join.setUser(new User(
                    null, //date
                    email, //email
                    0, //id
                    "X500", //model
                    "01000000000", //number
                    "A", //model
                    pwd, //password
                    "", //token
                    "" //uuid firebase fcm 연동 후 설정
            ));

            //서버로 보내는 작업.
            res = Net.getInstance().getMemberFactoryIm().join(req_join);
        res.enqueue(new Callback<Res_join>() {
            @Override
            public void onResponse(Call<Res_join> call, Response<Res_join> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        Res_join users = response.body();

                        MyLogger.log("Join", "회원 가입 성공!");
                        Toast.makeText(JoinActivity.this, "회원 가입 성공!", Toast.LENGTH_SHORT).show();
                        finish();



                    }else{
                        MyLogger.log("Join 통신", "실패 1 response 내용이 없음");
                    }
                }else{
                    try {
                        MyLogger.log("Join 통신", "실패 2 서버 에러 "+ response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Res_join> call, Throwable t) {
                MyLogger.log("Join 통신", "실패 3 통신 에러 "+t.getLocalizedMessage());
            }
        });



    }


    private void getInfo(){

        //사용자의 os, model, uuid, os version 등을 알아야 하는 이유는 지속적인 서비스와 업데이트 및 사용자 관리 때문.

        String result = "이메일 : "+ email+"\n패스워드 : "+pwd;
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }



    public void onShowTerm(View view){
        if(term.getVisibility()==View.INVISIBLE){
            term.setVisibility(View.VISIBLE);
        }else{
            term.setVisibility(View.INVISIBLE);
        }

    }

    private boolean duplication= false;
    public void onDuplicationCheck(View view){
        String email = editText_get_email.getText().toString();
        Call<String> res = Net.getInstance().getMemberFactoryIm().dupl(email);
        res.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body() != null) {
                    String result = response.body().toString();
                    if(!result.equals("1")){ //중복했는데 겹치는게 없음
                        Toast.makeText(JoinActivity.this, "사용 가능한 이메일 입니다.", Toast.LENGTH_SHORT).show();
                        duplication= true;
                    }else{ //중복됨
                        Toast.makeText(JoinActivity.this, "중복된 이메일 입니다.", Toast.LENGTH_SHORT).show();
                        duplication= false;
                    }


                }else{
                    MyLogger.log("Dupl 통신", "실패 2");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLogger.log("Dupl 통신", "실패 3 통신 에러 "+t.getLocalizedMessage());
            }
        });
    }

}


