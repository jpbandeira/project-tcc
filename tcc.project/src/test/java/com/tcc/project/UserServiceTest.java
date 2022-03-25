package com.tcc.project;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tcc.project.enums.TypeUser;
import com.tcc.project.model.User;

public class UserServiceTest {
	
	private User user;
	
	@BeforeEach
	public void before() {
		this.user = new User(UUID.randomUUID(), "name", TypeUser.STUDENT, "email@email.com", "1710027");
	}
	
	@Test
	public void shouldBeAdded() {
		
	}
}
