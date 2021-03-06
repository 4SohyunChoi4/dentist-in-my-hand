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
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.intro.IntroFragment;

public class DoctorFragment extends Fragment {

    public static DoctorFragment newInstance() {
        return new DoctorFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_doctor, container, false);
        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });
        return root;
    }
}