package com.toothfairy.dentist.ui.doctor;

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

public class DoctorFragment extends Fragment {

    private DoctorViewModel doctorViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        doctorViewModel =
                ViewModelProviders.of(this).get(DoctorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_subject, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        doctorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}