package ru.gromov.resvote.web.security;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

public class AdminRestControllerSecurityTest extends AbstractSecurityControllerTest {

	private static final String NEW_USER = "json/new_user.json";
	private static final String EDITED_USER = "json/edited_user.json";

	@WithMockUser
	@SneakyThrows
	@Test
	public void getAllUsersByUser() {
		getAllUsers();
	}

	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void getAllUsersByAnonymous() {
		getAllUsers();
	}

	private void getAllUsers() throws Exception {
		expectedNestedException();
		mockMvc.perform(get(REST_URL + "admin/users"));
	}

	@WithMockUser
	@SneakyThrows
	@Test
	public void createByUser() {
		createUser();

	}

	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void createByAnonymous() {
		createUser();
	}

	private void createUser() throws Exception {
		expectedNestedException();

		String json = util.getJsonString(util.getTestFile(NEW_USER).toPath());
		mockMvc.perform(post(REST_URL + "admin/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json));
	}

	@WithMockUser
	@SneakyThrows
	@Test
	public void getUserByUser() {
		getUser();
	}

	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void getUserByAnonymous() {
		getUser();
	}

	private void getUser() throws Exception {
		expectedNestedException();
		final int userId = 2;
		mockMvc.perform(get(REST_URL + "admin/users/" + userId))
				.andExpect(status().isOk());
	}

	@WithMockUser
	@SneakyThrows
	@Test
	public void updateByUser() {
		updateUser();
	}

	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void updateByAnonymous() {
		updateUser();
	}

	private void updateUser() throws Exception {
		expectedNestedException();
		String json = util.getJsonString(util.getTestFile(EDITED_USER).toPath());
		mockMvc.perform(put(REST_URL + "admin/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}

	@WithMockUser
	@SneakyThrows
	@Test
	public void deleteUserByUser() {
		expectedNestedException();
		final int userId = 2;
		mockMvc.perform(delete(REST_URL + "admin/users/" + userId))
				.andExpect(status().isNoContent());
	}

	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void deleteUserByAnonymous() {
		expectedNestedException();
		final int userId = 2;
		mockMvc.perform(delete(REST_URL + "admin/users/" + userId))
				.andExpect(status().isNoContent());
	}

}
