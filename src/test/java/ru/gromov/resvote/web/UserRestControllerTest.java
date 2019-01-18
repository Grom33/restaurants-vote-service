package ru.gromov.resvote.web;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import ru.gromov.resvote.service.UserService;
import ru.gromov.resvote.to.UserTo;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class UserRestControllerTest extends AbstractRestControllerTest {

	private static final String LOGGED_USER_IVAN = "json/logged_user_ivan.json";
	private static final String EDITED_USER = "json/edited_user.json";
	private static final String NEW_USER_TO = "json/new_user_to.json";
	private static final String IVAN_USER_TO = "json/ivan_user_to.json";

	@Autowired
	private UserService userService;

	@WithUserDetails("ivan@mail.ru")
	@SneakyThrows
	@Test
	public void getLoggedUser() {
		String json = util.getJsonString(util.getTestFile(LOGGED_USER_IVAN).toPath());
		mockMvc.perform(MockMvcRequestBuilders
				.get(REST_URL + "users"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(json));
	}

	@SneakyThrows
	@Test
	public void userRegistration() {
		String json = util.getJsonString(util.getTestFile(NEW_USER_TO).toPath());
		mockMvc.perform(post(REST_URL + "users/registration")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated());
		UserTo user = objectMapper.readValue(util.getTestFile(NEW_USER_TO), UserTo.class);
		assertEquals(user.getEmail(), userService.getUserByEmail(user.getEmail()).getEmail());
	}

	@SneakyThrows
	@Test
	public void tryToRegisterExistUser() {
		String json = util.getJsonString(util.getTestFile(IVAN_USER_TO).toPath());
		mockMvc.perform(post(REST_URL + "users/registration")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isConflict());

	}

}