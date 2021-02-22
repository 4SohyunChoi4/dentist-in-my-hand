package com.toothfairy.dentist;

public class PatientInfo {

    private String key;
    private String name;
    private long regiNum;
    private long phoneNum;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRegiNum() {
        return regiNum;
    }

    public void setRegiNum(long regiNum) {
        this.regiNum = regiNum;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }
}
