package com.toothfairy.dentist;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "dentist_ch";
    public static final String channelName = "내 손안의 치과";
    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannelNotification(int yesterd_m, int yesterd_d, int yesterd_h ) {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("숙명치과")
                .setContentText("내일 "+yesterd_m+"월 "+yesterd_d+"일 "+ yesterd_h+"시 치과 방문 잊지 말아주세요.") // nn월 nn일 nn시
                .setSmallIcon(R.drawable.ic_launcher_background);
    }
}