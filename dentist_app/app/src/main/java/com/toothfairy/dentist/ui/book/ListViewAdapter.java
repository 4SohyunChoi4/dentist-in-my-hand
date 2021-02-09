package com.toothfairy.dentist.ui.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.firebase.database.FirebaseDatabase;
import com.toothfairy.dentist.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private TextView time;
    private TextView limit;

    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();

    public ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

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
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }
        time = convertView.findViewById(R.id.time);
        limit = convertView.findViewById(R.id.limit);

        final ListViewItem listViewItem = listViewItemList.get(position);

        //final long regiNum = Integer.parseInt(editRegiNum.getText().toString());
        time.setText(String.valueOf(listViewItem.getTime()));
        if(listViewItem.getLimit()<2) //1명만
            limit.setText("예약 가능");
        else {//2명이상
            limit.setText("X");
            limit.setTextColor(Integer.parseInt("RED"));
        }

        return convertView;
    }
    public void addItem(int time){
        ListViewItem item = new ListViewItem();

        item.setTime(time);
        item.setLimit(0); //이렇게 하는게 아니고 firebase에서 값을 받아와야 할것같다...
         /*
        mFirebaseDatabase
                .getReference()
              .getReference("memos/" + mFirebaseUser.getUid() + "/" + selectedMemoKey)
                .removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Snackbar.make(etContent, "메모가 삭제되었습니다", Snackbar.LENGTH_SHORT).show();
                    }
                });//firebase에서 응답을 주는 서버 함수 구현

         */
        listViewItemList.add(item);
    }

}
