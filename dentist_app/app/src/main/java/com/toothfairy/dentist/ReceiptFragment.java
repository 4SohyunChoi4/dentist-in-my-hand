package com.toothfairy.dentist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.toothfairy.dentist.book.BookActivity;
import com.toothfairy.dentist.book.BookInfo;
import com.toothfairy.dentist.book.MyBookList;
import com.toothfairy.dentist.intro.IntroFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ReceiptFragment extends Fragment {
    FirebaseDatabase mFirebaseDatabase;
    String year;
    String month;
    String dayOfMonth;
    String hour;

    int y, m, d, day, h; //접수 날짜

    public static ReceiptFragment newInstance() {
        return new ReceiptFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_receipt, container, false);

        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("M", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH", Locale.getDefault());

        year = yearFormat.format(currentTime);
        month = monthFormat.format(currentTime);
        dayOfMonth = dayFormat.format(currentTime);
        hour = hourFormat.format(currentTime);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        findMyBook();
        //startReceipt();
        return root;
    }

    private void findMyBook() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_DeviceDefault));
        builder.setMessage("당일 예약한 내역이 없습니다.");
        builder.setPositiveButton("돌아가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });

        final AlertDialog alertDialog = builder.create();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase.getReference("patient/" + user.getUid() + "/" + "bookList/")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        //for (DataSnapshot d : snapshot.getChildren()) {
                        MyBookList myBookList = snapshot.getValue(MyBookList.class);
                        y = myBookList.getYear();
                        m = myBookList.getMonth();
                        day = myBookList.getDayOfMonth();
                        h = myBookList.getHour();
                        if (y == Integer.parseInt(year) && m == Integer.parseInt(month)
                                && day == Integer.parseInt(dayOfMonth) && h == Integer.parseInt(hour)) {
                            changeIsHere(snapshot.getKey());
                            return;
                        }
                        //alertDialog.show();
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
    }

    private void changeIsHere(String key) {
        mFirebaseDatabase.getReference("receipt/" + y + "/" + m + "/" + d + key)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d : snapshot.getChildren()) {
                            d.getRef().child("here").setValue(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
/*
    private void startReceipt() {
        mFirebaseDatabase.getReference("book" + "/" + year + "/" + month + "/" + dayOfMonth + "/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    BookInfo bookInfo = new BookInfo();
                    String name = d.child("name").getValue(String.class);
                    String detail = d.child("detail").getValue(String.class);
                    String phoneNum = d.child("phoneNum").getValue(String.class);
                    String subject = d.child("subject").getValue(String.class);
                    int time = d.child("time").getValue(int.class);
                    bookInfo.setName(name);
                    bookInfo.setDetail(detail);
                    bookInfo.setPhoneNum(phoneNum);
                    bookInfo.setSubject(subject);
                    bookInfo.setTime(time);
                    bookInfo.setHere(false);
                    setReceipt(bookInfo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


 */

/*
    private void setReceipt(BookInfo bookInfo) {

        mFirebaseDatabase.getReference("receipt/" + year + "/" + month + "/" + dayOfMonth)// + item.getTime() + "/")
                .push()
                .setValue(bookInfo)
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "접수 완료", Toast.LENGTH_SHORT).show();
                    }
                });
    }
 */
}
