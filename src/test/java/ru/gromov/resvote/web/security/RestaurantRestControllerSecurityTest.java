package ru.gromov.resvote.web.security;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class RestaurantRestControllerSecurityTest extends AbstractSecurityControllerTest {

	private static final String NEW_RESTAURANT = "json/new_restaurant.json";
	private static final String EDITED_RESTAURANT_ID_1 = "json/edited_restaurant_id_1.json";

	@WithMockUser
	@SneakyThrows
	@Test
	public void addRestaurantByUser() {
		addRestaurant();
	}

	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void addRestaurantByAnonymous() {
		addRestaurant();
	}

	private void addRestaurant() throws Exception {
		expectedNestedException();
		String json = util.getJsonString(util.getTestFile(NEW_RESTAURANT).toPath());
		mockMvc.perform(post(REST_URL + "restaurants")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}

	@WithMockUser
	@SneakyThrows
	@Test
	public void updateByUser() {
		updateRestaurant();
	}

	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void updateByAnonymous() {
		updateRestaurant();
	}

	private void updateRestaurant() throws Exception {
		expectedNestedException();
		final int restaurantId = 1;
		String json = util.getJsonString(util.getTestFile(EDITED_RESTAURANT_ID_1).toPath());
		mockMvc.perform(put(REST_URL + "restaurants/" + restaurantId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}

	@WithMockUser
	@SneakyThrows
	@Test
	public void deleteRestaurantByUser() {
		deleteRestaurant();
	}

	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void deleteRestaurantByAnonymous() {
		deleteRestaurant();
	}

	private void deleteRestaurant() throws Exception {
		expectedNestedException();
		final int restaurantId = 1;
		mockMvc.perform(delete(REST_URL + "restaurants/" + restaurantId))
				.andExpect(status().isNoContent());
	}
}
