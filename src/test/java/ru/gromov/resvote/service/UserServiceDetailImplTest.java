package ru.gromov.resvote.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import ru.gromov.resvote.AbstractTest;

import static org.junit.Assert.*;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

public class UserServiceDetailImplTest extends AbstractTest {

	@Autowired
	UserServiceDetailImpl userServiceDetail;

	@Test
	public void loadUserByUsername() {
		final String username = "ivan@mail.ru";
		UserDetails user = userServiceDetail.loadUserByUsername(username);
		assertEquals(username, user.getUsername());
	}
}