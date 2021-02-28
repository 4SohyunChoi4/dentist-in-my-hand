package com.toothfairy.dentist.book;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.PatientID;
import com.toothfairy.dentist.ui.intro.IntroFragment;

import java.util.Calendar;
import java.util.Objects;

public class BookFragment extends Fragment {
    public static BookFragment newInstance() {
        return new BookFragment();
    }

    FirebaseUser user;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    private ListView listView;
    private ListViewAdapter adapter;
    Dialog dialog;

    String[] monthTxt = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Dec"};
    String[] weekDay = {"일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"};
    int y, m, d;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        RadioButton etc = root.findViewById(R.id.etc);

        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });
        etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getView().findViewById(R.id.etcEdit).setVisibility(View.VISIBLE);
            }
        });
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_book);

        adapter = new ListViewAdapter();

        final TextView time = dialog.findViewById(R.id.time);

        listView = dialog.findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("정말로 예약하시겠습니까?");
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                final ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int which) {
                        //PatientID patientID = new PatientID(); // 로그인 된 사용자의 정보를 가져온다.
                        mFirebaseDatabase.getReference("patient/" + user.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                PatientID patient = snapshot.getValue(PatientID.class);
                                BookInfo bookInfo = new BookInfo();
                                bookInfo.setPatient(patient);
                                bookInfo.setSubject(getCheckbox());
                                mFirebaseDatabase.getReference("book/" + y + "Y/" + monthTxt[m] + "/" + d + "D/" + item.getTime() + "h/")
                                        .setValue(bookInfo)
                                        .addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dialog.dismiss();
                                                adapter.clear();
                                                Toast.makeText(getActivity(), "예약이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());

                                            }
                                        });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });


                    }
                });
                builder.create().show();
            }

        });
        final CalendarView calendarView = root.findViewById(R.id.calendar);
        final Calendar calendar = Calendar.getInstance();
        long startOfMonth = calendar.getTimeInMillis();
        calendarView.setMinDate(startOfMonth);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar subCalendar = Calendar.getInstance();
                subCalendar.set(year, month, dayOfMonth);
                int dayOfWeek = subCalendar.get(Calendar.DAY_OF_WEEK) - 1;
                showDialog(year, month, dayOfMonth, dayOfWeek);
            }
        });
        return root;
    }

    private String getCheckbox() {
        RadioGroup rdGroup = getView().findViewById(R.id.rdGroup);
        int subjectID = rdGroup.getCheckedRadioButtonId();

        if (subjectID == 0x7f0900b1) {
            EditText etcEdit = getView().findViewById(R.id.etcEdit);
            return etcEdit.getText().toString();
        }
        else {
            RadioButton rb = getView().findViewById(subjectID);
            return rb.getText().toString();
        }

    }

    public void showDialog(int year, int month, int dayOfMonth, int dayOfWeek) { //월, 연
        adapter.clear();
        dialog.show();
        TextView dialogTitle = dialog.findViewById(R.id.dialogTitle);
        dialogTitle.setText((month + 1) + "월" + dayOfMonth + "일" + weekDay[dayOfWeek]);
        y = year;
        m = month;
        d = dayOfMonth;

        for (int i = 9; i <= 16; i++) {
            if (i == 12)
                continue;
/*
            final int finalI = i;
            mDatabaseReference = FirebaseDatabase.getInstance().getReference();
            mDatabaseReference.child("Book/" + y + "Y/" + monthTxt[m] + "/" + d + "D/" + finalI + "h/").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        count = snapshot.getChildrenCount();
                        Toast.makeText(getActivity(), Long.toString(count), Toast.LENGTH_SHORT).show();
                    } else
                        count = 0;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/
            adapter.addItem(i);
        }

        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        (dialog.findViewById(R.id.cancelBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}