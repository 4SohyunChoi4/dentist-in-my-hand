package com.toothfairy.dentist.ui.intro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.toothfairy.dentist.LoginFragment;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.ui.NoticeFragment;
import com.toothfairy.dentist.ui.OfficehoursFragment;
import com.toothfairy.dentist.ui.ask.AskFragment;
import com.toothfairy.dentist.ui.book.BookFragment;

import java.util.Objects;

public class IntroFragment extends Fragment {

    private IntroViewModel introViewModel;

    public static IntroFragment newInstance() {
        return new IntroFragment();
    }

    private FirebaseUser user;

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

        user = FirebaseAuth.getInstance().getCurrentUser();

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
                 ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(BookFragment.newInstance());
            }
        });

        root.findViewById(R.id.officehoursBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(OfficehoursFragment.newInstance());
            }
        });
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
}