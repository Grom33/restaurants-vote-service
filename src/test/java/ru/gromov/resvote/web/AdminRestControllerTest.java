package ru.gromov.resvote.web;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class AdminRestControllerTest extends AbstractRestControllerTest {

	private static final String USERS_LIST = "json/users_list.json";
	private static final String LOGGED_USER_IVAN = "json/logged_user_ivan.json";
	private static final String NEW_USER = "json/new_user.json";
	private static final String EDITED_USER = "json/edited_user.json";

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getAllUsers() {
		String json = util.getJsonString(util.getTestFile(USERS_LIST).toPath());
		mockMvc.perform(get(REST_URL + "admin/users"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(json));
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void create() {
		String json = util.getJsonString(util.getTestFile(NEW_USER).toPath());
		mockMvc.perform(post(REST_URL + "admin/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getUser() {
		final int userId = 2;
		String json = util.getJsonString(util.getTestFile(LOGGED_USER_IVAN).toPath());
		mockMvc.perform(get(REST_URL + "admin/users/" + userId))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(json));
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void update() {
		String json = util.getJsonString(util.getTestFile(EDITED_USER).toPath());
		mockMvc.perform(put(REST_URL + "admin/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void deleteUser() {
		final int userId = 2;
		mockMvc.perform(delete(REST_URL + "admin/users/" + userId))
				.andExpect(status().isNoContent());
	}
}