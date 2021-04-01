package com.toothfairy.dentist.notice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.toothfairy.dentist.MainActivity;
import com.toothfairy.dentist.R;
import com.toothfairy.dentist.intro.IntroFragment;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {
    public static NoticeFragment newInstance() {
        return new NoticeFragment();
    }

    private ListView listView;
    ArrayList<ListItem> listItems = new ArrayList<>();
    private ListAdapter adapter;
    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notice, container, false);
        root.findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(IntroFragment.newInstance());
            }
        });
        listView = root.findViewById(R.id.noticeBoard);
        displayNotice();

        adapter = new ListAdapter(getActivity().getApplicationContext(), R.layout.listview_item_notice, listItems); //listItems
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toggle(view, position);
            }
        });
        return root;
    }

    private void toggle(View view, int position) {
        ListItem listItem = listItems.get(position);
        listItem.getHolder().setTextViewWrap((LinearLayout) view);
//        holder.setTextViewWrap((LinearLayout) view); 에서 null... 왜지

        int fromHeight = 0;
        int toHeight = 0;

        if (listItem.isOpen()) {
            fromHeight = listItem.getExpandedHeight();
            toHeight = listItem.getCollapsedHeight();
        } else {
            fromHeight = listItem.getCollapsedHeight();
            toHeight = listItem.getExpandedHeight();

            // boolean accordion = true;
            //if (accordion)
            //closeAll();
        }
        toggleAnimation(listItem, position, fromHeight, toHeight, true);
    }

    private void closeAll() {
        int i = 0;
        for (ListItem listItem : listItems) {
            if (listItem.isOpen()) {
                toggleAnimation(listItem, i, listItem.getExpandedHeight(), listItem.getCollapsedHeight(), false);
            }
            i++;
        }
    }

    private void toggleAnimation(final ListItem listItem, final int position, final int fromHeight, final int toHeight, final boolean goToItem) {
        ResizeAnimation resizeAnimation = new ResizeAnimation(adapter, listItem, 0, fromHeight, 0, toHeight);
        resizeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                listItem.setOpen(!listItem.isOpen());
                listItem.setDrawable(listItem.isOpen() ? R.drawable.up : R.drawable.down);
                listItem.setCurrentHeight(toHeight);
                //adapter.notifyDataSetChanged();

                if (goToItem)
                    goToItem(position);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        listItem.getHolder().getTextViewWrap().startAnimation(resizeAnimation);

    }

    private void goToItem(final int position) {
        listView.post(new Runnable() {
            @Override
            public void run() {
                try {
                    listView.smoothScrollToPosition(position);
                } catch (Exception e) {
                    listView.setSelection(position);
                }

            }
        });
    }

    private void displayNotice() {
        final int COLLAPSED_HEIGHT = 100;
        final int EXPANDED_HEIGHT = 300;
        mFirebaseDatabase.getReference("notice/")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Notice notice = snapshot.getValue(Notice.class);
                        String noticeComment = notice.getComment();
                        listItems.add(new ListItem(noticeComment, COLLAPSED_HEIGHT, COLLAPSED_HEIGHT, EXPANDED_HEIGHT));
                        adapter.notifyDataSetChanged();
                        //
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
}