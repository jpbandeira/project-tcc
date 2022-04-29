package com.tcc.project.services;

import java.util.ArrayList;
import java.util.UUID;

import com.tcc.project.model.Rent;
import com.tcc.project.model.User;

public class RentService {
	private Rent rent = new Rent();
	private ArrayList<Rent> rents = new ArrayList<>();

	private UserService userService = new UserService();

	public ArrayList<Rent> addRent(Rent rent, User user) {
		User validUser = new User();
		
		if(rent == null) {
			return null;
		}
		
		if(user == null) {
			return null;
		}
		
		validUser = this.userService.find(user.getUuid());
		
		if(validUser == null) {
			return null;
		}
		
		this.rents.add(rent);

		return this.rents;
	}

}
