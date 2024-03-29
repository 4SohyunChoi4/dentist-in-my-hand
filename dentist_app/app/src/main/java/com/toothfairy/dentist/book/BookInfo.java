package com.toothfairy.dentist.book;

import com.toothfairy.dentist.PatientID;

public class BookInfo {
    private int time;
    private String name;
    private String phoneNum;
    private String subject;
    private String detail;
    private String bookList;
    private Boolean isHere;

    public Boolean getHere() {
        return isHere;
    }

    public void setHere(Boolean here) {
        isHere = here;
    }

    public String getBookList() {
        return bookList;
    }

    public void setBookList(String bookList) {
        this.bookList = bookList;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
