package com.toothfairy.dentist.ui.book;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.PatientInfo;
import com.toothfairy.dentist.ui.intro.IntroFragment;

import java.util.Calendar;
import java.util.Objects;

public class BookFragment extends Fragment{
    public static BookFragment newInstance(){
        return new BookFragment();
    }

    int hour;
    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();

    private ListView listView;
    private ListViewAdapter adapter;

    Dialog dialog;
    String[] monthTxt = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Dec"};
    String[] weekDay = {"일요일", "월요일","화요일","수요일","목요일","금요일","토요일"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book, container, false);

        mFirebaseDatabase.setPersistenceEnabled(true);


        final CheckBox ch1 = root.findViewById(R.id.ch1);
        final CheckBox ch2 = root.findViewById(R.id.ch2);
        final CheckBox ch3 = root.findViewById(R.id.ch3);
        final CheckBox ch4 = root.findViewById(R.id.ch4);
        final CheckBox ch5 = root.findViewById(R.id.ch5);

        final RadioButton park = root.findViewById(R.id.doctorPark);
        final RadioButton kim = root.findViewById(R.id.doctorKim);

        /*final TimePicker time = root.findViewById(R.id.timePicker);
        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
            }
        });
         */
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_book);

        adapter = new ListViewAdapter();

        listView = dialog.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        adapter.addItem("9시","예약가능");
        adapter.addItem("10시","예약가능");
        adapter.addItem("11시","예약가능");
        adapter.addItem("1시","예약가능");
        adapter.addItem("2시","예약가능");
        adapter.addItem("3시","예약가능");
        adapter.addItem("4시","예약가능");

        adapter.notifyDataSetChanged();

        final CalendarView calendarView = root.findViewById(R.id.calendar);
        final Calendar calendar = Calendar.getInstance();
        long startOfMonth = calendar.getTimeInMillis();
        calendarView.setMinDate(startOfMonth);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(year,month,dayOfMonth);
                int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK)-1;
                showDialog(year, month, dayOfMonth, dayOfWeek);
            }
        });
               root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
                   @Override
                 public void onClick(View v) {
                   ((MainActivity)getActivity()).replaceFragment(IntroFragment.newInstance());
                 }
               });
        return root;
    }

    public void showDialog(final int year, final int month, final int dayOfMonth, final int dayOfWeek) { //월, 연
        dialog.show();

        TextView dialogTitle = dialog.findViewById(R.id.dialogTitle);

        EditText editName = getView().findViewById(R.id.name);
        EditText editRegiNum= getView().findViewById(R.id.regiNum);
        EditText editPhoneNum= getView().findViewById(R.id.phoneNum);

        final String name = editName.getText().toString();
        final long regiNum = Integer.parseInt(editRegiNum.getText().toString());
        final long phoneNum = Integer.parseInt(editPhoneNum.getText().toString());

        dialogTitle.setText((month+1)+"월"+dayOfMonth+"일"+weekDay[dayOfWeek]);

        (dialog.findViewById(R.id.okBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PatientInfo patientInfo = new PatientInfo();
                patientInfo.setName(name);
                patientInfo.setRegiNum(regiNum);
                patientInfo.setPhoneNum(phoneNum);

                mFirebaseDatabase.getReference(year+"Y/"+monthTxt[month]+"/"+dayOfMonth+"D/"+hour+"h/")
                        .push()
                        .setValue(patientInfo)//calendar year+"/"+patientInfo
                        .addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
                dialog.dismiss();
            }
        });
        (dialog.findViewById(R.id.cancelBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}