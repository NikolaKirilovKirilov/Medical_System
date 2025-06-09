package com.example.model;

import jakarta.persistence.*;

@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String name;

	private String surname, specialization;
	
	public User() {
	}
	
	public User(String id, String name, String surname, String specialization) {
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