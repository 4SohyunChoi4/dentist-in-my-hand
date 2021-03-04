package com.toothfairy.dentist.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.intro.IntroFragment;

public class OfficehoursFragment extends Fragment {
    public static OfficehoursFragment newInstance(){
        return new OfficehoursFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_officehours, container, false);

        //firebase DB에서 글 받아오고 출력(만)


       root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ((MainActivity)getActivity()).replaceFragment(IntroFragment.newInstance());
           }
       });
        return root;
    }
}