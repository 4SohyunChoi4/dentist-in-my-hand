package com.toothfairy.dentist.ui.book;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.toothfairy.dentist.R;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private TextView title;
    private TextView bool;

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

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

        title = (TextView) convertView.findViewById(R.id.time);
        bool = (TextView) convertView.findViewById(R.id.bool);

        ListViewItem listViewItem = listViewItemList.get(position);

        title.setText(listViewItem.getTitle());
        bool.setText(listViewItem.getBool());

        return convertView;
    }
    public void addItem(String title, String bool){
        ListViewItem item = new ListViewItem();

        item.setTitle(title);
        item.setBool(bool);

        listViewItemList.add(item);
    }
}
