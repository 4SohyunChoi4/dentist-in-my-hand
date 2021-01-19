package com.toothfairy.dentist.ui.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.toothfairy.dentist.R;

public class IntroFragment extends Fragment {

    private IntroViewModel introViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        introViewModel =
                ViewModelProviders.of(this).get(IntroViewModel.class);
        View root = inflater.inflate(R.layout.fragment_intro, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        introViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}