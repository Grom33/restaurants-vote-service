package ru.gromov.resvote.web;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class DishRestControllerTest extends AbstractRestControllerTest {

	private static final String DISH_ID_1 = "json/dish_id_1.json";
	private static final String DISH_OF_RESTAURANT_ID_1 = "json/dishes_of_Rest_id_1.json";
	private static final String EDITED_DISH = "json/edited_dish.json";
	private static final String DISH_OF_RESTAURANT_ID_1_WITHOUT_DELETED_DISH = "json/dishes_of_Rest_id_1_without_deleted_dish.json";
	private static final String NEW_DISHES = "json/new_dishes.json";

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getByRestaurant() {
		final int restaurantID = 1;
		String json = util.getJsonString(util.getTestFile(DISH_OF_RESTAURANT_ID_1).toPath());
		mockMvc.perform(get(REST_URL + "restaurants/" + restaurantID + "/dishes"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(json));
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void addDishes() {
		final int restaurantID = 1;
		String json = util.getJsonString(util.getTestFile(NEW_DISHES).toPath());
		ResultActions action = mockMvc.perform(post(REST_URL + "restaurants/" + restaurantID + "/dishes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getDish() {
		final int dishId = 1;
		String json = util.getJsonString(util.getTestFile(DISH_ID_1).toPath());
		mockMvc.perform(get(REST_URL + "restaurants/dishes/" + dishId))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(json));
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void update() {
		final int dishId = 1;
		String json = util.getJsonString(util.getTestFile(EDITED_DISH).toPath());
		mockMvc.perform(put(REST_URL + "restaurants/dishes/" + dishId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void deleteDish() {
		final int dishId = 1;
		mockMvc.perform(delete(REST_URL + "restaurants/dishes/" + dishId))
				.andExpect(status().isNoContent());
	}
}