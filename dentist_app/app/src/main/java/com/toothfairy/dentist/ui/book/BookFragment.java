package com.toothfairy.dentist.ui.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class BookFragment extends Fragment {
    public static BookFragment newInstance(){
        return new BookFragment();
    }

       Calendar calendar = Calendar.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book, container, false);

        final FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.setPersistenceEnabled(true);
        final EditText editName = root.findViewById(R.id.name);
        final EditText editRegiNum= root.findViewById(R.id.regiNum);
        final EditText editPhoneNum= root.findViewById(R.id.phoneNum);

        final CheckBox ch1 = root.findViewById(R.id.ch1);
        final CheckBox ch2 = root.findViewById(R.id.ch2);
        final CheckBox ch3 = root.findViewById(R.id.ch3);
        final CheckBox ch4 = root.findViewById(R.id.ch4);
        final CheckBox ch5 = root.findViewById(R.id.ch5);

        final RadioButton park = root.findViewById(R.id.doctorPark);
        final RadioButton kim = root.findViewById(R.id.doctorKim);

        final TimePicker time = root.findViewById(R.id.time);
        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //TODO
            }
        });

        final CalendarView date = root.findViewById(R.id.calendar);
        date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                view.setDate(calendar.getTimeInMillis());
            }
        });

        root.findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                long regiNum = Integer.parseInt(editRegiNum.getText().toString());
                long phoneNum = Integer.parseInt(editPhoneNum.getText().toString());

                PatientInfo patientInfo = new PatientInfo();
                patientInfo.setName(name);
                patientInfo.setRegiNum(regiNum);
                patientInfo.setPhoneNum(phoneNum);

                mFirebaseDatabase.getReference("patientInfo/")
                        .push()
                        .setValue(patientInfo)//calendar+"/"+
                        .addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(),"ㅎㅇ",Toast.LENGTH_SHORT).show();
                            }
                        });

                //((MainActivity) getActivity()).replaceFragment(SubBookFragment.newInstance());
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
}