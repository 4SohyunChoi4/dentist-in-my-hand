package com.toothfairy.dentist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        int m = intent.getExtras().getInt("m");
        int d = intent.getExtras().getInt("d");
        int h = intent.getExtras().getInt("h");
        int id = intent.getIntExtra("id",0);

        NotificationHelper notificationHelper = new NotificationHelper(context);

       NotificationCompat.Builder nb;
        switch (id) {
            case 1:
                nb = notificationHelper.getChannelNotification1(m, d, h);
                notificationHelper.getManager().notify(1, nb.build());
                break;

            case 2:
                nb = notificationHelper.getChannelNotification2(h);
                notificationHelper.getManager().notify(2, nb.build());
                break;
        }


    }
}
        /*
        Log.i(TAG, "onReceive");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, BookFragment.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pending = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);

            String channelName = "전날 알람 채널";
            String description = "그 전날 알림을 띄운다";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            if (notificationManager != null)
                notificationManager.createNotificationChannel(channel);
        } else {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }
        builder.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("{Time to watch some cool stuff!}")
                .setContentTitle("숙명치과")
                .setContentText("내일 치과 방문 잊지 말아주세요.")
                .setContentIntent(pending);

        if (notificationManager != null) {
            // 노티피케이션 동작시킴
            notificationManager.notify(1234, builder.build());

        }
    }
 */