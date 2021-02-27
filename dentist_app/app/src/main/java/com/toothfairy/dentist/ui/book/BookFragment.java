package com.toothfairy.dentist.ui.book;

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
import com.google.firebase.database.*;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.PatientID;
import com.toothfairy.dentist.ui.intro.IntroFragment;

import java.util.Calendar;
import java.util.Objects;

public class BookFragment extends Fragment{
    public static BookFragment newInstance(){
        return new BookFragment();
    }

    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference;
    private ListView listView;
    private ListViewAdapter adapter;
    long count=0;
    Dialog dialog;
    String[] monthTxt = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Dec"};
    String[] weekDay = {"일요일", "월요일","화요일","수요일","목요일","금요일","토요일"};
    int y, m, d;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book, container, false);

        final EditText editName = root.findViewById(R.id.name);
        final EditText editRegiNum = root.findViewById(R.id.regiNum);
        final EditText editPhoneNum = root.findViewById(R.id.phoneNum);


        final CheckBox ch1 = root.findViewById(R.id.ch1);
        final CheckBox ch2 = root.findViewById(R.id.ch2);
        final CheckBox ch3 = root.findViewById(R.id.ch3);
        final CheckBox ch4 = root.findViewById(R.id.ch4);
        final CheckBox ch5 = root.findViewById(R.id.ch5);

        final RadioButton park = root.findViewById(R.id.doctorPark);
        final RadioButton kim = root.findViewById(R.id.doctorKim);

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_book);

        adapter = new ListViewAdapter();


        final TextView time = dialog.findViewById(R.id.time);
        final TextView limit = dialog.findViewById(R.id.limit);

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
                        String name = editName.getText().toString();
                        long regiNum = Integer.parseInt(editRegiNum.getText().toString());
                        long phoneNum = Integer.parseInt(editPhoneNum.getText().toString());

                        PatientID patientID = new PatientID();
                        patientID.setName(name);
                        patientID.setRegiNum(regiNum);
                        patientID.setPhoneNum(phoneNum);

                        final String path = "Book/"+y+"Y/"+monthTxt[m]+"/"+d+"D/"+item.getTime()+"h/";

                        mFirebaseDatabase.getReference(path)
                                .push()
                                .setValue(patientID)
                                .addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialog.dismiss();
                                        adapter.clear();
                                        ((MainActivity)getActivity()).replaceFragment(IntroFragment.newInstance());

                                    }
                                });
                        mFirebaseDatabase.getReference(path)
                                .addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                        PatientID patientID = snapshot.getValue(PatientID.class);
                                        assert patientID != null;
                                        patientID.setKey(snapshot.getKey());
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
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(year,month,dayOfMonth);
                int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK)-1;
                showDialog(year, month, dayOfMonth, dayOfWeek);
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

    public void showDialog(int year, int month, int dayOfMonth, int dayOfWeek) { //월, 연
        adapter.clear();
        dialog.show();
        TextView dialogTitle = dialog.findViewById(R.id.dialogTitle);
        dialogTitle.setText((month + 1) + "월" + dayOfMonth + "일" + weekDay[dayOfWeek]);
        y=year;
        m=month;
        d=dayOfMonth;

        for(int i=9 ; i<=16 ; i++){
            if (i == 12)
                continue;
            count=0;

            final int finalI = i;
            mDatabaseReference = FirebaseDatabase.getInstance().getReference();
            mDatabaseReference.child("Book/"+y+"Y/"+monthTxt[m]+"/"+d+"D/"+finalI+"h/").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                             if(snapshot.exists()){
                                count = snapshot.getChildrenCount();
                                Toast.makeText(getActivity(),Long.toString(count),Toast.LENGTH_SHORT).show();
                            }
                            else
                                count = 0;
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
            adapter.addItem(i, count);
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