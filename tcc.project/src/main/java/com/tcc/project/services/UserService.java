package com.tcc.project.services;

import java.util.ArrayList;
import java.util.UUID;

import com.tcc.project.model.User;

public class UserService {
	private User user = new User();
	private ArrayList<User> users = new ArrayList<User>();
	
	public ArrayList<User> addUser(User user) {
		this.users.add(user);

		return this.users;
	}
	
	public User find(UUID uuid) {
		for (User user : users) {
			if (user.getUuid().equals(uuid)) {
				return user;
			}
		}
		
		return null;
	}
	
	public ArrayList<User> findAll() {
		return this.users;
	}
	
	public void delete(UUID uuid) {
		User user = find(uuid);
		
		if(user != null) {
			this.users.remove(user);
		}
	}
}
