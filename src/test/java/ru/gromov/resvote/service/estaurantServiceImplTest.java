package ru.gromov.resvote.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.gromov.resvote.repository.RestaurantRepository;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@Transactional
public class estaurantServiceImplTest {

	@Autowired
	RestaurantService restaurantService;

	@Autowired
	RestaurantRepository restaurantRepository;


	@Test
	public void getAll() {
		System.out.println(restaurantService.getAll());
	}


	@Test
	public void getWithDishes() {
		System.out.println(restaurantRepository.getByIdwithDishes(1L));
	}


}