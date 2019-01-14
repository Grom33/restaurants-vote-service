package ru.gromov.resvote.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import ru.gromov.resvote.AbstractTest;
import ru.gromov.resvote.model.Dish;
import ru.gromov.resvote.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class RestaurantServiceImplTest extends AbstractTest {

	private static final String ALL_RESTAURANTS = "json/restaurants_all.json";

	@Autowired
	RestaurantService restaurantService;


	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getAll() {
		List<Restaurant> restaurants = objectMapper.readValue(
				util.getTestFile(ALL_RESTAURANTS), new TypeReference<List<Restaurant>>() {
				});
		assertThat(restaurantService.getAll()).isEqualTo(restaurants);
	}

	@Test
	public void getAllPaginated() {
	}

	@Test
	public void getAllRestaurantWithDishesByDate() {
	}

	@Test
	public void addRestaurant() {
	}

	@Test
	public void getById() {
	}

	@Test
	public void update() {
	}

	@Test
	public void delete() {
	}

	@Test
	public void getAllRestaurantWithDishesByDatePaginated() {
	}
}