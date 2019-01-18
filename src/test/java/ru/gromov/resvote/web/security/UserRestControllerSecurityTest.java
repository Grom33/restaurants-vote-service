package ru.gromov.resvote.web.security;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.service.ProfileService;


import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class UserRestControllerSecurityTest extends AbstractSecurityControllerTest {
	private static final String EDITED_USER = "json/edited_user.json";

	@Autowired
	private ProfileService profileService;

	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void getLoggedUserByAnonymous() {
		expectedNestedException(AccessDeniedException.class);
		mockMvc.perform(get(REST_URL + "users"))
				.andExpect(status().isOk());
	}

	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void updateLoggedUserByAnonymous() {
		expectedNestedException(AccessDeniedException.class);
		String json = util.getJsonString(util.getTestFile(EDITED_USER).toPath());
		mockMvc.perform(put(REST_URL + "users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}

	@WithUserDetails("petr@mail.ru")
	@SneakyThrows
	@Test
	public void updateUserProfileByAnotherUser() {
		expectedNestedException(AccessDeniedException.class);
		String json = util.getJsonString(util.getTestFile(EDITED_USER).toPath());
		mockMvc.perform(put(REST_URL + "users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
		User user = objectMapper.readValue(util.getTestFile(EDITED_USER), User.class);
		assertEquals(profileService.getLoggedUser().getName(), user.getName());
	}
}
