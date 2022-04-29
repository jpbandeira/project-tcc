package com.tcc.project.model;

import java.util.Date;
import java.util.UUID;

public class Rent {

	private UUID uuid;
	private Date dueDate;
	private User user;
	private Book book;

	public Rent() {
	}

	public Rent(UUID uuid, Date dueDate) {
		super();
		this.uuid = uuid;
		this.dueDate = dueDate;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}
