package com.toothfairy.dentist;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toothfairy.dentist.intro.IntroFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReceiptFragment extends Fragment {
    FirebaseDatabase mFirebaseDatabase;


    public static ReceiptFragment newInstance() {
        return new ReceiptFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_receipt, container, false);

        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("M", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());

        String year = yearFormat.format(currentTime);
        String month = monthFormat.format(currentTime);
        String day = dayFormat.format(currentTime);
        String hour = hourFormat.format(currentTime);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.getReference("book" + "/" + year + "/" + month + "/" + day + "/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              //  for (DataSnapshot d : snapshot.getChildren()) {
              //      PatientID patientID = d.getValue(PatientID.class);
              //      if (item.getKey() == uid) {
              //          User user = dataSnapshot.getValue(User.class);
              //          array[0] = user;
             //       }
             //   }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return root;
    }
}