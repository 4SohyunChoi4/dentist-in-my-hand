package com.toothfairy.dentist.book;

import android.content.Context;
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

public class ListViewAdapter extends BaseAdapter {

    private TextView time;
    private TextView limit;

    DatabaseReference mDatabaseReference;

    public ArrayList<ListViewItem> listViewItemList = new ArrayList<>();

    public ListViewAdapter() {
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item_dialog, parent, false);
        }
        time = convertView.findViewById(R.id.time);
        limit = convertView.findViewById(R.id.limit);

        final ListViewItem item = listViewItemList.get(position);

        if (item.getLimit() < 2) //1명만
            limit.setText("예약하기");
        else {//2명이상
            limit.setText("X");
        }
        time.setText(String.valueOf(item.getTime()));
        return convertView;
    }

    public void clear() {
        listViewItemList.clear();
    }

    public void addItem(String ref, int time) {
        final ListViewItem item = new ListViewItem();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        item.setTime(time); //이렇게 하는게 아니고 firebase에서 값을 받아와야 할것같다...
        item.setLimit(0);
        mDatabaseReference.child(ref + time + "h/").addChildEventListener(new ChildEventListener() {
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
        listViewItemList.add(item);

    }
}