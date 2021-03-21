package com.toothfairy.dentist.notice;

import com.toothfairy.dentist.R;

public class ListItem {

    private ListViewHolder holder;
    private String text;
    private int collapsedHeight, currentHeight, expandedHeight;
    private boolean isOpen;
    private int drawable;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCollapsedHeight() {
        return collapsedHeight;
    }

    public void setCollapsedHeight(int collapsedHeight) {
        this.collapsedHeight = collapsedHeight;
    }

    public int getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentHeight(int currentHeight) {
        this.currentHeight = currentHeight;
    }

    public int getExpandedHeight() {
        return expandedHeight;
    }

    public void setExpandedHeight(int expandedHeight) {
        this.expandedHeight = expandedHeight;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public ListViewHolder getHolder() {
        return holder;
    }

    public void setHolder(ListViewHolder holder) {
        this.holder = holder;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }


    public ListItem(String text, int collapsedHeight, int currentHeight, int expandedHeight){
        super();
        this.text = text;
        this.collapsedHeight = collapsedHeight;
        this.currentHeight = currentHeight;
        this.expandedHeight = expandedHeight;
        this.isOpen = false;
        this.drawable = R.drawable.down;
    }


}
