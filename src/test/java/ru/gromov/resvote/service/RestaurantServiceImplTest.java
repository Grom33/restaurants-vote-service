package ru.gromov.resvote.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import ru.gromov.resvote.AbstractTest;
import ru.gromov.resvote.model.AbstractNamedEntity;
import ru.gromov.resvote.model.Restaurant;
import ru.gromov.resvote.util.exception.RestaurantNotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class RestaurantServiceImplTest extends AbstractTest {

	private static final String ALL_RESTAURANTS = "json/restaurants_all.json";
	private static final String ALL_RESTAURANTS_WITH_DISHES_TODAY = "json/restaurants_all.json";
	private static final String RESTAURANT_ID_1 = "json/restaurant_id_1.json";
	private static final String RESTAURANT_WITHOUT_ID_1 = "json/all_restaurant_without_id_1.json";


	@Autowired
	private RestaurantService restaurantService;


	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getAll() {
		final List<Restaurant> restaurants = objectMapper.readValue(
				util.getTestFile(ALL_RESTAURANTS), new TypeReference<List<Restaurant>>() {
				});
		assertThat(restaurantService.getAll()).isEqualTo(restaurants);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getAllPaginated() {
		final int page = 0;
		final int size = 3;
		final int countOfList = 3;
		assertEquals(restaurantService.getAllPaginated(page, size).size(), countOfList);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getAllRestaurantWithDishesByDate() {
		final List<Restaurant> restaurants = objectMapper.readValue(
				util.getTestFile(ALL_RESTAURANTS_WITH_DISHES_TODAY), new TypeReference<List<Restaurant>>() {
				});
		restaurants.sort(Comparator.comparing(AbstractNamedEntity::getName));

		assertThat(restaurantService.getAllRestaurantWithDishesByDate(LocalDate.now()))
				.isEqualTo(restaurants);
	}

	@WithMockUser(roles = {"ADMIN"})
	@Test
	public void addRestaurant() {
		final long newId = 100000;
		Restaurant newRestaurant = new Restaurant();
		newRestaurant.setName("Test");
		Restaurant restaurant = restaurantService.addRestaurant(newRestaurant);
		assertEquals((long) restaurant.getId(), newId);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getById() {
		final Restaurant restaurantId1 = objectMapper.readValue(util.getTestFile(RESTAURANT_ID_1), Restaurant.class);
		assertEquals(restaurantService.getById(restaurantId1.getId()), restaurantId1);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test(expected = RestaurantNotFoundException.class)
	public void getByWrongId() {
		final int wrongId = 999;
		restaurantService.getById(wrongId);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void update() {
		final Restaurant restaurantId1 = objectMapper.readValue(util.getTestFile(RESTAURANT_ID_1), Restaurant.class);
		restaurantId1.setName("TEST UPDATED");
		restaurantService.update(restaurantId1);
		assertEquals(restaurantService.getById(restaurantId1.getId()), restaurantId1);

	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void delete() {
		final long deletedId = 1L;
		restaurantService.delete(deletedId);
		final List<Restaurant> restaurants = objectMapper.readValue(
				util.getTestFile(RESTAURANT_WITHOUT_ID_1), new TypeReference<List<Restaurant>>() {
				});
		assertThat(restaurantService.getAll()).isEqualTo(restaurants);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getAllRestaurantWithDishesByDatePaginated() {
		final int page = 0;
		final int size = 3;
		final int countOfList = 3;
		assertEquals(
				restaurantService.getAllRestaurantWithDishesByDatePaginated(LocalDate.now(), page, size).size(),
				countOfList);
	}
}