package com.toothfairy.dentist.book;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.toothfairy.dentist.*;
import com.toothfairy.dentist.R;

import java.util.Calendar;

public class BookActivity extends AppCompatActivity {

    FirebaseUser user;
    FirebaseDatabase mFirebaseDatabase;
    private ListView listView;
    private ListViewDialogAdapter adapter;
    Dialog dialog;
    String[] weekDay = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};
    int y, m, d, h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        RadioButton etc = findViewById(R.id.etc);

        /*findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (MainActivity.context).replaceFragment(IntroFragment.newInstance());
            }
        });
         */
        etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.etcEdit).setVisibility(View.VISIBLE);
            }
        });
        dialog = new Dialog(BookActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_book);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);

        adapter = new ListViewDialogAdapter();

        listView = dialog.findViewById(R.id.listView);
        final RadioGroup rdGroup = findViewById(R.id.rdGroup);
        final CalendarView calendarView = findViewById(R.id.calendar);
        final Calendar calendar = Calendar.getInstance();
        long startOfMonth = calendar.getTimeInMillis();
        calendarView.setMinDate(startOfMonth);
        calendarView.setMaxDate(startOfMonth + 1000L * 60 * 60 * 24 * 14);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                Calendar subCalendar = Calendar.getInstance();
                subCalendar.set(year, month, dayOfMonth);
                int dayOfWeek = subCalendar.get(Calendar.DAY_OF_WEEK) - 1;
                    if (dayOfWeek != 0 && dayOfWeek != 6)
                showDialog(year, month, dayOfMonth, dayOfWeek);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this);
                builder.setMessage("정말로 예약하시겠습니까?");
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                final ListViewDialogItem item = (ListViewDialogItem) parent.getItemAtPosition(position);

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int which) {
                        setBook(rdGroup.getCheckedRadioButtonId(), item.getTime());
                    }
                });
                builder.create().show();
            }

        });
    }

    private void setBook(int subjectID, final int hour) {
        final String checkboxNum = getCheckbox(subjectID);
        final TextView detailEdit = findViewById(R.id.detailEdit);

        final BookInfo bookInfo = new BookInfo();
        mFirebaseDatabase.getReference("patient/" + user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PatientID patient = snapshot.getValue(PatientID.class);
                bookInfo.setName(patient.getName());
                bookInfo.setPhoneNum(patient.getPhoneNum());
                bookInfo.setTime(hour);
                bookInfo.setSubject(checkboxNum);
                bookInfo.setDetail(detailEdit.getText().toString());
                saveBook(bookInfo, hour);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        final MyBookList myBookList = new MyBookList();
        myBookList.setYear(y);
        myBookList.setMonth(m);
        myBookList.setDayOfMonth(d);
        myBookList.setHour(hour);
        myBookList.setDetail(bookInfo.getDetail());
        myBookList.setSubject(bookInfo.getSubject());
        addMyBookList(myBookList);

        finish();
    }

    private void saveBook(BookInfo bookInfo, final int hour) {
        mFirebaseDatabase.getReference("book/" + y + "/" + m + "/" + d)// + item.getTime() + "/")
                .push()
                .setValue(bookInfo)
                .addOnSuccessListener(BookActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //name, phonenum, booklist 빼고, 날짜와 시간을 넣어야 함
                        //setYesterdayAlarm(m, d, hour);
                        setOneHourAgoAlarm(m, d, hour);
                    }
                });
        bookInfo.setHere(false);
        mFirebaseDatabase.getReference("receipt/" + y + "/" + m + "/" + d)// + item.getTime() + "/")
                .push()
                .setValue(bookInfo)
                .addOnSuccessListener(BookActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dialog.dismiss();
                        adapter.clear();
                        //name, phonenum, booklist 빼고, 날짜와 시간을 넣어야 함
                        Toast.makeText(getApplicationContext(), "예약이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addMyBookList(MyBookList myBookList) {
        Log.d("addMyBookList", "addMyBookList");
        mFirebaseDatabase.getReference("patient/" + user.getUid() + "/" + "bookList/")//+date+"/")
                .push()
                .setValue(myBookList);
        //.addOnSuccessListener(activity, new OnSuccessListener<Void>() { //여기서 자꾸 에러가 남.
        //    @Override
        //    public void onSuccess(Void unused) {
        //    }
        // });
    }

    private String getCheckbox(int subjectID) {

        if (subjectID == 0x7f0900b1) {
            EditText etcEdit = findViewById(R.id.etcEdit);
            return etcEdit.getText().toString();
        } else {
            RadioButton rb = findViewById(subjectID);
            return rb.getText().toString();
        }

    }

    private void setYesterdayAlarm(int m, int d, int hour) {
        PackageManager pm = getPackageManager();
        ComponentName receiver = new ComponentName(BookActivity.this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(BookActivity.this, AlarmReceiver.class);
        alarmIntent.putExtra("m", m);
        alarmIntent.putExtra("d", d);
        alarmIntent.putExtra("h", hour);
        alarmIntent.putExtra("id", 1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(BookActivity.this, 1, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar yesterdayCalendar = Calendar.getInstance();
        yesterdayCalendar.set(Calendar.YEAR, y);
        yesterdayCalendar.set(Calendar.MONTH, m - 1);
        yesterdayCalendar.set(Calendar.DATE, d - 1);
        yesterdayCalendar.set(Calendar.HOUR_OF_DAY, h);
        yesterdayCalendar.set(Calendar.MINUTE, 0);
        yesterdayCalendar.set(Calendar.SECOND, 0);

        if (alarmManager != null) {
            //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, yesterdayCalendar.getTimeInMillis(), pendingIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, yesterdayCalendar.getTimeInMillis(), pendingIntent);
        }
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }


    private void setOneHourAgoAlarm(int m, int d, int hour) {
        PackageManager pm = getPackageManager();
        ComponentName receiver = new ComponentName(BookActivity.this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(BookActivity.this, AlarmReceiver.class);
        //alarmIntent.putExtra("m",m);
        //alarmIntent.putExtra("d",d);
        alarmIntent.putExtra("h", hour);
        alarmIntent.putExtra("id", 2);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(BookActivity.this, 2, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(Calendar.YEAR, y);
        todayCalendar.set(Calendar.MONTH, m - 1);
        todayCalendar.set(Calendar.DATE, d);
        todayCalendar.set(Calendar.HOUR_OF_DAY, h); //hour-1
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);

        if (alarmManager != null) {
            //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, todayCalendar.getTimeInMillis(), pendingIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, todayCalendar.getTimeInMillis(), pendingIntent);
        }
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    public void showDialog(int year, int month, int dayOfMonth, int dayOfWeek) { //월, 연
        adapter.clear();
        dialog.show();
        TextView dialogTitle = dialog.findViewById(R.id.dialogTitle);
        dialogTitle.setText((month + 1) + "월" + dayOfMonth + "일" + weekDay[dayOfWeek]);
        y = year;
        m = month + 1;
        d = dayOfMonth;
        String ref = "Book/" + year + "/" + month + "/" + dayOfMonth + "/";

        int max = 17;
        if (dayOfWeek == 2 || dayOfWeek == 4) // 화요일이나 목요일일 때
            max = 20;

        for (int i = 9; i <= max; i++) {
            if (i == 12)
                continue;
            adapter.addItem(ref, i);
        }
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        (dialog.findViewById(R.id.cancelBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


}