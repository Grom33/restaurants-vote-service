package ru.gromov.resvote.web.security;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class RestaurantRestControllerSecurityTest extends AbstractSecurityControllerTest {

	private static final String NEW_RESTAURANT = "json/new_restaurant.json";
	private static final String EDITED_RESTAURANT_ID_1 = "json/edited_restaurant_id_1.json";

	@SneakyThrows
	@Test
	public void addRestaurantByUser() {
		String json = util.getJsonString(util.getTestFile(NEW_RESTAURANT).toPath());
		mockMvc.perform(post(REST_URL + "restaurants")
				.with(user("ivan@mail.ru"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void addRestaurantByAnonymous() {
		String json = util.getJsonString(util.getTestFile(NEW_RESTAURANT).toPath());
		mockMvc.perform(post(REST_URL + "restaurants")
				.with(anonymous())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isUnauthorized());
	}

	@SneakyThrows
	@Test
	public void updateByUser() {
		final int restaurantId = 1;
		String json = util.getJsonString(util.getTestFile(EDITED_RESTAURANT_ID_1).toPath());
		mockMvc.perform(put(REST_URL + "restaurants/" + restaurantId)
				.with(user("ivan@mail.ru"))
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void updateByAnonymous() {
		final int restaurantId = 1;
		String json = util.getJsonString(util.getTestFile(EDITED_RESTAURANT_ID_1).toPath());
		mockMvc.perform(put(REST_URL + "restaurants/" + restaurantId)
				.with(anonymous())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isUnauthorized());
	}

	@SneakyThrows
	@Test
	public void deleteRestaurantByUser() {
		final int restaurantId = 1;
		mockMvc.perform(delete(REST_URL + "restaurants/" + restaurantId)
				.with(user("ivan@mail.ru")))
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void deleteRestaurantByAnonymous() {
		final int restaurantId = 1;
		mockMvc.perform(delete(REST_URL + "restaurants/" + restaurantId)
				.with(anonymous()))
				.andExpect(status().isUnauthorized());
	}

}
