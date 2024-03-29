package com.toothfairy.dentist.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.toothfairy.dentist.R;

import java.util.ArrayList;


public class ListAdapter extends ArrayAdapter<ListItem> {
    private ArrayList<ListItem> listItems;
    private Context context;

    public ListAdapter(@NonNull Context context, int textViewResourceId, @NonNull ArrayList<ListItem> listItems) {
        super(context, textViewResourceId, listItems);
        this.listItems = listItems;
        this.context = context;
    }


    @Override
    @SuppressWarnings("deprecation")
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder holder;
        ListItem listItem = listItems.get(position);

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.listview_item_notice, null);

            LinearLayout textViewWrap = convertView.findViewById(R.id.text_wrap);
            TextView text = convertView.findViewById(R.id.text_notice);
            holder = new ListViewHolder(textViewWrap, text);
        } else
            holder = (ListViewHolder) convertView.getTag();
        holder.getTextView().setText(listItem.getText());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, listItem.getCurrentHeight());
        holder.getTextViewWrap().setLayoutParams(layoutParams);
        holder.getTextView().setCompoundDrawablesWithIntrinsicBounds(listItem.getDrawable(), 0, 0, 0);
        convertView.setTag(holder);
        listItem.setHolder(holder);
        return convertView;
    }
}