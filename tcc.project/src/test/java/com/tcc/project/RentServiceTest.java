
package com.tcc.project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.tcc.project.model.Fine;
import com.tcc.project.services.FineService;
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
	private FineService fineService;

	private User student;
	private User professor;

	@Before
	public void before() {
		this.rentService = new RentService();
		this.userService = new UserService();
		this.fineService = new FineService();

		this.student = new User(UUID.randomUUID(), "Student", TypeUser.STUDENT, "student@email.com", "1710027");
		this.professor = new User(UUID.randomUUID(), "Professos", TypeUser.PROFESSOR, "professor@email.com", "1710028");
		this.userService.addUser(this.student);
		this.userService.addUser(this.professor);
	}

	private Rent setUp() {
		return new Rent(new Book(UUID.randomUUID(), "title", false));
	}

	private void cleanList() {
		RentService.rents.clear();
		UserService.users.clear();
		FineService.fines.clear();
	}

	private Date getFakeDate(TypeUser typeUser) {
		if (typeUser.equals(TypeUser.STUDENT)) {
			Calendar fakeDate = Calendar.getInstance();
			fakeDate.add(Calendar.DAY_OF_MONTH, -19);
			return fakeDate.getTime();
		} else {
			Calendar fakeDate = Calendar.getInstance();
			fakeDate.add(Calendar.DAY_OF_MONTH, -34);
			return fakeDate.getTime();
		}
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
	public void fineShouldNotBeAddedWhenTheStudentStatusIsOk() {
		this.rentService.addRent(setUp(), this.student);
		Rent rent = setUp();
		this.rentService.addRent(rent, this.student);

		Assert.assertNull(this.fineService.findAllByUser(rent.getUser()));
		this.cleanList();
	}

	@Test
	public void fineShouldNotBeAddedWhenTheProfessorStatusIsOk() {
		this.rentService.addRent(setUp(), this.professor);
		this.rentService.addRent(setUp(), this.professor);

		Assert.assertNull(this.fineService.findAllByUser(this.professor));
		this.cleanList();
	}

	@Test
	public void fineShouldNotBeAddedWhenRentDateIsEqualDueDate() {
		Rent rent = this.rentService.addRent(setUp(), this.student);
		this.rentService.find(rent.getUuid()).setDueDate(rent.getRentDate());
		this.rentService.addRent(setUp(), this.student);

		Assert.assertNull(this.fineService.findAllByUser(this.student));
		this.cleanList();
	}

	@Test
	public void fineShouldBeAddedWhenAStudentHasOneOrMoreRentOutOfDueDate() {
		Date fakeDate = getFakeDate(this.student.getType());
		Rent rent = setUp();
		rent.setRentDate(fakeDate);
		this.rentService.addRent(rent, this.student);
		Rent rentAdded = this.rentService.addRent(setUp(), this.student);
		Assert.assertNotEquals(rentAdded.getRentDate(), rentAdded.getDueDate());
		Assert.assertNotEquals(0, this.fineService.findAllByUser(rent.getUser()).size());

		this.cleanList();
	}

	@Test
	public void fineShouldBeAddedWhenAProfessorHasOneOrMoreRentOutOfDueDate() {
		Date fakeDate = getFakeDate(this.professor.getType());
		Rent rent = setUp();
		rent.setRentDate(fakeDate);
		this.rentService.addRent(rent, this.professor);
		Rent rentAdded = this.rentService.addRent(setUp(), this.professor);
		Assert.assertNotEquals(rentAdded.getRentDate(), rentAdded.getDueDate());
		Assert.assertNotEquals(0, this.fineService.findAllByUser(rent.getUser()).size());

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

	@Test
	public void shouldNotDeleteARentWithANullUUID() {
		Rent rent1 = setUp();
		Rent rent2 = setUp();
		this.rentService.addRent(rent1, this.student);
		this.rentService.addRent(rent2, this.student);

		this.rentService.delete(null);

		Assert.assertNotNull(this.rentService.find(rent1.getUuid()));
		Assert.assertNotNull(this.rentService.find(rent2.getUuid()));
		this.cleanList();
	}

	@Test
	public void shouldNotDeleteARentWithAnInvalidUUID() {
		Rent rent1 = setUp();
		Rent rent2 = setUp();
		this.rentService.addRent(rent1, this.student);
		this.rentService.addRent(rent2, this.student);

		UUID uuid = UUID.randomUUID();
		this.rentService.delete(uuid);

		Assert.assertNotNull(this.rentService.find(rent1.getUuid()));
		Assert.assertNotNull(this.rentService.find(rent2.getUuid()));
		this.cleanList();
	}

	@Test
	public void shouldDeleteARent() {
		Rent rent1 = setUp();
		Rent rent2 = setUp();
		this.rentService.addRent(rent1, this.student);
		this.rentService.addRent(rent2, this.student);

		this.rentService.delete(rent1.getUuid());

		Assert.assertNull(this.rentService.find(rent1.getUuid()));
		Assert.assertNotNull(this.rentService.find(rent2.getUuid()));
		this.cleanList();
	}

	@Test
	public void shouldNotReturnABookWithANullRent() {
		Rent rent1 = setUp();
		Rent rent2 = setUp();
		this.rentService.addRent(rent1, this.student);
		this.rentService.addRent(rent2, this.student);

		this.rentService.returnABook(null);

		Assert.assertNotNull(this.rentService.find(rent1.getUuid()));
		Assert.assertNotNull(this.rentService.find(rent2.getUuid()));
		this.cleanList();
	}

	@Test
	public void shouldNotReturnARentWithAnInvalidUUID() {
		Rent rent1 = setUp();
		Rent rent2 = setUp();
		this.rentService.addRent(rent1, this.student);
		this.rentService.addRent(rent2, this.student);

		Rent invalidRent = setUp();
		this.rentService.returnABook(invalidRent);

		Assert.assertNotNull(this.rentService.find(rent1.getUuid()));
		Assert.assertNotNull(this.rentService.find(rent2.getUuid()));
		this.cleanList();
	}

	@Test
	public void shouldReturnARent() {
		Rent rent1 = setUp();
		Rent rent2 = setUp();
		this.rentService.addRent(rent1, this.student);
		this.rentService.addRent(rent2, this.student);

		this.rentService.returnABook(rent1);

		Assert.assertNull(this.rentService.find(rent1.getUuid()));
		Assert.assertNotNull(this.rentService.find(rent2.getUuid()));
		this.cleanList();
	}

	@Test
	public void testar() {
		Calendar fakeDate = Calendar.getInstance();
		fakeDate.add(Calendar.DAY_OF_MONTH, -16);
		Date date = fakeDate.getTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 15);

		int dueDatePlus16 = calendar.get(Calendar.DAY_OF_MONTH);
		int dayOfMonthToday = Calendar.DAY_OF_MONTH;
		if (dueDatePlus16 < dayOfMonthToday) {
			System.out.printf("");
		}
	}
}
