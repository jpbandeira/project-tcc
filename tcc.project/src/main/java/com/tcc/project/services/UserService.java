package com.tcc.project.services;

import java.util.ArrayList;
import java.util.UUID;

import com.tcc.project.enums.TypeUser;
import com.tcc.project.model.User;

public class UserService {
	public static ArrayList<User> users = new ArrayList<User>();
	
	public ArrayList<User> addUser(User user) {
		if (user == null) {
			return null;
		}
		
		users.add(user);

		return users;
	}
	
	public User findLoged() {		
		for (User user : users) {
			if (user.isAuthenticated()) {
				return user;
			}
		}
		
		return null;
	}
	
	public User find(UUID uuid) {
		if (uuid == null) {
			return null;
		}
		
		for (User user : users) {
			if (user.getUuid().equals(uuid)) {
				return user;
			}
		}
		
		return null;
	}
	
	public ArrayList<User> findAll() {
		if (users.isEmpty()) {
			return null;
		}
		
		return users;
	}
	
	public void delete(UUID uuid) {
		User user = find(uuid);
		
		if(user != null) {
			users.remove(user);
		}
	}
	
	public User update(User user) {
		if(user == null) {
			return null;
		}
		
		User userToUpdate = find(user.getUuid());
		
		if(userToUpdate == null ) {
			return null;
		}
		
		this.updateUser(user, userToUpdate);
		
		return userToUpdate;
	}
	
	private void updateUser(User newUser, User oldUser) {
		oldUser.setName(newUser.getName());
		oldUser.setEmail(newUser.getEmail());
		oldUser.setType(newUser.getType());
		oldUser.setRegistration(newUser.getRegistration());
		oldUser.setAuthenticated(newUser.isAuthenticated());
	}
	
}
