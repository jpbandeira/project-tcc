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
	public void shouldBeAdded() {
		ArrayList<User> users = new ArrayList<User>();
		users = this.userService.addUser(user);
		
		Assert.assertNotEquals(null, this.user);
		Assert.assertEquals(this.user, users.get(0));
	}//
	
}
