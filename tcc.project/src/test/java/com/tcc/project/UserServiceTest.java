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

	private UserService userService;

	private User setUp() {
		return new User(UUID.randomUUID(), "name", TypeUser.STUDENT, "email@email.com", "1710027", true);
	}

	@Before
	public void before() {
		this.userService = new UserService();
	}

	private void cleanList() {
		UserService.users.clear();
	}

	@Test
	public void userShouldNotBeAdded() {
		ArrayList<User> users = this.userService.addUser(null);

		Assert.assertNull(null, users);
	}

	@Test
	public void userShouldBeAdded() {
		User usersetUp = setUp();
		ArrayList<User> users = this.userService.addUser(usersetUp);

		Assert.assertNotEquals(null, users);
		Assert.assertEquals(usersetUp, users.get(0));
		this.cleanList();
	}

	@Test
	public void shouldNotGetAllUsers() {
		ArrayList<User> users = this.userService.findAll();

		Assert.assertNull(users);
	}

	@Test
	public void shouldGetAllUsers() {
		User usersetUp = setUp();
		this.userService.addUser(usersetUp);

		ArrayList<User> users = this.userService.findAll();

		Assert.assertNotEquals(0, users.size());
		Assert.assertNotNull(users);
		this.cleanList();
	}

	@Test
	public void findUsersWithANullUUID() {
		User user = this.userService.find(null);

		Assert.assertNull(user);

	}

	@Test
	public void findUsersWithAInvalidUUID() {
		User usersetUp = setUp();
		this.userService.addUser(usersetUp);

		User user = this.userService.find(UUID.randomUUID());

		Assert.assertNull(user);
		this.cleanList();
	}

	@Test
	public void shouldFindAUser() {
		User usersetUp = setUp();
		this.userService.addUser(usersetUp);

		User user = this.userService.find(usersetUp.getUuid());

		Assert.assertNotNull(user);
		this.cleanList();
	}
	
	@Test
	public void shouldNotFindAAuthenticatedUser() {
		User usersetUp = setUp();
		usersetUp.setAuthenticated(false);
		this.userService.addUser(usersetUp);
		
		User user = this.userService.findLoged();
		
		Assert.assertNull(user);
		this.cleanList();
	}
	
	@Test
	public void shouldFindAUserAuthenticated() {
		this.userService.addUser(setUp());
		
		User user = this.userService.findLoged();
		
		Assert.assertNotNull(user);
		Assert.assertTrue(user.isAuthenticated());
		this.cleanList();
	}

	@Test
	public void deleteAUserWithAInvalidUUID() {
		User usersetUp = setUp();
		this.userService.addUser(usersetUp);

		this.userService.delete(UUID.randomUUID());

		Assert.assertNotNull(this.userService.find(usersetUp.getUuid()));
		this.cleanList();
	}

	@Test
	public void deleteAUserWithANullUUID() {
		User usersetUp = setUp();
		this.userService.addUser(usersetUp);

		this.userService.delete(null);

		Assert.assertNotNull(this.userService.find(usersetUp.getUuid()));
		this.cleanList();
	}

	@Test
	public void shouldBeDeleted() {
		User usersetUp = setUp();
		this.userService.addUser(usersetUp);

		this.userService.delete(usersetUp.getUuid());

		Assert.assertNull(this.userService.find(usersetUp.getUuid()));
		this.cleanList();
	}

	@Test
	public void updateAUSerWithANullObject() {
		User user = this.userService.update(null);

		Assert.assertNull(user);
	}

	@Test
	public void updateAUserWithAInvalidObject() {
		User userSetUp = setUp();
		this.userService.addUser(setUp());

		User user = this.userService.update(userSetUp);

		Assert.assertNull(user);
		this.cleanList();
	}

	@Test
	public void shouldBeUpdated() {
		User usersetUp = setUp();
		this.userService.addUser(usersetUp);

		User userToUpdate = new User(usersetUp.getUuid(), "Name User 2", TypeUser.PROFESSOR, "user2@gmail.com", "1710039", true);

		User user = this.userService.update(userToUpdate);

		Assert.assertEquals(userToUpdate.getType(), user.getType());
		Assert.assertNotNull(user);
		this.cleanList();
	}
}
