package com.example.model;

public class Administrator implements User{
	
	int code;
	String name;
	String clearance;
	String password;
	
	public Administrator() {};

	public Administrator(int code) {
		this.code = code;
	};
	
	public Administrator(int code, String password) {
		this.code = code;
		this.password = password;
	}
	
	public Administrator(int code, String name, String clearance, String password) {
		this.code = code;
		this.name = name;
		this.clearance = clearance;
		this.password = password;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getClearance() {
		return this.clearance;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setClearance(String clearance) {
		this.clearance = clearance;
	}
}
