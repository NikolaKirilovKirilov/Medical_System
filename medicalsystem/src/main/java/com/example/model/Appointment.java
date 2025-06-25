package com.example.model;

public class Appointment implements User {

    int code, doctorCode, patientCode;
    private String reason, description, date, hours, status;

    public Appointment(int doctorCode, int patientCode, String reason, String description, String date, String hours, String status, int code) {
        this.code = code;
        this.doctorCode = doctorCode;
        this.patientCode = patientCode;
        this.reason = reason;
        this.description = description;
        this.date = date;
        this.hours = hours;
        this.status = status;
    }

    public int getCode() {
        return this.code;
    }

    @Override
    public String getFullName() {
        return "";
    }

    public int getAppointmentCode() {return this.code;}
    public int getDocCode() {
        return this.doctorCode;
    }
    public int getPatCode() {return this.patientCode;}
    public String getReason() {
        return this.reason;
    }
    public String getDescription() {return this.description;}
    public String getDate() {return this.date;}
    public String getHours() {return this.hours;}
    public String getStatus() {return this.status;}

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
    public void setDescription(String description) { this.description = description; }
    public void setDate(String date) {this.date = date;}
    public void setHours(String hours) {this.hours = hours;}
    public void setStatus(String status) {this.status = status;}
}
