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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.toothfairy.dentist.ui.intro.IntroFragment;

import java.util.Objects;
import java.util.concurrent.Executor;

public class JoinFragment extends Fragment {
    public static JoinFragment newInstance() {
        return new JoinFragment();
    }

    private FirebaseAuth mAuth;

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

        //final EditText editid = root.findViewById(R.id.id);
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
                Toast.makeText(getActivity(),email+passwd,Toast.LENGTH_SHORT).show();
                //String name = editName.toString();
                //long regiNum = Integer.parseInt(editRegiNum.getText().toString());
                //long phoneNum = Integer.parseInt(editPhoneNum.getText().toString());

                //PatientInfo patientInfo = new PatientInfo();
                //patientInfo.setName(name);
                //patientInfo.setRegiNum(regiNum);
                //patientInfo.setPhoneNum(phoneNum);

                mAuth.createUserWithEmailAndPassword(email, passwd)
                        .addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "createUserWithEmail:success");
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });

            }
        });
        return root;
    }
}