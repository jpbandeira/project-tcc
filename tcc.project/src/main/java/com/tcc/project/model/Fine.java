package com.tcc.project.model;

import java.util.UUID;

public class Fine {
	
	private UUID uuid;
	private boolean paid;
	private User user;
		
	public Fine() {}

	public Fine(User user) {
		this.user = user;
	}

	public Fine(Fine fine) {
		this.user = fine.getUser();
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
