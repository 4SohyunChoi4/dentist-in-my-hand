package com.toothfairy.dentist.book;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.database.*;
import com.toothfairy.dentist.R;

import java.util.ArrayList;

public class ListViewDialogAdapter extends BaseAdapter {

    private TextView time;
    private TextView limit;

    DatabaseReference mDatabaseReference;

    public ArrayList<ListViewDialogItem> listViewItemListDialog = new ArrayList<>();

    public ListViewDialogAdapter() {
    }

    @Override
    public int getCount() {
        return listViewItemListDialog.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemListDialog.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item_dialog, parent, false);
        }
        time = convertView.findViewById(R.id.time);
        limit = convertView.findViewById(R.id.limit);

        final ListViewDialogItem item = listViewItemListDialog.get(position);

        if (item.getLimit()>1) { //1명만
            limit.setText("예약불가");
            //limit.setTextColor(Color.parseColor("#000000"));
        }
        else {//2명이상'
            limit.setText("예약하기");
            limit.setTextColor(Color.parseColor("#0D8513"));
        }
        time.setText(String.valueOf(item.getTime()));
        return convertView;
    }

    public void clear() {
        listViewItemListDialog.clear();
    }

    public void addItem(String ref, int time) {
        final ListViewDialogItem item = new ListViewDialogItem();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        item.setTime(time); //이렇게 하는게 아니고 firebase에서 값을 받아와야 할것같다...
        item.setLimit(0);
       /* mDatabaseReference.child(ref + time + "h/").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    //item= snapshot.getValue()
                    item.setLimit(snapshot.getChildrenCount());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists()) {
                    item.setLimit(snapshot.getChildrenCount());
                }
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

        */
        listViewItemListDialog.add(item);

    }
}