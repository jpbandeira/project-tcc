package com.tcc.project;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tcc.project.enums.TypeUser;
import com.tcc.project.model.User;
import com.tcc.project.services.UserService;

public class UserServiceTest {
	
	private User user;
	private UserService userService;
	
	@Before
	public void before() {
		this.user = new User(UUID.randomUUID(), "name", TypeUser.STUDENT, "email@email.com", "1710027");
		this.userService = new UserService();		
	}
	
	@Test
	public void userShouldBeAdded() {
		ArrayList<User> users = this.userService.addUser(this.user);
		
		Assert.assertNotEquals(null, users);
		Assert.assertEquals(this.user, users.get(0));
	}
	
	@Test
	public void shouldGetAllUsers() {
		this.userService.addUser(this.user);
		ArrayList<User> users = this.userService.findAll();
		Assert.assertNotEquals(0, users.size());
		Assert.assertNotNull(users);
	}
	
	@Test
	public void shouldGetUserBeNull() {		
		this.userService.addUser(this.user);
		User user = this.userService.find(UUID.randomUUID());
		
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldGetUser() {
		this.userService.addUser(this.user);
		User user = this.userService.find(this.user.getUuid());
		
		Assert.assertNotNull(user);
	}
	
	@Test
	public void shouldNotBeDeleted() {
		this.userService.addUser(this.user);
		this.userService.delete(null);
		
		Assert.assertNotNull(this.userService.find(this.user.getUuid()));
	}
	
	@Test
	public void shouldBeDeleted() {
		this.userService.addUser(this.user);
		this.userService.delete(this.user.getUuid());
		
		Assert.assertNull(this.userService.find(this.user.getUuid()));
	}
	
	@Test
	public void shouldBeUpdated() {}
	
}
