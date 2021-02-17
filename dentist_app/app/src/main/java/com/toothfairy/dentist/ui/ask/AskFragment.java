package com.toothfairy.dentist.ui.ask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.google.firebase.database.*;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.ui.intro.IntroFragment;

public class AskFragment extends Fragment {


    public static AskFragment newInstance(){
        return new AskFragment();
    }

    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AskViewModel askViewModel = ViewModelProviders.of(this).get(AskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ask, container, false);
        final TextView textView = root.findViewById(R.id.title);
        askViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });

        root.findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(SubAskFragment.newInstance());
            }
        });

        final EditText board = root.findViewById(R.id.board);
        board.setFocusable(false);
        board.setClickable(false);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mFirebaseDatabase.getReference("Ask/")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        AskInfo askInfo = snapshot.getValue(AskInfo.class);
                        askInfo.setKey(snapshot.getKey());
                        Menu menu = board.getMenu();
                        getMenu()
                        board.setTag(askInfo);
                        /*
                                Menu leftMenu = mNavigationView.getMenu(); //네비게이션 바 변수
        MenuItem item = leftMenu.add(memo.getTxt());
        View view = new View(getApplication());
        view.setTag(memo); //view에다가 memo의 내용값 지정
        item.setActionView(view);
                         */
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName){
                        /*
                        Memo memo = snapshot.getValue(Memo.class);
                        memo.setKey(snapshot.getKey()); //데이터를 받아온다
                        for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
                            MenuItem menuItem = mNavigationView.getMenu().getItem(i);
                            if (memo.getKey().equals(((Memo) menuItem.getActionView().getTag()).getKey())) {//tag -> Memo로
                                menuItem.getActionView().setTag(memo);
                                menuItem.setTitle(memo.getTxt());
                                break;
                         */
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


        return root;
    }
}