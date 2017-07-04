package com.example.tacademy.helloworld.UI;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tacademy.helloworld.R;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }

    boolean isFirstEnd;
    public void onBackPressed() {
        if (!isFirstEnd) {
            handler.sendEmptyMessageDelayed(0, 2000);
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            isFirstEnd = true;
        } else {
            super.onBackPressed();
        }
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if(msg.what==0){
                isFirstEnd=false;
            }
        }
    };
}
