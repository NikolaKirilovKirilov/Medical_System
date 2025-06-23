package com.example.model;

public class Appointment implements User {

    int code, doctorCode, patientCode;
    private String reason, description;

    public Appointment(int code, int doctorCode, int patientCode, String reason, String description) {
        this.code = code;
        this.doctorCode = doctorCode;
        this.patientCode = patientCode;
        this.reason = reason;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }
    public int setDocCode() {
        return this.doctorCode;
    }
    public int getPatCode() {return this.patientCode;}
    public String getReason() {
        return this.reason;
    }
    public String getDescription() {return this.description;}

    public void setCode(int code) {
        this.code = code;
    }
    public void setDocCode(int doctorCode) {
        this.doctorCode = doctorCode;
    }
    public void setPatCode(int patientCode) {this.patientCode = patientCode;}
    public void setReason(String reason) {
        this.reason = reason;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
