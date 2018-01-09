package com.henallux.namikot;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.henallux.namikot.Controller.MessagesFragment;

/**
 * Created by Maurine on 8/01/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public MyFirebaseMessagingService(){

    }

    //Token :  eqY1CFtskZQ:APA91bEMIFqSCOcxqcYfROmPLt_w1GXFq5jqHMdPkHZRYJb-lugJhLM4vlvVDJbsezCGFVdAmBk56vLnPgyKuF41iTGBRV2kfo-6nbUqqty0TxXk1bAoMH6eEX4jekm5q5RGShg3Q299

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setSmallIcon(R.mipmap.home_icon)
                .build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(123, notification);
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MessagesFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 // Request code
                , intent, PendingIntent.FLAG_ONE_SHOT);

        //String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_stat_ic_notification)
                        .setContentTitle("FCM Message")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 // ID of notification
                    , notificationBuilder.build());
    }
}
