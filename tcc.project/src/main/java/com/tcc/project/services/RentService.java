
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

		if (!this.checkRentsByUser(rent)) {
			return null;
		}

		rents.add(rent);

		return rents;
	}

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

	public void delete(UUID uuid) {
		Rent rent = new Rent();

		if (uuid != null) {
			rent = this.find(uuid);
		}

		if (rent != null) {
			rents.remove(rent);
		}
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

	private Boolean checkRentsByUser(Rent rent) {
		if (rent.getBook().isRare()) {
			System.out.println("Cannot rent this book, because is rare!");
			return false;
		}

		if (!this.checkIfTheBookWasRentedByUser(rent.getUser(), rent.getBook())) {
			System.out.println("Cannot rent this book, because you already did this!");
			return false;
		}

		if (this.findAllByUser() != null) {

			switch (rent.getUser().getType()) {
			case STUDENT:
				if (this.findAllByUser().size() >= 5) {
					System.out.println("User with type " + TypeUser.STUDENT + " gets all books that he can");
					return false;
				}
				break;
			case PROFESSOR:
				if (this.findAllByUser().size() >= 7) {
					System.out.println("User with type " + TypeUser.PROFESSOR + " gets all books that he can");
					return false;
				}
				break;
			}

		}

		return true;
	}

	private Boolean checkIfTheBookWasRentedByUser(User user, Book book) {
		for (Rent value : rents) {
			if (book.getUuid().equals(value.getBook().getUuid())) {
				return false;
			}
		}

		return true;
	}

	/*
	 * public void returnABook(Rent rent) { User user =
	 * this.userService.findLoged();
	 * 
	 * 
	 * }
	 */
	
}
