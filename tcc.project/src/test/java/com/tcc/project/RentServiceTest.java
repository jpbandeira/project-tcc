
package com.tcc.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tcc.project.enums.TypeUser;
import com.tcc.project.model.Book;
import com.tcc.project.model.Rent;
import com.tcc.project.model.User;
import com.tcc.project.services.BookService;
import com.tcc.project.services.RentService;
import com.tcc.project.services.UserService;

public class RentServiceTest {

	private RentService rentService;
	private UserService userService;
	private BookService bookService;

	@Before
	public void before() {
		this.rentService = new RentService();
		this.userService = new UserService();
		this.bookService = new BookService();

		this.userService.addUser(
				new User(UUID.randomUUID(), "Student", TypeUser.STUDENT, "student@email.com", "1710027", false));
		this.userService.addUser(
				new User(UUID.randomUUID(), "Professos", TypeUser.PROFESSOR, "professor@email.com", "1710028", false));
	}

	private Rent setUp() {
		return new Rent(UUID.randomUUID(), new Date(), new Book(UUID.randomUUID(), "title", false));
	}

	private void cleanList() {
		RentService.rents.clear();
		UserService.users.clear();
		BookService.books.clear();
	}

	private User makeUserBeAuthenticated(TypeUser type) {
		for (User user : this.userService.findAll()) {
			if (user.getType().equals(type)) {
				user.setAuthenticated(true);
				return user;
			}
		}

		return null;
	}

	@Test
	public void rentShouldNotBeAddedWithoutUserRegistered() {
		this.cleanList();

		Rent rent = this.rentService.addRent(new Rent());
		Assert.assertNull(rent);
	}

	@Test
	public void rentShouldNotBeAddedWithNull() {
		this.makeUserBeAuthenticated(TypeUser.STUDENT);

		Rent rent = this.rentService.addRent(null);
		Assert.assertNull(rent);

		this.cleanList();
	}

	@Test
	public void rentShouldBeAddedWithStudentUser() {
		this.makeUserBeAuthenticated(TypeUser.STUDENT);

		this.rentService.addRent(setUp());
		this.rentService.addRent(setUp());
		this.rentService.addRent(setUp());
		this.rentService.addRent(setUp());
		Rent rent= this.rentService.addRent(setUp());
		Assert.assertNotNull(rent);
		Assert.assertNotNull(rent.getUser());

		this.cleanList();
	}

	@Test
	public void rentShouldBeAddedWithProfessorUser() {
		this.makeUserBeAuthenticated(TypeUser.PROFESSOR);

		this.rentService.addRent(setUp());
		this.rentService.addRent(setUp());
		this.rentService.addRent(setUp());
		this.rentService.addRent(setUp());
		this.rentService.addRent(setUp());
		this.rentService.addRent(setUp());
		Rent rent = this.rentService.addRent(setUp());
		Assert.assertNotNull(rent);
		Assert.assertNotNull(rent.getUser());

		this.cleanList();
	}

	@Test
	public void rentShouldNotBeAddedWithARareBook() {
		this.makeUserBeAuthenticated(TypeUser.STUDENT);

		Rent rentSetup = this.setUp();
		rentSetup.getBook().setRare(true);

		Rent rent = this.rentService.addRent(rentSetup);
		Assert.assertNull(rent);

		this.cleanList();
	}

	@Test
	public void rentShouldNotBeAddedWhenAUserAlreadyDidThis() {
		this.makeUserBeAuthenticated(TypeUser.STUDENT);

		Rent rent1 = setUp();
		this.rentService.addRent(rent1);
		Rent rent2 = setUp();
		rent2.setBook(rent1.getBook());
		Rent rent = this.rentService.addRent(rent2);

		Assert.assertNull(rent);
		this.cleanList();
	}

	@Test
	public void shouldNotFindARentWithANullUUID() {
		Rent rent = this.rentService.find(null);
		Assert.assertNull(rent);

		this.cleanList();
	}

	@Test
	public void shouldNotFindARentWithAInvalidUUID() {
		this.makeUserBeAuthenticated(TypeUser.STUDENT);

		this.rentService.addRent(setUp());
		this.rentService.addRent(setUp());

		Rent rent = this.rentService.find(UUID.randomUUID());
		Assert.assertNull(rent);

		this.cleanList();
	}

	@Test
	public void shouldFindARent() {
		this.makeUserBeAuthenticated(TypeUser.STUDENT);

		Rent rent = setUp();
		this.rentService.addRent(rent);
		this.rentService.addRent(setUp());

		Rent value = this.rentService.find(rent.getUuid());
		Assert.assertNotNull(value);

		this.cleanList();
	}

	@Test
	public void shouldNotFindAllByUserNullUser() {
		this.cleanList();
		//User user = this.makeUserBeAuthenticated(TypeUser.STUDENT);

		ArrayList<Rent> rents = this.rentService.findAllByUser(null);

		Assert.assertNull(rents);
	}

	@Test
	public void shouldNotFindAllByUserWithoutRents() {
		User user = this.makeUserBeAuthenticated(TypeUser.STUDENT);

		ArrayList<Rent> rents = this.rentService.findAllByUser(user);
		Assert.assertNull(rents);

		this.cleanList();
	}

	@Test
	public void shouldFindAllByUser() {
		User user = this.makeUserBeAuthenticated(TypeUser.STUDENT);

		this.rentService.addRent(setUp());
		this.rentService.addRent(setUp());
		this.rentService.addRent(setUp());

		ArrayList<Rent> rents = this.rentService.findAllByUser(user);
		Assert.assertNotNull(rents);

		this.cleanList();
	}
}
