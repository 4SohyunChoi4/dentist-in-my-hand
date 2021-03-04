package com.toothfairy.dentist.ask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.PatientID;
import com.toothfairy.dentist.R;

import java.text.SimpleDateFormat;
import java.util.*;

public class SubAskFragment extends Fragment {

    public static SubAskFragment newInstance() {
        return new SubAskFragment();
    }

    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AskViewModel askViewModel = ViewModelProviders.of(this).get(AskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ask_sub, container, false);
        final TextView textView = root.findViewById(R.id.title);
        askViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        final EditText editContent = root.findViewById(R.id.editContent);

        root.findViewById(R.id.btnUpload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editContent.getText().toString();
                Ask ask = new Ask();
                ask.setContent(content);
                ask.setName(getPatientName());
                ask.setCreateTime(getTimeToString());
                mFirebaseDatabase.getReference("ask/")
                        .push()
                        .setValue(ask)
                        .addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "글 작성이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                ((MainActivity) getActivity()).replaceFragment(AskFragment.newInstance());
                            }
                        });
            }
        });


        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(AskFragment.newInstance());
            }
        });
        return root;
    }

    private String getPatientName() {
        String name = null;
        Bundle bundle = getArguments();  //번들 받기. getArguments() 메소드로 받음.

        if(bundle != null){
            name = bundle.getString("name"); //Name 받기.
        }
        return name;
    }

    private String getTimeToString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMddHHmm", Locale.KOREA);
        //"20213311941"
        Date currentTime = new Date();
        String dTime = formatter.format(currentTime);
        return dTime;
    }
}