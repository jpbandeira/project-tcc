package com.tcc.project.model;

import java.util.UUID;

import com.tcc.project.enums.TypeUser;

public class User {

	private UUID uuid;
	private String name;
	private TypeUser type;
	private String email;
	private String registration;
	private Boolean authenticated;

	public User() {
	}

	public User(UUID uuid, String name, TypeUser type, String email, String registration, Boolean authenticated) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.type = type;
		this.email = email;
		this.registration = registration;
		this.authenticated = authenticated;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeUser getType() {
		return type;
	}

	public void setType(TypeUser type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public Boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}
}
