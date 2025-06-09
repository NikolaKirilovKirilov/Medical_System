package com.example.model;

public class Doctor extends User{
	
	private String id, name, surname, specialization;
	
	public Doctor() {
		super();
	}
	
	public Doctor(String id, String name, String surname, String specialization) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.specialization = specialization;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public String getspecialization() {
		return this.specialization;
	}
}
