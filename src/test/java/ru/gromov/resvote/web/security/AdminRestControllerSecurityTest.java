package ru.gromov.resvote.web.security;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

public class AdminRestControllerSecurityTest extends AbstractSecurityControllerTest {

	private static final String NEW_USER = "json/new_user.json";
	private static final String EDITED_USER = "json/edited_user.json";

	@SneakyThrows
	@Test
	public void getAllUsersByUser() {
		mockMvc.perform(get(REST_URL + "admin/users").with(user("ivan@mail.ru")))
				.andDo(print())
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void getAllUsersByAnonymous() {
		mockMvc.perform(get(REST_URL + "admin/users").with(anonymous()))
				.andDo(print())
				.andExpect(status().isUnauthorized());
	}


	@SneakyThrows
	@Test
	public void createByUser() {
		String json = util.getJsonString(util.getTestFile(NEW_USER).toPath());
		mockMvc.perform(post(REST_URL + "admin/users")
				.with(user("ivan@mail.ru"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void createByAnonymous() {
		String json = util.getJsonString(util.getTestFile(NEW_USER).toPath());
		mockMvc.perform(post(REST_URL + "admin/users")
				.with(anonymous())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isUnauthorized());
	}

	@SneakyThrows
	@Test
	public void getUserByUser() {
		final int userId = 2;
		mockMvc.perform(get(REST_URL + "admin/users/" + userId)
				.with(user("ivan@mail.ru")))
				.andExpect(status().isForbidden());
	}


	@SneakyThrows
	@Test
	public void getUserByAnonymous() {
		final int userId = 2;
		mockMvc.perform(get(REST_URL + "admin/users/" + userId)
				.with(anonymous()))
				.andExpect(status().isUnauthorized());
	}

	@SneakyThrows
	@Test
	public void updateByUser() {
		String json = util.getJsonString(util.getTestFile(EDITED_USER).toPath());
		mockMvc.perform(put(REST_URL + "admin/users")
				.with(user("ivan@mail.ru"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void updateByAnonymous() {
		String json = util.getJsonString(util.getTestFile(EDITED_USER).toPath());
		mockMvc.perform(put(REST_URL + "admin/users")
				.with(anonymous())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isUnauthorized());
	}

	@SneakyThrows
	@Test
	public void deleteUserByUser() {
		final int userId = 2;
		mockMvc.perform(delete(REST_URL + "admin/users/" + userId)
				.with(user("ivan@mail.ru")))
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void deleteUserByAnonymous() {
		final int userId = 2;
		mockMvc.perform(delete(REST_URL + "admin/users/" + userId)
				.with(anonymous()))
				.andExpect(status().isUnauthorized());
	}

}
