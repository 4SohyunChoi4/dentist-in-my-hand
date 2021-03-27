package com.toothfairy.dentist.book;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.PatientID;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.intro.IntroFragment;

import java.util.ArrayList;
import java.util.List;

public class MyBookListFragment extends Fragment {
    public static MyBookListFragment newInstance() {
        return new MyBookListFragment();
    }

    List<Object> myBookListArray = new ArrayList<>();
    ArrayAdapter<String> myBookListAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_my_book_list, container, false);
        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });


        final ListView myBookListBoard = root.findViewById(R.id.myBookListBoard);
        displayBookList();
        myBookListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        myBookListBoard.setAdapter(myBookListAdapter);

        return root;
    }

    protected void displayBookList() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.getReference("patient/" + user.getUid() + "/" +"bookList/")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int y, m, d, h;
                        MyBookList myBookList = snapshot.getValue(MyBookList.class);
                        m = myBookList.getMonth();
                        d = myBookList.getDayOfMonth();
                        h = myBookList.getHour();
                        String date = myBookList.getYear() +"년 "+myBookList.getMonth()+"월 "+myBookList.getDayOfMonth()+"일 "+myBookList.getHour()+"시";
                        //String time = myBookList.getSubject();
                        myBookListArray.add(date);
                        myBookListAdapter.add(date);
                        myBookListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}
