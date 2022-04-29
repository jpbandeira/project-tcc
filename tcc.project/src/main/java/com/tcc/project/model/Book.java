package com.tcc.project.model;

import java.util.UUID;

public class Book {

	private UUID uuid;
	private String title;
	private boolean rare;

	public Book() {
	}

	public Book(UUID uuid, String title, boolean rare) {
		super();
		this.uuid = uuid;
		this.title = title;
		this.rare = rare;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isRare() {
		return rare;
	}

	public void setRare(boolean rare) {
		this.rare = rare;
	}
}
