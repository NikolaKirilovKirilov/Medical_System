package com.example.model;

public class Prescription implements User {

    int code, doctorCode, patientCode, illnessCode, medicationCode;

    public Prescription(int code, int doctorCode, int patientCode, int illnessCode, int medicationCode) {
        this.code = code;
        this.doctorCode = doctorCode;
        this.patientCode = patientCode;
        this.illnessCode = illnessCode;
        this.medicationCode = medicationCode;
    }

    public int getCode() {
        return this.code;
    }
    public int getDocCode() {
        return this.doctorCode;
    }
    public int getPatCode() {return this.patientCode;}
    public int getIllCode() {
        return this.illnessCode;
    }
    public int getMedCode() {return this.medicationCode;}

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
    public void setDescription(int medicationCode) {
        this.medicationCode = medicationCode;
    }
}
