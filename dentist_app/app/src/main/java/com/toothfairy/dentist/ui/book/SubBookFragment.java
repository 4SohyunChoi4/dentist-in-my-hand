package com.toothfairy.dentist.ui.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.ui.intro.IntroFragment;

import java.util.Objects;

public class SubBookFragment extends Fragment {
    public static SubBookFragment newInstance(){
        return new SubBookFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book_sub, container, false);



       root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ((MainActivity)getActivity()).replaceFragment(BookFragment.newInstance());
           }
       });
        return root;
    }
}