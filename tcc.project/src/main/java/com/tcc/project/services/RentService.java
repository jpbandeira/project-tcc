
package com.tcc.project.services;

import java.util.ArrayList;
import java.util.UUID;

import com.tcc.project.enums.TypeUser;
import com.tcc.project.model.Book;
import com.tcc.project.model.Rent;
import com.tcc.project.model.User;

public class RentService {

	public static ArrayList<Rent> rents = new ArrayList<>();

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

	public Rent addRent(Rent rent, User user) {
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

	public void delete(UUID uuid) {
		Rent rent = new Rent();

		if (uuid != null) {
			rent = this.find(uuid);
		}

		if (rent != null) {
			rents.remove(rent);
		}
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

		ArrayList<Rent> books = this.findAllByUser(rent.getUser());

		if (books != null) {
			switch (rent.getUser().getType()) {
				case STUDENT:
					if (books.size() == 5) {
						System.out.println("User with type " + TypeUser.STUDENT + " gets all books that he can");
						return false;
					}
					break;
				case PROFESSOR:
					if (books.size() == 7) {
						System.out.println("User with type " + TypeUser.PROFESSOR + " gets all books that he can");
						return false;
					}
					break;
			}
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

	 public void returnABook(Rent rent) {
		Rent findedRent = null;

		if (rent != null) {
			findedRent = this.find(rent.getUuid());
		}

		if (findedRent != null) {
			this.delete(rent.getUuid());
		}
	 }
}
