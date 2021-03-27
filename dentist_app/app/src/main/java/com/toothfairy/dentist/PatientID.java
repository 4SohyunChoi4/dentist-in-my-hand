package com.toothfairy.dentist;

public class PatientID {

    private String key;
    private String name;
    private String regiNum;
    private String phoneNum;
    private boolean isHere;

    public boolean isHere() {
        return isHere;
    }

    public void setHere(boolean here) {
        isHere = here;
    }

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

    public String getRegiNum() {
        return regiNum;
    }

    public void setRegiNum(String regiNum) {
        this.regiNum = regiNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
