package com.toothfairy.dentist;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class NotificationHelper extends ContextWrapper {
    public static final String CHANNEL_YESTERDAY_ID = "com.toothfairy.dentist.yesterday";
    public static final String CHANNEL_YESTERDAY_NAME = "전날 알림";
    public static final String CHANNEL_OHA_ID = "com.toothfairy.dentist.yesterday.onehourago";
    public static final String CHANNEL_OHA_NAME = "한시간전 알림";
    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_YESTERDAY_ID, CHANNEL_YESTERDAY_NAME, NotificationManager.IMPORTANCE_HIGH);
        //channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(channel);

        NotificationChannel channel2 = new NotificationChannel(CHANNEL_OHA_ID, CHANNEL_OHA_NAME, NotificationManager.IMPORTANCE_HIGH);
        //channel2.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(channel2);
    }
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannelNotification1(int m, int d, int h ) {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_YESTERDAY_ID)
                .setContentTitle("숙명치과")
                .setContentText("내일 "+m+"월 "+d+"일 "+ h+"시 치과 방문 잊지 말아주세요.") // nn월 nn일 nn시
                .setSmallIcon(R.drawable.ic_launcher_background);
    }
    public NotificationCompat.Builder getChannelNotification2(int h ) {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_OHA_ID)
                .setContentTitle("숙명치과")
                .setContentText("오늘"+ h +"시 치과 방문 잊지 말아주세요.") // nn월 nn일 nn시
                .setSmallIcon(R.drawable.ic_launcher_background);
    }
}