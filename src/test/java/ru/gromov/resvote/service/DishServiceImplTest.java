package ru.gromov.resvote.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import ru.gromov.resvote.AbstractTest;
import ru.gromov.resvote.model.Dish;
import ru.gromov.resvote.model.Restaurant;
import ru.gromov.resvote.util.exception.DishNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */


public class DishServiceImplTest extends AbstractTest {

	private static final String DISH_ID_1 = "json/dish_id_1.json";
	private static final String DISH_OF_RESTAURANT_ID_1 = "json/dishes_of_Rest_id_1.json";
	private static final String EDITED_DISH = "json/edited_dish.json";
	private static final String DISH_OF_RESTAURANT_ID_1_WITHOUT_DELETED_DISH = "json/dishes_of_Rest_id_1_without_deleted_dish.json";
	private static final String NEW_DISHES = "json/new_dishes.json";

	@Autowired
	RestaurantService restaurantService;

	@Autowired
	DishService dishService;


	@WithMockUser(roles = {"ADMIN"})
	@Test
	@SneakyThrows
	public void getById() {
		final Dish dish = objectMapper.readValue(util.getTestFile(DISH_ID_1), Dish.class);
		assertEquals(dishService.getById(dish.getId()), dish);
	}

	@WithMockUser(roles = {"ADMIN"})
	@Test(expected = DishNotFoundException.class)
	@SneakyThrows
	public void getByIdWrongId() {
		final int wrongId = 999;
		dishService.getById(wrongId);
	}

	@SneakyThrows
	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void getByIdWithoutCredential() {
		final Dish dish = objectMapper.readValue(util.getTestFile(DISH_ID_1), Dish.class);
		assertEquals(dishService.getById(dish.getId()), dish);
	}

	@WithAnonymousUser
	@SneakyThrows
	@Test(expected = AccessDeniedException.class)
	public void getByIdByAnonymous() {
		final Dish dish = objectMapper.readValue(util.getTestFile(DISH_ID_1), Dish.class);
		assertEquals(dishService.getById(dish.getId()), dish);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getByRestaurantId() {
		final long restaurantId = 1L;
		List<Dish> dishesOfRestauranId1 = objectMapper.readValue(
				util.getTestFile(DISH_OF_RESTAURANT_ID_1), new TypeReference<List<Dish>>() {
				});
		assertThat(dishService.getByRestaurantId(restaurantId, LocalDate.now())).isEqualTo(dishesOfRestauranId1);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void update() {
		final Dish dish = objectMapper.readValue(util.getTestFile(EDITED_DISH), Dish.class);
		dishService.update(dish);
		assertEquals(dishService.getById(dish.getId()), dish);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void delete() {
		final long dishToDelete = 9L;
		final long restaurantId = 1L;
		dishService.delete(dishToDelete);
		List<Dish> dishesOfRestaurantId1 = objectMapper.readValue(
				util.getTestFile(DISH_OF_RESTAURANT_ID_1_WITHOUT_DELETED_DISH), new TypeReference<List<Dish>>() {
				});
		assertThat(dishService.getByRestaurantId(restaurantId, LocalDate.now())).isEqualTo(dishesOfRestaurantId1);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void createAll() {
		final int dishesCountAfterCreate = 5;
		final long restaurantId = 1L;
		List<Dish> dishes = objectMapper.readValue(
				util.getTestFile(NEW_DISHES), new TypeReference<List<Dish>>() {
				});

		final Restaurant restaurant = restaurantService.getById(restaurantId);

		List<Dish> dishList = dishes.stream()
				.map(dish -> new Dish(
						dish.getName(), dish.getPrice(), restaurant, LocalDate.now()
				)).collect(Collectors.toList());
		dishService.createAll(dishList);
		assertEquals(dishesCountAfterCreate, dishesCountAfterCreate);
	}
}