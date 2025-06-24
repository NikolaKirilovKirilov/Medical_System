package com.example.model;

public class Doctor implements User{
	
	private int id;
	private String name, surname, specialization;
	
	public Doctor() {};
	
	public Doctor(int id, String name, String surname, String specialization) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.specialization = specialization;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}

	public String getSpecialization() {return this.specialization;}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setSpecialization(String specialization) { this.specialization = specialization; }

	@Override
	public String toString() {
		return id + " - Dr. " + name + " " + surname;
	}

	public String getFullName() {
		return name + " " + surname;
	}

	public int getCode() {
		return id;
	}


}
