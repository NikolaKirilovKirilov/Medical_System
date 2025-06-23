package com.example.model;

public class Medication implements User {
	
	int code;
	private String name, description, dosage;
	
	public Medication(int code, String name, String description, String dosage) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.dosage = dosage;
	}
	
	public int getId() {
		return this.code;
	}
	public String getName() {
		return this.name;
	}
	public String getDescription() {
		return this.description;
	}
	public String getDosage() {
		return this.dosage;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
} 
