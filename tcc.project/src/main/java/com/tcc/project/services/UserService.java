package com.tcc.project.services;

import java.util.ArrayList;

import com.tcc.project.model.User;

public class UserService {
	private User user = new User();
	private ArrayList<User> users = new ArrayList<User>();
	
	public ArrayList<User> addUser(User user) {
		this.users.add(user);

		return users;
	}
}
