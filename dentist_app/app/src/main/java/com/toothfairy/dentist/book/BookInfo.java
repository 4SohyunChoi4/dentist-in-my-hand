package com.toothfairy.dentist.book;

import com.toothfairy.dentist.PatientID;

public class BookInfo {

    private PatientID patient;
    private String subject;

    public PatientID getPatient() {
        return patient;
    }

    public void setPatient(PatientID patient) {
        this.patient = patient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
