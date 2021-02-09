package com.toothfairy.dentist.ui.book;

public class ListViewItem {

    private int time;
    private int limit;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void addPatient(){
        this.limit+=1;
    }
}
