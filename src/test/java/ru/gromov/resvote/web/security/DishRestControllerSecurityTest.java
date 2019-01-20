package ru.gromov.resvote.web.security;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class DishRestControllerSecurityTest extends AbstractSecurityControllerTest {

	private static final String EDITED_DISH = "json/edited_dish.json";
	private static final String NEW_DISHES = "json/new_dishes.json";


	@SneakyThrows
	@Test
	public void addDishesByUser() {
		final int restaurantID = 1;
		String json = util.getJsonString(util.getTestFile(NEW_DISHES).toPath());
		mockMvc.perform(post(REST_URL + "restaurants/" + restaurantID + "/dishes")
				.with(user("ivan@mail.ru"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void addDishesByAnonymous() {
		final int restaurantID = 1;
		String json = util.getJsonString(util.getTestFile(NEW_DISHES).toPath());
		mockMvc.perform(post(REST_URL + "restaurants/" + restaurantID + "/dishes")
				.with(anonymous())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isUnauthorized());
	}

	@SneakyThrows
	@Test
	public void updateByUser() {
		final int dishId = 1;
		String json = util.getJsonString(util.getTestFile(EDITED_DISH).toPath());
		mockMvc.perform(put(REST_URL + "restaurants/dishes/" + dishId)
				.with(user("ivan@mail.ru"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void updateByAnonymous() {
		final int dishId = 1;
		String json = util.getJsonString(util.getTestFile(EDITED_DISH).toPath());
		mockMvc.perform(put(REST_URL + "restaurants/dishes/" + dishId)
				.with(anonymous())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isUnauthorized());
	}

	@SneakyThrows
	@Test
	public void deleteDishByUser() {
		final int dishId = 1;
		mockMvc.perform(delete(REST_URL + "restaurants/dishes/" + dishId)
				.with(user("ivan@mail.ru")))
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void deleteDishByAnonymous() {
		final int dishId = 1;
		mockMvc.perform(delete(REST_URL + "restaurants/dishes/" + dishId)
				.with(anonymous()))
				.andExpect(status().isUnauthorized());
	}
}
