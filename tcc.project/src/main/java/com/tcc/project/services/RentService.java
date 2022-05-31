
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

	public Rent find(UUID uuid) {
		if (uuid == null) {
			return null;
		}

		for (Rent rent : rents) {
			if (rent.getUuid().equals(uuid)) {
				return rent;
			}
		}

		return null;
	}

	public ArrayList<Rent> findAllByUser(User user) {
		ArrayList<Rent> findedRents = new ArrayList<>();

		if (user == null) {
			return null;
		}

		if (rents.size() != 0) {
			for (Rent rent : rents) {
				if (rent.getUser().equals(user)) {
					findedRents.add(rent);
				}
			}
		}

		if (findedRents.isEmpty()) {
			return null;
		}

		return findedRents;
	}

	public Rent addRent(Rent rent) {
		User user = this.userService.findLoged();

		if (user == null) {
			return null;
		}

		if (rent == null) {
			return null;
		}
		rent.setUser(user);

		if (!this.checkRentsByUser(rent)) {
			return null;
		}

		rents.add(rent);

		return rent;
	}

	private Boolean checkRentsByUser(Rent rent) {
		if (rent.getBook().isRare()) {
			System.out.println("Cannot rent a rare book!");
			return false;
		}

		if (this.checkIfTheBookWasRentedByUser(rent.getUser(), rent.getBook())) {
			System.out.println("The book " + rent.getBook().getTitle() + " was already rented by " + rent.getUser().getName());
			return false;
		}

		return true;
	}

	private Boolean checkIfTheBookWasRentedByUser(User user, Book book) {
		ArrayList<Rent> rents = findAllByUser(user);

		if (rents != null) {
			for (Rent value : rents) {
				if (book.getUuid().equals(value.getBook().getUuid())) {
					return true;
				}
			}
		}

		return false;
	}
}
