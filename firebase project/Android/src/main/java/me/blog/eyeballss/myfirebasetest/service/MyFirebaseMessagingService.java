package me.blog.eyeballss.myfirebasetest.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
        Log.d("FCMMessagingService", "Message: " + remoteMessage.getFrom().toString());

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
}
