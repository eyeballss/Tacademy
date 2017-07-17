package com.example.tacademy.fbtest186.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * FCM 사용을 위한 구성요소중 유저의 token을 획득하는 서비스임
 * token의 신규발급부터 변경시 이벤트를 전달하여 서버쪽으로 갱신하는 역활을 담당한다.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
    // 토큰이 갱신되면 호출된다.
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("FCM", "onTokenRefresh:"+refreshedToken);
        // 서버쪽으로 보내서 갱신 가능함
    }
}















