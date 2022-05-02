
package com.tcc.project.services;

import java.awt.Window.Type;
import java.util.ArrayList;
import java.util.UUID;

import com.tcc.project.enums.TypeUser;
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

		if (rent.getBook().isRare()) {
			System.out.println("Cannot rent this book, because is rare!");
			return null;
		}
		
		if (this.findAllByUser() != null) {
			if (user.getType().equals(TypeUser.STUDENT) && this.findAllByUser().size() == 5) {
				System.out.println("User with type " + TypeUser.STUDENT + " gets all books that he can");
				return null;
			}

			if (user.getType().equals(TypeUser.PROFESSOR) && this.findAllByUser().size() == 7) {
				System.out.println("User with type " + TypeUser.PROFESSOR + " gets all books that he can");
				return null;
			}
		}

		rents.add(rent);

		return rents;
	}

	public ArrayList<Rent> findAllByUser() {
		User user = this.userService.findLoged();
		ArrayList<Rent> findedRents = new ArrayList<>();

		if (user == null) {
			return null;
		}

		for (Rent rent : rents) {
			if (rent.getUser().equals(user)) {
				findedRents.add(rent);
			}
		}

		if (findedRents.isEmpty()) {
			return null;
		}

		return rents;
	}

}
