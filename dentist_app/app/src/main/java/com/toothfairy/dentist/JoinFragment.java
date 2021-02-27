package com.toothfairy.dentist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.toothfairy.dentist.ui.intro.IntroFragment;

import java.util.Objects;

public class JoinFragment extends Fragment {
    public static JoinFragment newInstance() {
        return new JoinFragment();
    }

    private static FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_join, container, false);

        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });

        mAuth = FirebaseAuth.getInstance();

        final EditText editEmail = root.findViewById(R.id.email);
        final EditText editPasswd = root.findViewById(R.id.passwd);
        final EditText editName = root.findViewById(R.id.name);
        final EditText editRegiNum = root.findViewById(R.id.regiNum);
        final EditText editPhoneNum = root.findViewById(R.id.phoneNum);

        root.findViewById(R.id.join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String passwd = editPasswd.getText().toString();
                final String name = editName.getText().toString();
                final long regiNum = Integer.parseInt(editRegiNum.getText().toString());
                final long phoneNum = Integer.parseInt(editPhoneNum.getText().toString());

                mAuth.createUserWithEmailAndPassword(email, passwd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    PatientID patient = new PatientID();
                                    patient.setName(name);
                                    patient.setRegiNum(regiNum);
                                    patient.setPhoneNum(phoneNum);
                                    user = mAuth.getCurrentUser();
                                    updateDatabase(patient);
                                } else {
                                    Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    ((MainActivity) getActivity()).updateUI(null);
                                }

                            }
                        });

            }
        });
        return root;
    }

    private void updateDatabase(PatientID patient) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.getReference("patient/" + user.getUid())
                .setValue(patient)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
                        Toast.makeText(getActivity(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
                        ((MainActivity) getActivity()).updateUI(user);

                    }
                });
    }
}