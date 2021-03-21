package com.toothfairy.dentist.notice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
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
        final int EXPANDED_HEIGHT = 500;
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

//
/*
        listItems
                .add(new ListItem(
                        "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                        COLLAPSED_HEIGHT_1, COLLAPSED_HEIGHT_1,
                        EXPANDED_HEIGHT_1));

        int COLLAPSED_HEIGHT_2 = 200;
        int EXPANDED_HEIGHT_2 = 300;
        listItems
                .add(new ListItem(
                        "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                        COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2,
                        EXPANDED_HEIGHT_2));

        int COLLAPSED_HEIGHT_3 = 250;
        int EXPANDED_HEIGHT_3 = 350;
        listItems
                .add(new ListItem(
                        "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                        COLLAPSED_HEIGHT_3, COLLAPSED_HEIGHT_3,
                        EXPANDED_HEIGHT_3));

        int EXPANDED_HEIGHT_4 = 400;
        listItems
                .add(new ListItem(
                        "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.",
                        COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2,
                        EXPANDED_HEIGHT_4));

        listItems
                .add(new ListItem(
                        "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga.",
                        COLLAPSED_HEIGHT_1, COLLAPSED_HEIGHT_1,
                        EXPANDED_HEIGHT_4));

        listItems
                .add(new ListItem(
                        "Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus.",
                        COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2,
                        EXPANDED_HEIGHT_4));

        listItems
                .add(new ListItem(
                        "Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae.",
                        COLLAPSED_HEIGHT_3, COLLAPSED_HEIGHT_3,
                        EXPANDED_HEIGHT_3));

        listItems
                .add(new ListItem(
                        "Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.",
                        COLLAPSED_HEIGHT_1, COLLAPSED_HEIGHT_1,
                        EXPANDED_HEIGHT_4));

    }

}
*/