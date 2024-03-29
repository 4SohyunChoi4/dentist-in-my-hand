package com.toothfairy.dentist;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.toothfairy.dentist.intro.IntroFragment;

import java.util.Objects;

public class LoginFragment extends Fragment {
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });

        mAuth = FirebaseAuth.getInstance();

        final EditText editEmail = root.findViewById(R.id.email);
        final EditText editPasswd = root.findViewById(R.id.passwd);

        root.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((TextUtils.isEmpty(editEmail.getText())) || (TextUtils.isEmpty(editPasswd.getText())))
                    Toast.makeText(getActivity(), "아이디 혹은 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                else {
                    String email = editEmail.getText().toString();
                    String passwd = editPasswd.getText().toString();

                    mAuth.signInWithEmailAndPassword(email, passwd)
                            .addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                       // FirebaseUser user = mAuth.getCurrentUser();
                                        ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getActivity(), "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }

            }
        });
        return root;
    }
}
