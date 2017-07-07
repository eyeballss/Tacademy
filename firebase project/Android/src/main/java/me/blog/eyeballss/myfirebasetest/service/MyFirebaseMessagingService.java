package me.blog.eyeballss.myfirebasetest.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import me.blog.eyeballss.myfirebasetest.MainActivity;
import me.blog.eyeballss.myfirebasetest.R;

/**
 * Created by Tacademy on 2017-07-07.
 * 메세지를 수신하는 서비스. fcm 메세지 도착 시 자동 호출
 * 메세지 우선순위 high여야 메세지가 notify 된다. 안드로이드 6.0 이상에서는 저전력이 위주이기 때문.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    //서버로부터의 메세지를 수신
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("FCMMessagingService", "From: " + remoteMessage.getFrom());

        //remoteMessage.getData().get("msg") 자체가 Json이므로 Gson으로 파싱하여 받으면 됨.
        //raw 데이터가 data= ..으로 나온다는 의미는 map 이라는 의미이므로 이렇게하면 집합에서 Json만 가져온다.
        Log.e("FCMMessagingService", "Raw Message: " + remoteMessage.getData().get("data"));
        Gson gson = new Gson();
        ResPushModel res = gson.fromJson(remoteMessage.getData().get("data"), ResPushModel.class);
        showNotification(res);

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("FCMMessagingService", "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("FCMMessagingService", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    //사용자에게 noti 알림. 상태바에 알림으로 갈꺼야.
    private void showNotification(ResPushModel res){

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder nb = new NotificationCompat.Builder(this)
                //알림 아이콘
                .setSmallIcon(R.mipmap.ic_launcher_round)
                //알림 제목
                .setContentTitle(res.title)
                .setContentText(res.body)
                //취소가 되게 할 것인지 말게 할 것인지
                .setAutoCancel(true)
                //알람음
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //알람을 클릭했을 때 앱의 액티비티를 띄우는 부분
                .setContentIntent(pendingIntent);

        //시스템아, 네가 갖고 있는 서비스 중에서 noti에 관한 서비스를 내게 줘.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //noti 발동 코드.
        // 0 : noti의 고유번호. 눌러서 시작하면 해당 번호를 넣어 알림 삭제.
        notificationManager.notify(0, nb.build());
    }
}

class ResPushModel {
    String title;
    String body;
}