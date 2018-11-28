package com.example.andrewcruz.parking_app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

public class FireBaseMessengingService extends com.google.firebase.messaging.FirebaseMessagingService {
    static final String TAG = "MESSEGING SERv";
    public FireBaseMessengingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "RECIEVED MESSEGE");
        String title = remoteMessage.getNotification().getTitle();
        String messegae = remoteMessage.getNotification().getBody();

        sendNotification(title,messegae);
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification(String title, String messageBody) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// The id of the channel.
        String id = "my_channel_01";

// The user-visible name of the channel.
        CharSequence name = "CHANNEL";

// The user-visible description of the channel.
        String description = "NAME OF CHANNEL";

        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel mChannel = new NotificationChannel(id, name,importance);

// Configure the notification channel.
        mChannel.setDescription(description);

        mChannel.enableLights(true);
// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
        mChannel.setLightColor(Color.RED);

        mChannel.enableVibration(true);
        mChannel. (new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        assert mNotificationManager != null;
        mNotificationManager.createNotificationChannel(mChannel);

        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        int notifyID = 1;

        String CHANNEL_ID = "my_channel_01";

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_parking_saved_icon)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(pendingIntent);


        mNotificationManager.notify(notifyID /* ID of notification */, notificationBuilder.build());
    }
}
