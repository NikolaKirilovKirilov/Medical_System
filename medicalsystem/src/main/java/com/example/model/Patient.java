package com.example.model;

public class Patient implements User{
	private int id;
	private String name, surname;
	
	public Patient() {};
	
	public Patient(int id, String name, String surname) {
		this.id = id;
		this.name = name;
		this.surname = surname;
	};
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return id + " - " + name + " " + surname;
	}

	public String getFullName() {
		return name + " " + surname;
	}

	public int getCode() {
		return id;
	}
}
