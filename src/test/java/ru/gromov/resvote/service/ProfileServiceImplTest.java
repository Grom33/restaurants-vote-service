package ru.gromov.resvote.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import ru.gromov.resvote.AbstractTest;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.util.exception.UserNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class ProfileServiceImplTest extends AbstractTest {

	private static final String LOGGED_USER_IVAN = "json/logged_user_ivan.json";
	private static final String USERS_LIST = "json/users_list.json";
	private static final String NEW_USER = "json/new_user.json";

	@Autowired
	private ProfileService profileService;

	@WithMockUser(value = "ivan@mail.ru")
	@SneakyThrows
	@Test
	public void getLoggedUser() {
		final User user = objectMapper.readValue(util.getTestFile(LOGGED_USER_IVAN), User.class);
		assertEquals(profileService.getLoggedUser(), user);
	}

	@WithMockUser(value = "ivan@mail.ru")
	@SneakyThrows
	@Test
	public void updateLoggedUser() {
		User user = objectMapper.readValue(util.getTestFile(LOGGED_USER_IVAN), User.class);
		user.setName("TEST");
		user.setPassword("123456");
		profileService.updateLoggedUser(user);
		assertEquals(profileService.getLoggedUser(), user);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getAll() {
		List<User> usersList = objectMapper.readValue(
				util.getTestFile(USERS_LIST), new TypeReference<List<User>>() {
				});
		assertThat(profileService.getAll()).isEqualTo(usersList);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void create() {
		User user = objectMapper.readValue(util.getTestFile(NEW_USER), User.class);
		assertTrue(user.isNew());
		User newUser = profileService.create(user);
		assertFalse(newUser.isNew());
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getById() {
		User user = objectMapper.readValue(util.getTestFile(LOGGED_USER_IVAN), User.class);
		assertEquals(profileService.getById(user.getId()), user);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test(expected = UserNotFoundException.class)
	public void getByWrongId() {
		final int wrongId = 999;
		profileService.getById(wrongId);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void update() {
		User user = objectMapper.readValue(util.getTestFile(LOGGED_USER_IVAN), User.class);
		user.setEmail("new@mail.ru");
		profileService.update(user);
		assertEquals(profileService.getById(user.getId()), user);
	}

	@WithMockUser(roles = {"ADMIN"})
	@Test
	public void delete() {
		final long userToDelete = 2L;
		final int expectedUserCount = 5;
		profileService.delete(userToDelete);
		assertEquals(profileService.getAll().size(), expectedUserCount);

	}
}