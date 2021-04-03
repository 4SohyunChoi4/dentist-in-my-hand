package com.toothfairy.dentist.intro;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toothfairy.dentist.LoginFragment;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.PatientID;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.book.BookActivity;
import com.toothfairy.dentist.book.MyBookListFragment;
import com.toothfairy.dentist.notice.NoticeFragment;
import com.toothfairy.dentist.ui.OfficehoursFragment;
import com.toothfairy.dentist.ask.AskFragment;

import java.util.Objects;

public class IntroFragment extends Fragment {

    private IntroViewModel introViewModel;

    public static IntroFragment newInstance() {
        return new IntroFragment();
    }

    private FirebaseUser user;
    TextView welcomeText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),android.R.style.Theme_DeviceDefault));
        builder.setMessage("로그인이 필요한 기능입니다.");
        builder.setPositiveButton("로그인하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity) getActivity()).replaceFragment(LoginFragment.newInstance());
            }
        });
        final AlertDialog alertDialog = builder.create();
        introViewModel =
                ViewModelProviders.of(this).get(IntroViewModel.class);
        View root = inflater.inflate(R.layout.fragment_intro, container, false);
        welcomeText = root.findViewById(R.id.welcomeText);

        user = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(user);

        root.findViewById(R.id.noticeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(NoticeFragment.newInstance());
            }
        });
        root.findViewById(R.id.bookBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    alertDialog.show();
                }
                else
                 ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(MyBookListFragment.newInstance());
            }
        });

        root.findViewById(R.id.officehoursBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(OfficehoursFragment.newInstance());
            }
        });

        //TODO: officehour 옮기기
        root.findViewById(R.id.askBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    alertDialog.show();
                } else
                    ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(AskFragment.newInstance());
            }
        });

        return root;
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) { // 로그인했을때
            FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseDatabase.getReference("patient/" + user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    PatientID patient = snapshot.getValue(PatientID.class);
                    String name = patient.getName();
                    welcomeText.setText(" "+name+"님\n 안녕하세요");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

    }
}