package com.toothfairy.dentist.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.toothfairy.dentist.R;

public class MyBookListFragment extends Fragment{
    public static MyBookListFragment newInstance() {
        return new MyBookListFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_my_book_list, container, false);

        return root;
    }
}
