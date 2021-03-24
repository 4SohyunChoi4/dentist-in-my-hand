package com.toothfairy.dentist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;

public class DeviceBootReceiver extends BroadcastReceiver {
    private static final String TAG = AlarmReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "onReceive");

        if(Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")){
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            /*PendingIntent pending = PendingIntent.getBroadcast(context, 1 , alarmIntent, 0);
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            if ( manager != null)
            {
                manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
            }

             */

        }
    }
}
