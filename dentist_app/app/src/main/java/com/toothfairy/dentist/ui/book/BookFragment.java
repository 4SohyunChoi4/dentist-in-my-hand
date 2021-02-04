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

       //Calendar calendar = Calendar.getInstance()
    int year, month, day;
    int hour;
    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();

    Dialog dialog;


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

        final CalendarView calendarView = root.findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int y, int m, int d) {
                year = y;
                month = m;
                day = d;
                //calendar.set(y, m, d);
                //view.setDate(calendar.getTimeInMillis());

                //여기에 dialog 추가해야 함
                showDialog();
            }
        });

        root.findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    public void showDialog() {
        dialog.show();

        EditText editName = getView().findViewById(R.id.name);
        EditText editRegiNum= getView().findViewById(R.id.regiNum);
        EditText editPhoneNum= getView().findViewById(R.id.phoneNum);

        final String name = editName.getText().toString();
        final long regiNum = Integer.parseInt(editRegiNum.getText().toString());
        final long phoneNum = Integer.parseInt(editPhoneNum.getText().toString());
        (dialog.findViewById(R.id.okBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PatientInfo patientInfo = new PatientInfo();
                patientInfo.setName(name);
                patientInfo.setRegiNum(regiNum);
                patientInfo.setPhoneNum(phoneNum);

                mFirebaseDatabase.getReference(year+"Y/"+month+"M/"+day+"D/"+hour+"h/")
                        .push()
                        .setValue(patientInfo)//calendar year+"/"+patientInfo
                        .addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(),"예약이 완료됨",Toast.LENGTH_SHORT).show();
                            }
                        });
                Toast.makeText(getActivity(),"완료",Toast.LENGTH_SHORT).show();
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