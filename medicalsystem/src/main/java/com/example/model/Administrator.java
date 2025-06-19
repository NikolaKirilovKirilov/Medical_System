package com.example.model;

public class Administrator implements User{
	
	int id;
	String name;
	String clearance;
	String password;
	
	public Administrator() {};
	
	public Administrator(int id, String password) {
		this.id = id;	
		this.password = password;
	}
	
	public Administrator(int id, String name, String clearance, String password) {
		this.id = id;
		this.name = name;
		this.clearance = clearance;
		this.password = password;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getClearance() {
		return this.clearance;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setClearance(String clearance) {
		this.clearance = clearance;
	}
}
