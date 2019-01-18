package ru.gromov.resvote.web;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.service.ProfileService;
import ru.gromov.resvote.util.exception.NotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.gromov.resvote.TestUtil.getContent;


/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class AdminRestControllerTest extends AbstractRestControllerTest {


	private static final String USERS_LIST = "json/users_list.json";
	private static final String LOGGED_USER_IVAN = "json/logged_user_ivan.json";
	private static final String NEW_USER = "json/new_user.json";
	private static final String EDITED_USER = "json/edited_user.json";

	@Autowired
	private ProfileService profileService;

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
		final int newUserCount = 7;
		String json = util.getJsonString(util.getTestFile(NEW_USER).toPath());
		ResultActions action = mockMvc.perform(post(REST_URL + "admin/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated());
		User user = objectMapper.readValue(getContent(action), User.class);
		assertFalse(user.isNew());
		assertEquals(newUserCount, profileService.getAll().size());
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
		User user = objectMapper.readValue(util.getTestFile(EDITED_USER), User.class);
		assertEquals(user, profileService.getById(user.getId()));
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test(expected = NotFoundException.class)
	public void deleteUser() {
		final int userId = 2;
		mockMvc.perform(delete(REST_URL + "admin/users/" + userId))
				.andExpect(status().isNoContent());
		profileService.getById(userId);
	}
}