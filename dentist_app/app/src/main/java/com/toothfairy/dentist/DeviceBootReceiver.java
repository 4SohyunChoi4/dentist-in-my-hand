package com.toothfairy.dentist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Objects;

public class DeviceBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")){
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent pending = PendingIntent.getBroadcast(context, 0 , alarmIntent, 0);
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            if ( manager != null)
            {
                //manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
            }
        }
    }
}
