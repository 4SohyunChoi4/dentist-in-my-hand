package com.toothfairy.dentist.ui.book;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.UserInfo;
import com.toothfairy.dentist.ui.intro.IntroFragment;

import java.lang.reflect.Array;
import java.util.Objects;

public class BookFragment extends Fragment {
    public static BookFragment newInstance(){
        return new BookFragment();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book, container, false);

        final FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.setPersistenceEnabled(true);
        final EditText editName = root.findViewById(R.id.name);
        final EditText editRegiNum= root.findViewById(R.id.regiNum);
        final EditText editPhoneNum= root.findViewById(R.id.phoneNum);

        root.findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                long regiNum = Integer.parseInt(editRegiNum.getText().toString());
                long phoneNum = Integer.parseInt(editPhoneNum.getText().toString());

                UserInfo userInfo = new UserInfo();
                userInfo.setName(name);
                userInfo.setRegiNum(regiNum);
                userInfo.setPhoneNum(phoneNum);
                mFirebaseDatabase.getReference("userInfo/")
                        .push()
                        .setValue(userInfo)
                        .addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                ((MainActivity)getActivity()).replaceFragment(SubBookFragment.newInstance());
                            }
                        });
            }
        });


       root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ((MainActivity)getActivity()).replaceFragment(IntroFragment.newInstance());
           }
       });
        return root;
    }
}