package com.toothfairy.dentist.book;

import android.content.Context;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.ConversationAction;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.toothfairy.dentist.R;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ListViewBookListAdapter extends BaseAdapter implements View.OnClickListener {
    public interface ListBtnClickListener {
        void onListBtnClick(int position, String key, String root);
    }

    private Button btnEditBook;
    private Date today;
    public ArrayList<ListViewBookListItem> listViewBookListItemList = new ArrayList<>();
    int resourceId;
    String key;
    String root;
    private ListBtnClickListener listBtnClickListener;

    public ListViewBookListAdapter(ListBtnClickListener clickListener) {
        this.listBtnClickListener = clickListener;

    }

    @Override
    public int getCount() {
        return listViewBookListItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewBookListItemList.get(position);
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
            convertView = inflater.inflate(R.layout.listview_item_book, parent, false);
        }

        TextView textDate = convertView.findViewById(R.id.textDate);
        TextView isChangeable = convertView.findViewById(R.id.isChangeable);
        btnEditBook = convertView.findViewById(R.id.btnEditBook);

        ListViewBookListItem listViewBookListItem = listViewBookListItemList.get(position);

        textDate.setText(listViewBookListItem.getTextDate());
        isChangeable.setText("진료 전"); // else 진료전
        btnEditBook.setTag(position);
        btnEditBook.setOnClickListener(this);
        if (listViewBookListItem.getChangeable()) { //true이면
            isChangeable.setText("진료 완료"); // else 진료전
            isChangeable.setTextColor(Color.parseColor("#0D8513"));
            btnEditBook.setVisibility(View.GONE);
        } else {
            isChangeable.setText("진료 전");
            isChangeable.setTextColor(Color.parseColor("#BD1414"));
        }
        return convertView;
    }

    public void addItem(String k, int y, int m, int d, int h, int year, int month, int dayOfMonth, int hour) {
        String bookedDate = y + "년 " + m + "월 " + d + "일 " + h + "시"; //
        root = "book/" + y + "/" + m + "/" + d;
        ListViewBookListItem listViewBookListItem = new ListViewBookListItem();

        listViewBookListItem.setTextDate(bookedDate);
        listViewBookListItem.setKey(k);
        key=k;
        if (y >= year && m >= month && d >= dayOfMonth) // 예약 날짜 > 현재 날짜 => 예약 날짜가 더 뒤(아직일때)
        {
            listViewBookListItem.setChangeable(false);
        } else {
            listViewBookListItem.setChangeable(true);
        }
        listViewBookListItemList.add(listViewBookListItem);
    }


    public void addEmptyItem() {

    }

    public void setToday(Date t) {
        today = t;
    }

    public void onClick(View v) {
        // ListBtnClickListener(MainActivity)의 onListBtnClick() 함수 호출.
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int) v.getTag(), key, root);
        }
    }
}
