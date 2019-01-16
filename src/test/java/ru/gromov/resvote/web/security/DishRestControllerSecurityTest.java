package ru.gromov.resvote.web.security;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class DishRestControllerSecurityTest extends AbstractSecurityControllerTest {

	private static final String EDITED_DISH = "json/edited_dish.json";
	private static final String NEW_DISHES = "json/new_dishes.json";


	@WithMockUser
	@SneakyThrows
	@Test
	public void addDishesByUser() {
		addDish();
	}

	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void addDishesByAnonymous() {
		addDish();
	}

	private void addDish() throws Exception {
		expectedNestedException(AccessDeniedException.class);
		final int restaurantID = 1;
		String json = util.getJsonString(util.getTestFile(NEW_DISHES).toPath());
		mockMvc.perform(post(REST_URL + "restaurants/" + restaurantID + "/dishes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}

	@WithMockUser
	@SneakyThrows
	@Test
	public void updateByUser() {
		updateDish();
	}

	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void updateByAnonymous() {
		updateDish();
	}

	private void updateDish() throws Exception {
		expectedNestedException(AccessDeniedException.class);
		final int dishId = 1;
		String json = util.getJsonString(util.getTestFile(EDITED_DISH).toPath());
		mockMvc.perform(put(REST_URL + "restaurants/dishes/" + dishId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk());
	}


	@WithMockUser
	@SneakyThrows
	@Test
	public void deleteDishByUser() {
		deleteDish();
	}

	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void deleteDishByAnonymous() {
		deleteDish();
	}

	private void deleteDish() throws Exception {
		expectedNestedException(AccessDeniedException.class);
		final int dishId = 1;
		mockMvc.perform(delete(REST_URL + "restaurants/dishes/" + dishId))
				.andExpect(status().isNoContent());
	}
}
