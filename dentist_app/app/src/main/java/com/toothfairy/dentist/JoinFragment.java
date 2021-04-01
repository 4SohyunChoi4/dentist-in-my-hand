package com.toothfairy.dentist;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.toothfairy.dentist.intro.IntroFragment;

public class JoinFragment extends Fragment {
    public static JoinFragment newInstance() {
        return new JoinFragment();
    }

    private static FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_join, container, false);

        final Bundle bundleName = new Bundle();
        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });

        mAuth = FirebaseAuth.getInstance();

        final EditText editEmail = root.findViewById(R.id.email);
        final EditText editPasswd = root.findViewById(R.id.passwd);
        final EditText editPasswd2 = root.findViewById(R.id.passwd2);
        final EditText editName = root.findViewById(R.id.name);
        final EditText editRegiNum = root.findViewById(R.id.regiNum);
        final EditText editRegiNum2 = root.findViewById(R.id.regiNum2);
        final EditText editPhoneNum = root.findViewById(R.id.phoneNum);
        final EditText editPhoneNum2 = root.findViewById(R.id.phoneNum2);
        final EditText editPhoneNum3 = root.findViewById(R.id.phoneNum3);


        String[] mail = {
                "naver.com", "google.com", "hanmail.com", "sookmyung.ac.kr"
        };
        final Spinner emailSpinner = root.findViewById(R.id.emailSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                mail);
        emailSpinner.setAdapter(adapter);
        emailSpinner.setSelection(0);

  /*
        editPhoneNum.addTextChangedListener(new TextWatcher() {
            private int beforeLength = 0;
            private int afterLength = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeLength = s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() <= 0){
                    return;
                }
                char inputChar = s.charAt(s.length() - 1);
                if(inputChar != '-' && (inputChar < '0' || inputChar > '9')){
                    editPhoneNum.getText().delete(s.length()-1, s.length());
                    return;
                }
                afterLength = s.length();

                if (beforeLength > afterLength) {
                    // 삭제 중에 마지막에 -는 자동으로 지우기
                    if (s.toString().endsWith("-")) {
                        editPhoneNum.setText(s.toString().substring(0, s.length() - 1));
                    }
                }
                // 입력 중
                else if (beforeLength < afterLength) {
                    if (afterLength == 5 && !s.toString().contains("-")) {
                        editPhoneNum.setText(s.toString().subSequence(0, 3) + "-" + s.toString().substring(4, s.length()));
                    } else if (afterLength == 10) {
                        editPhoneNum.setText(s.toString().subSequence(0, 9) + "-" + s.toString().substring(9, s.length()));
                    } else if (afterLength == 15) {
                        editPhoneNum.setText(s.toString().subSequence(0, 14) + "-" + s.toString().substring(14, s.length()));
                    }
                }
                editPhoneNum.setSelection(editPhoneNum.length());

              if(s.length() == 19) {
                    btnInput.setBackground(
                            ContextCompat.getDrawable(getContext(), R.drawable.btn_active));
                } else {
                    btnInput.setBackground(
                            ContextCompat.getDrawable(getContext(), R.drawable.btn_inactive));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
*/
        root.findViewById(R.id.join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( (TextUtils.isEmpty(editEmail.getText()))|| (TextUtils.isEmpty(editPasswd.getText())) ||
                        (TextUtils.isEmpty(editPasswd2.getText())) || (TextUtils.isEmpty(editName.getText())) ||
                        (TextUtils.isEmpty(editRegiNum.getText())) || (TextUtils.isEmpty(editPhoneNum.getText()))) { //빈칸 채우기
                    Toast.makeText(getActivity(), "빈칸을 전부 채워주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    String email = editEmail.getText().toString().concat("@").concat(emailSpinner.getSelectedItem().toString());
                    String passwd = editPasswd.getText().toString();
                    String passwd2 = editPasswd2.getText().toString();
                    final String name = editName.getText().toString();
                    final String regiNum = editRegiNum.getText().toString().concat(editRegiNum2.getText().toString());
                    final String phoneNum = editPhoneNum.getText().toString().concat(editPhoneNum2.getText().toString()).concat(editPhoneNum3.getText().toString());
                    bundleName.putString("name",name);
                    if (!passwd.equals(passwd2)) {
                        Toast.makeText(getActivity(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    } else {
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

                }
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
                        ((MainActivity) getActivity()).updateUI(user);
                        ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
                        Toast.makeText(getActivity(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}