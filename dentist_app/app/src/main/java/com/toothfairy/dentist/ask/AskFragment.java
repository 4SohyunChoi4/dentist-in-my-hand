package com.toothfairy.dentist.ask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.PatientID;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.intro.IntroFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AskFragment extends Fragment {


    public static AskFragment newInstance(){
        return new AskFragment();
    }

    private FirebaseDatabase mFirebaseDatabase;

    long num=1;
    private ArrayAdapter<String> askAdapter;
    List<Object> askArray = new ArrayList<>();
    Bundle bundle = new Bundle();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AskViewModel askViewModel = ViewModelProviders.of(this).get(AskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ask, container, false);

        isLogin();

        CountAskNum();
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
                ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });

        root.findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = SubAskFragment.newInstance();
                bundle.putLong("number", num);                //FragmentManager fm = getSupportFragmentManager();
                fragment.setArguments(bundle);
                /*FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.nav_host_fragment, fragment).commit();
                */
                ((MainActivity) Objects.requireNonNull(getActivity())).replaceFragment(fragment);
            }
        });

        final ListView askBoard = root.findViewById(R.id.askBoard);
        displayAsk();
        askAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        askBoard.setAdapter(askAdapter);

        return root;
    }

    private void isLogin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!= null)
        {
            mFirebaseDatabase = FirebaseDatabase.getInstance(); //빼도 될것 같다
            mFirebaseDatabase.getReference("patient/" + user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    PatientID patient = snapshot.getValue(PatientID.class);
                    bundle.putString("name",patient.getName());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }


    private void CountAskNum() {
        mFirebaseDatabase.getReference("ask/")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //num = snapshot.getChildrenCount();
                        for (DataSnapshot d: snapshot.getChildren())
                            // TODO: handle the post
                            num++;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
    private void displayAsk() {
        mFirebaseDatabase.getReference("ask/")
                .addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName){
                    Ask ask = snapshot.getValue(Ask.class);
                    String askContent = ask.getContent();
                    askArray.add(askContent);
                    askAdapter.add(askContent);
                    askAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    //답변이 달렸을 때
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
    }

}