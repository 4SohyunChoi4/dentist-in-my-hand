package com.toothfairy.dentist.ui.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;

public class SubBookFragment extends Fragment {
    public static SubBookFragment newInstance(){
        return new SubBookFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book_sub, container, false);

        //final FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        //mFirebaseDatabase.setPersistenceEnabled(true);
        CalendarView calendarView = root.findViewById(R.id.calendar);
        calendarView.setMinDate(System.currentTimeMillis());

/*
        final EditText editName = root.findViewById(R.id.name);
        final EditText editRegiNum= root.findViewById(R.id.regiNum);
        final EditText editPhoneNum= root.findViewById(R.id.phoneNum);

        root.findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                long regiNum = Integer.parseInt(editRegiNum.getText().toString());
                long phoneNum = Integer.parseInt(editPhoneNum.getText().toString());

                UserInfo userInfo = new UserInfo();
                userInfo.setName(name);
                userInfo.setRegiNum(regiNum);
                userInfo.setPhoneNum(phoneNum);
                mFirebaseDatabase.getReference("patientInfo/")
                        .push()
                        .setValue(userInfo)
                        .addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                ((MainActivity)getActivity()).replaceFragment(SubBookFragment.newInstance());
                            }
                        });
            }
        });

 */



       root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ((MainActivity)getActivity()).replaceFragment(BookFragment.newInstance());
           }
       });
        return root;
    }
}