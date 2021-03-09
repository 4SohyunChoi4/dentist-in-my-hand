package com.toothfairy.dentist.book;

import com.toothfairy.dentist.PatientID;

public class BookInfo {
    private int time;
    private String name;
    private long phoneNum;
    private String subject;

    public String getName() {
        return name;
    }
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    public void setName(String name) {
        this.name = name;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
