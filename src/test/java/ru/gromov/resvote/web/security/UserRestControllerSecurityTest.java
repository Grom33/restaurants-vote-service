package ru.gromov.resvote.web.security;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class UserRestControllerSecurityTest extends AbstractSecurityControllerTest {
	private static final String EDITED_USER = "json/edited_user.json";

	@SneakyThrows
	@Test
	public void getLoggedUserByAnonymous() {
		mockMvc.perform(get(REST_URL + "users")
				.with(anonymous()))
				.andExpect(status().isUnauthorized());
	}

	@SneakyThrows
	@Test
	public void updateLoggedUserByAnonymous() {
		String json = util.getJsonString(util.getTestFile(EDITED_USER).toPath());
		mockMvc.perform(put(REST_URL + "users")
				.with(anonymous())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isUnauthorized());
	}

	@SneakyThrows
	@Test
	public void updateUserProfileByAnotherUser() {
		String json = util.getJsonString(util.getTestFile(EDITED_USER).toPath());
		mockMvc.perform(put(REST_URL + "users")
				.with(user("petr@mail.ru"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().is5xxServerError());
	}
}
