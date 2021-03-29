package com.toothfairy.dentist.book;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.PatientID;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.intro.IntroFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyBookListFragment extends Fragment implements ListViewBookListAdapter.ListBtnClickListener {
    public static MyBookListFragment newInstance() {
        return new MyBookListFragment();
    }
    FirebaseDatabase mFirebaseDatabase;
    FirebaseUser user;
    ListViewBookListAdapter adapter;
    Date today;
    String rootPath, key;
    int year, month, dayOfMonth, hour;

    @Override
    public void onListBtnClick(int position, String key, String rootPath) {//삭제한다
        this.key = key;
        this.rootPath = rootPath;
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),android.R.style.Theme_DeviceDefault));
        builder.setMessage("기존 예약이 취소되고,\n 다시 설정해야 합니다.");
        builder.setPositiveButton("바꾸기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteBook();
                Intent intent = new Intent(getActivity(), BookActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_my_book_list, container, false);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });
        root.findViewById(R.id.btnNewBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookActivity.class);
                startActivity(intent);
            }
        });

        adapter = new ListViewBookListAdapter(this);
        ListView listView = root.findViewById(R.id.myBookListBoard);
        listView.setAdapter(adapter);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("M", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());
        String year_s = yearFormat.format(currentTime);
        String month_s = monthFormat.format(currentTime);
        String dayOfMonth_s = dayFormat.format(currentTime);
        String hour_s = hourFormat.format(currentTime);
        year = Integer.parseInt(year_s);
        month = Integer.parseInt(month_s);
        dayOfMonth = Integer.parseInt(dayOfMonth_s);
        hour = Integer.parseInt(hour_s);

        adapter.setToday(today);

        displayBookList();
        return root;
    }

    private void deleteBook() {
        /*
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase
                .getReference(rootPath+"/"+key)
                .removeValue();*/
        mFirebaseDatabase
                .getReference("patient/" + user.getUid() + "/" + "bookList/"+key)
                .removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getActivity(),"삭제 완료",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    protected void displayBookList() {
        mFirebaseDatabase.getReference("patient/" + user.getUid() + "/" + "bookList/")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        MyBookList myBookList = snapshot.getValue(MyBookList.class);
                        if(snapshot.getChildrenCount() > 0) {
                            adapter.addItem(snapshot.getKey(), myBookList.getYear(), myBookList.getMonth(),
                                    myBookList.getDayOfMonth(), myBookList.getHour(), year, month, dayOfMonth, hour);
                        }
                        else{
                            return;
                            //adapter.addEmptyItem();
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        adapter.notifyDataSetChanged();
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
