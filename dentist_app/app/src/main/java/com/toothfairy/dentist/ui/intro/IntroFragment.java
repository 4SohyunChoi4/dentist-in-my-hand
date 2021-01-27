package com.toothfairy.dentist.ui.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.ui.NoticeFragment;

import java.util.Objects;

public class IntroFragment extends Fragment {

    private IntroViewModel introViewModel;

    public static IntroFragment newInstance() {
        return new IntroFragment();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        introViewModel =
                ViewModelProviders.of(this).get(IntroViewModel.class);
        View root = inflater.inflate(R.layout.fragment_intro, container, false);

        root.findViewById(R.id.noticeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(NoticeFragment.newInstance());
            }
        });


        return root;
    }
}