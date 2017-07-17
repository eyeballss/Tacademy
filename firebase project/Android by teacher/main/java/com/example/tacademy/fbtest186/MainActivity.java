package com.example.tacademy.fbtest186;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.tacademy.fbtest186.ui.LoginActivity;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, LoginActivity.class));

        // 최초 구동시에는 onTokenRefresh이 호출되기 전까지 null이다. 한번 호출된 이후에는 값이 나옴
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("FCM", "onTokenRefresh:"+refreshedToken);
    }
}
