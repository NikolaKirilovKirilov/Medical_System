package com.example.model;

public class Disease implements User {
	
	int code;
	private String name, description, treatment;
	
	public Disease(int code, String name, String description, String treatment) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.treatment = treatment;
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
	public String getTreatment() {
		return this.treatment;
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
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	@Override
	public int getCode() {
		return 0;
	}

	@Override
	public String getFullName() {
		return "";
	}
}
