package com.example.model;

public class Specialization implements User {
    int code;
    String name, description;

    public Specialization(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    @Override
    public int getCode() { return this.code;}
    public String getName() { return this.name;}
    public String getDescription() { return this.description;}

    @Override
    public String getFullName() {
        return "";
    }

    public void setCode(int code) { this.code = code; }
    public void setName(String name) { this.name = name; }
    public void getDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return code + " - " + name;
    }

}
