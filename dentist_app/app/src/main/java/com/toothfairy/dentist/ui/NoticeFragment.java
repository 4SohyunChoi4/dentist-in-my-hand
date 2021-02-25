package com.toothfairy.dentist.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.Notice;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.ui.intro.IntroFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment {
    public static NoticeFragment newInstance(){
        return new NoticeFragment();
    }


    ListView noticeBoard;
    ArrayAdapter<String> noticeAdapter;
    List<Object> noticeArray = new ArrayList<>();

    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notice, container, false);

        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });
        ListView noticeBoard = root.findViewById(R.id.noticeBoard);
        displayNotice();
        noticeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        noticeBoard.setAdapter(noticeAdapter);
        return root;
    }

    private void displayNotice() {

        mFirebaseDatabase.getReference("notice/")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        //for(DataSnapshot d : snapshot.getChildren()){
                            Notice notice = snapshot.getValue(Notice.class);
                            //notice.setKey(snapshot.getKey());
                            String noticeComment = notice.getComment();
                            noticeArray.add(noticeComment);
                            noticeAdapter.add(noticeComment);
                        //}
                            noticeAdapter.notifyDataSetChanged();
                        //noticeBoard.setSelection(noticeAdapter.getCount()-1);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        //firebase DB에서 글 받아오고 출력(만)

    }
}