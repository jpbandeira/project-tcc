
package com.tcc.project.services;

import java.util.ArrayList;
import java.util.UUID;

import com.tcc.project.enums.TypeUser;
import com.tcc.project.model.Book;
import com.tcc.project.model.Rent;
import com.tcc.project.model.User;

public class RentService {

	public static ArrayList<Rent> rents = new ArrayList<>();

	private UserService userService = new UserService();
	private BookService bookService = new BookService();

	public ArrayList<Rent> addRent(Rent rent) {
		User user = this.userService.findLoged();

		if (user == null) {
			return null;
		}

		if (rent == null) {
			return null;
		}
		rent.setUser(user);

		rents.add(rent);

		return rents;
	}
}
