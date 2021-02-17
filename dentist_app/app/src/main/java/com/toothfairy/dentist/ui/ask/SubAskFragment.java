package com.toothfairy.dentist.ui.ask;

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
import com.google.firebase.database.FirebaseDatabase;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.ui.intro.IntroFragment;

import java.util.Date;
import java.util.Objects;

public class SubAskFragment extends Fragment {


    public static SubAskFragment newInstance(){
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
            public void onClick(View v) {//
                String content  = editContent.getText().toString();
                AskInfo askInfo = new AskInfo();
                askInfo.setContent(content);
                askInfo.setUploadDate(new Date().getTime());
                askInfo.setAnswered(false);
                mFirebaseDatabase.getReference("Ask/")
                        .push()
                        .setValue(askInfo)
                        .addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(),"글 작성이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                                ((MainActivity)getActivity()).replaceFragment(AskFragment.newInstance());
                            }
                        });
            }
        });


       root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ((MainActivity)getActivity()).replaceFragment(AskFragment.newInstance());
           }
       });
        return root;
    }
}