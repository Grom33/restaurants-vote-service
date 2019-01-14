package ru.gromov.resvote.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.gromov.resvote.AbstractTest;

import static org.junit.Assert.*;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class UserServiceImplTest extends AbstractTest {

	@Autowired
	UserService userService;

	@Test(expected = UsernameNotFoundException.class)
	public void getUserByEmail() {
		final String wrongEmail = "WRONG_NAME";
		userService.getUserByEmail(wrongEmail);
	}
}