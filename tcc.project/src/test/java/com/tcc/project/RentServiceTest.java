
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

	private User student;
	private User professor;

	@Before
	public void before() {
		this.rentService = new RentService();
		this.userService = new UserService();
		this.bookService = new BookService();

		this.student = new User(UUID.randomUUID(), "Student", TypeUser.STUDENT, "student@email.com", "1710027");
		this.professor = new User(UUID.randomUUID(), "Professos", TypeUser.PROFESSOR, "professor@email.com", "1710028");
		this.userService.addUser(this.student);
		this.userService.addUser(this.professor);
	}

	private Rent setUp() {
		return new Rent(UUID.randomUUID(), new Date(), new Book(UUID.randomUUID(), "title", false));
	}

	private void cleanList() {
		RentService.rents.clear();
		UserService.users.clear();
		BookService.books.clear();
	}

	@Test
	public void rentShouldNotBeAddedWithNullUser() {
		this.cleanList();

		Rent rent = this.rentService.addRent(new Rent(), null);
		Assert.assertNull(rent);
	}

	@Test
	public void rentShouldNotBeAddedWithNull() {
		Rent rent = this.rentService.addRent(null, this.student);
		Assert.assertNull(rent);

		this.cleanList();
	}

	@Test
	public void rentShouldBeAddedWithStudentUser() {
		this.rentService.addRent(setUp(), this.student);
		this.rentService.addRent(setUp(), this.student);
		this.rentService.addRent(setUp(), this.student);
		this.rentService.addRent(setUp(), this.student);

		Rent rent= this.rentService.addRent(setUp(), this.student);
		Assert.assertNotNull(rent);
		Assert.assertNotNull(rent.getUser());

		this.cleanList();
	}

	@Test
	public void rentShouldBeAddedWithProfessorUser() {
		this.rentService.addRent(setUp(), this.professor);
		this.rentService.addRent(setUp(), this.professor);
		this.rentService.addRent(setUp(), this.professor);
		this.rentService.addRent(setUp(), this.professor);
		this.rentService.addRent(setUp(), this.professor);
		this.rentService.addRent(setUp(), this.professor);

		Rent rent = this.rentService.addRent(setUp(), this.professor);
		Assert.assertNotNull(rent);
		Assert.assertNotNull(rent.getUser());

		this.cleanList();
	}

	@Test
	public void rentShouldNotBeAddedWithARareBook() {
		Rent rentSetup = this.setUp();
		rentSetup.getBook().setRare(true);

		Rent rent = this.rentService.addRent(rentSetup, this.student);
		Assert.assertNull(rent);

		this.cleanList();
	}

	@Test
	public void rentShouldNotBeAddedWhenAUserAlreadyDidThis() {
		Rent rent1 = setUp();
		this.rentService.addRent(rent1, this.student);
		Rent rent2 = setUp();
		rent2.setBook(rent1.getBook());
		Rent rent = this.rentService.addRent(rent2, this.student);

		Assert.assertNull(rent);
		this.cleanList();
	}

	@Test
	public void rentShouldNotBeAddedWhenStudentUserGetsAllBooksInLimit() {
		this.rentService.addRent(setUp(), this.student);
		this.rentService.addRent(setUp(), this.student);
		this.rentService.addRent(setUp(), this.student);
		this.rentService.addRent(setUp(), this.student);
		this.rentService.addRent(setUp(), this.student);
		Rent rent = this.rentService.addRent(setUp(), this.student);

		Assert.assertNull(rent);
		this.cleanList();
	}

	@Test
	public void rentShouldNotBeAddedWhenProfessorUserGetsAllBooksInLimit() {
		this.rentService.addRent(setUp(), this.professor);
		this.rentService.addRent(setUp(), this.professor);
		this.rentService.addRent(setUp(), this.professor);
		this.rentService.addRent(setUp(), this.professor);
		this.rentService.addRent(setUp(), this.professor);
		this.rentService.addRent(setUp(), this.professor);
		this.rentService.addRent(setUp(), this.professor);
		Rent rent = this.rentService.addRent(setUp(), this.professor);

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
		this.rentService.addRent(setUp(), this.student);
		this.rentService.addRent(setUp(), this.student);

		Rent rent = this.rentService.find(UUID.randomUUID());
		Assert.assertNull(rent);

		this.cleanList();
	}

	@Test
	public void shouldFindARent() {
		Rent rent = setUp();
		this.rentService.addRent(rent, this.student);
		this.rentService.addRent(setUp(), this.student);

		Rent value = this.rentService.find(rent.getUuid());
		Assert.assertNotNull(value);

		this.cleanList();
	}

	@Test
	public void shouldNotFindAllByUserWithNullUser() {
		this.cleanList();

		ArrayList<Rent> rents = this.rentService.findAllByUser(null);
		Assert.assertNull(rents);
	}

	@Test
	public void shouldNotFindAllByUserWithoutRents() {
		ArrayList<Rent> rents = this.rentService.findAllByUser(this.student);
		Assert.assertNull(rents);

		this.cleanList();
	}

	@Test
	public void shouldFindAllByUser() {
		this.rentService.addRent(setUp(), this.student);
		this.rentService.addRent(setUp(), this.student);
		this.rentService.addRent(setUp(), this.student);

		ArrayList<Rent> rents = this.rentService.findAllByUser(this.student);
		Assert.assertNotNull(rents);

		this.cleanList();
	}
}
