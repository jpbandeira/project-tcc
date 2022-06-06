
package com.tcc.project.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import com.tcc.project.enums.TypeUser;
import com.tcc.project.model.Book;
import com.tcc.project.model.Fine;
import com.tcc.project.model.Rent;
import com.tcc.project.model.User;

public class RentService {

	private FineService fineService = new FineService();
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

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(rent.getRentDate());

		switch (rent.getUser().getType()){
			case STUDENT:
				calendar.add(Calendar.DAY_OF_MONTH, 15);
				break;
			case PROFESSOR:
				calendar.add(Calendar.DAY_OF_MONTH, 30);
				break;
		}
		rent.setDueDate(calendar.getTime());

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
			Calendar calendar = Calendar.getInstance();
			int dueDateDay = 0;
			int today = 0;

			switch (rent.getUser().getType()) {
				case STUDENT:
					if (books.size() == 5) {
						System.out.println("User with type " + TypeUser.STUDENT + " gets all books that he can");
						return false;
					}

					for (Rent value: rents) {
						calendar.setTime(value.getDueDate());
						dueDateDay = calendar.get(Calendar.DAY_OF_MONTH);
						today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
						if (dueDateDay < today) {
							this.fineService.addFine(new Fine(value.getUser()));
						}
					}
					break;
				case PROFESSOR:
					if (books.size() == 7) {
						System.out.println("User with type " + TypeUser.PROFESSOR + " gets all books that he can");
						return false;
					}

					for (Rent value: rents) {
						calendar.setTime(value.getDueDate());
						dueDateDay = calendar.get(Calendar.DAY_OF_MONTH);
						today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
						if (dueDateDay < today) {
							this.fineService.addFine(new Fine(value.getUser()));
						}
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
