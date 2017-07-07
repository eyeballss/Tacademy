package me.blog.eyeballss.myfirebasetest.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Tacademy on 2017-07-07.
 * FCM 사용을 위한 구성요소 중 유저의 토큰을 획득하는 서비스.
 * 토큰의 신규 발급부터 변경시 이벤트를 전달하여 서버쪽으로 전달 갱신.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCM IDService", "onTokenRefresh:"+ refreshedToken);
        //서버로 보내서 갱신
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {

    }
}
