package com.tcc.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tcc.project.enums.TypeUser;
import com.tcc.project.model.Rent;
import com.tcc.project.model.User;
import com.tcc.project.services.RentService;
import com.tcc.project.services.UserService;

public class RentServiceTest {

	private RentService rentService;
	private UserService userService;

	private User user;

	private Rent setUp() {
		this.user = new User(UUID.randomUUID(), "name", TypeUser.STUDENT, "email@email.com", "1710027");

		return new Rent(UUID.randomUUID(), new Date());
	}

	@Before
	public void before() {
		this.rentService = new RentService();
		this.userService = new UserService();
	}

	@Test
	public void rentShouldNotBeAdded() {
		ArrayList<Rent> rents = this.rentService.addRent(null, this.user);
		
		Assert.assertNull(rents);
	}
	
	@Test
	public void addRentWithInvalidUser() {
		Rent rentsetUp = setUp();
		rentsetUp.setUser(null);
		
		ArrayList<Rent> rents = this.rentService.addRent(rentsetUp, null);
		
		Assert.assertNull(rents);
	}
	
	@Test
	public void addRentWithUnregisteredUser() {
		Rent rentsetUp = setUp();
		
		ArrayList<Rent> rents = this.rentService.addRent(rentsetUp, this.user);
		
		Assert.assertNull(rents);
	}
	
	@Test
	public void rentShouldBeAdded() {
		Rent rentsetUp = setUp();
		userService.addUser(this.user);

		ArrayList<Rent> rents = this.rentService.addRent(rentsetUp, this.user);

		Assert.assertNotNull(rents);
	}


	/*
	 * @Test public void rentShouldBeDelete() { this.rentService }
	 */

}
