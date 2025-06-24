package com.example.model;

public class Referral implements User {

    int code, doctorCode, patientCode, illnessCode;
    private String description;

    public Referral(int code, int doctorCode, int patientCode, int illnessCode, String description) {
        this.code = code;
        this.doctorCode = doctorCode;
        this.patientCode = patientCode;
        this.illnessCode = illnessCode;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    @Override
    public String getFullName() {
        return "";
    }

    public int setDocCode() {
        return this.doctorCode;
    }
    public int getPatCode() {return this.patientCode;}
    public int getReason() {
        return this.illnessCode;
    }
    public String getDescription() {return this.description;}

    public void setCode(int code) {
        this.code = code;
    }
    public void setDocCode(int doctorCode) {
        this.doctorCode = doctorCode;
    }
    public void setPatCode(int patientCode) {this.patientCode = patientCode;}
    public void setReason(int illnessCode) {
        this.illnessCode = illnessCode;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
