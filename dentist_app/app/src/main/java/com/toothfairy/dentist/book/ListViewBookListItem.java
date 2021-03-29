package com.toothfairy.dentist.book;

public class ListViewBookListItem {
    private String textDate;
    private Boolean isChangeable;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTextDate() {
        return textDate;
    }

    public Boolean getChangeable() {
        return isChangeable;
    }

    public void setChangeable(Boolean changeable) {
        isChangeable = changeable;
    }

    public void setTextDate(String textDate) {
        this.textDate = textDate;
    }

}
