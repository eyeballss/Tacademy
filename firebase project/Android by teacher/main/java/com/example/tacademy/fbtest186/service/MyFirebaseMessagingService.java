package com.example.tacademy.fbtest186.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.tacademy.fbtest186.MainActivity;
import com.example.tacademy.fbtest186.R;
import com.example.tacademy.fbtest186.model.ResPushModel;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

/**
 * 메시지 수신: fcm 메시지가 도착하면 자동 호출된다.
 * 메시지 우선순위가 high가 아니면 안드6.0이상에서는 백그라운드시 즉각적으로 도달하지 않읋수 있다
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    // 메시지를 수신한다.
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        Log.d("FCM", "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0)
        {
            // remoteMessage.getData().get("data") => json 형태의 String 획득 => 그릇 => 파싱 => 데이터 획득
            ResPushModel res = new Gson().fromJson(remoteMessage.getData().get("data"), ResPushModel.class);
            Log.d("FCM", "Message data payload: " + res.getBody());
            showNotification( res );
        }
    }
    // 사용자에게 푸시가 도착했음을 통보한다.(안테나 영역에 알림으로 처리)
    public void showNotification(ResPushModel res)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        // 알람 구성
        NotificationCompat.Builder nb  = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle( res.getTitle() )
                .setContentText( res.getBody() )
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);
        // 노티 작동
        NotificationManager notificationManager
                = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        // 0:노이티케이션 고유 번호 생각하고 눌러서 시작하면 해당 번호를 넣어서 알림 삭제
        notificationManager.notify(0, nb.build());

    }

}















