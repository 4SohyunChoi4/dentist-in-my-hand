package com.toothfairy.dentist.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.ui.intro.IntroFragment;

public class NoticeFragment extends Fragment {
    public static NoticeFragment newInstance(){
        return new NoticeFragment();
    }

    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notice, container, false);

        final EditText board = root.findViewById(R.id.board);
        board.setFocusable(false);
        board.setClickable(false);

        mFirebaseDatabase.getReference("Ask/")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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


/*
private void displayMemos() {
        mFirebaseDatabase.getReference("memos/" + mFirebaseUser.getUid())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Memo memo = snapshot.getValue(Memo.class);
                        memo.setKey(snapshot.getKey());
                        displayMemoList(memo);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {//데이터 수정이 일어났을 때
                        Memo memo = snapshot.getValue(Memo.class);
                        memo.setKey(snapshot.getKey()); //데이터를 받아온다
                        for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
                            MenuItem menuItem = mNavigationView.getMenu().getItem(i);
                            if (memo.getKey().equals(((Memo) menuItem.getActionView().getTag()).getKey())) {//tag -> Memo로
                                menuItem.getActionView().setTag(memo);
                                menuItem.setTitle(memo.getTxt());
                                break;
                            }
                        }
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        Memo memo = snapshot.getValue(Memo.class);
                        initMemo();
                        memo.setKey(snapshot.getKey()); //데이터를 받아온다
                        for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
                            MenuItem menuItem = mNavigationView.getMenu().getItem(i);
                            if (memo.getKey().equals(((Memo) menuItem.getActionView().getTag()).getKey())) {//tag -> Memo로
                                menuItem.setVisible(false);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

 */
       root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ((MainActivity)getActivity()).replaceFragment(IntroFragment.newInstance());
           }
       });
        return root;
    }
}