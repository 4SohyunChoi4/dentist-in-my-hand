package com.toothfairy.dentist.ui.intro;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.ui.about.AboutFragment;
import com.toothfairy.dentist.ui.doctor.DoctorFragment;
import com.toothfairy.dentist.ui.map.MapFragment;
import com.toothfairy.dentist.ui.subject.SubjectFragment;

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


        return root;
    }
}