package ru.gromov.resvote.security;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ru.gromov.resvote.AbstractTest;
import ru.gromov.resvote.util.exception.UserNotFoundException;
import ru.gromov.resvote.web.AbstractRestControllerTest;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class SecurityServiceTest extends AbstractTest {

	@Autowired
	SecurityService securityService;

	@WithMockUser(value = "admin111@mail.ru")
	@SneakyThrows
	@Test(expected = UserNotFoundException.class )
	public void getLoggedUser() {
		securityService.getLoggedUser();
	}

}