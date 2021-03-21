package com.toothfairy.dentist.notice;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

public class ResizeAnimation extends Animation {
    private final View mView;
    private final float mToHeight;
    private final float mFromHeight;

    private final float mToWidth;
    private final float mFromWidth;

    private final ListAdapter mListAdapter;
    private final ListItem mListItem;

    public ResizeAnimation(ListAdapter listAdapter, ListItem listItem, float fromWidth, float fromHeight, float toWidth, float toHeight) {
        mToHeight = toHeight;
        mToWidth = toWidth;
        mFromHeight = fromHeight;
        mFromWidth = fromWidth;
        mView = listItem.getHolder().getTextViewWrap();
        mListAdapter = listAdapter;
        mListItem = listItem;
        setDuration(200);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float height = (mToHeight - mFromHeight) * interpolatedTime + mFromHeight;
        float width= (mToWidth - mFromWidth) * interpolatedTime + mFromWidth;
        ViewGroup.LayoutParams p = mView.getLayoutParams();
        p.height = (int) height;
        p.width = (int) width;
        mListItem.setCurrentHeight(p.height);
        ((BaseAdapter)mListAdapter).notifyDataSetChanged();
    }
}
