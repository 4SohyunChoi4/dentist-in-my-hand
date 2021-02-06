package com.toothfairy.dentist.ui.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.toothfairy.dentist.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private TextView time;
    private TextView bool;

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
        bool = convertView.findViewById(R.id.bool);

        final ListViewItem listViewItem = listViewItemList.get(position);

        //final long regiNum = Integer.parseInt(editRegiNum.getText().toString());
        time.setText(String.valueOf(listViewItem.getTime()));
        bool.setText(listViewItem.getBool());

        return convertView;
    }
    public void addItem(int time, String bool){
        ListViewItem item = new ListViewItem();

        item.setTime(time);
        item.setBool(bool);
        listViewItemList.add(item);
    }

}
