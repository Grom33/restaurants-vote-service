package ru.gromov.resvote.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.gromov.resvote.model.Restaurant;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@Transactional
public class RestaurantServiceImplTest {

	private static final String RESTAURANT = "json/Restaurants.json";
	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@SneakyThrows
	public void getAll() {
		List<Restaurant> restaurants = objectMapper.readValue(
				getTestFile(RESTAURANT), new TypeReference<List<Restaurant>>() {
				});
		System.out.println(restaurants);
	}

	private File getTestFile(String fileNmae) {
		return new File(getClass().getClassLoader()
				.getResource(fileNmae).getFile());
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
	public void getRestaurantWithDishesByDate() {
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
}