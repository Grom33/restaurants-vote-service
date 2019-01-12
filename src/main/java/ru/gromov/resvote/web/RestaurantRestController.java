package ru.gromov.resvote.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gromov.resvote.model.Restaurant;
import ru.gromov.resvote.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Slf4j
@RequestMapping(value = "api/v1/restaurants")
@RestController
@RequiredArgsConstructor
public class RestaurantRestController {

	@Autowired
	RestaurantService restaurantService;

	/**
	 * Add new restaurant entity
	 *
	 * @param restaurant New Restaurant Entity
	 * @return Added restaurant entity
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	Restaurant addRestaurant(@RequestBody Restaurant restaurant) {
		return restaurantService.addRestaurant(restaurant);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	List<Restaurant> getAllRestaurant() {
		return restaurantService.getAll();
	}


	/**
	 * @return List of Restaurants with Dishes on today
	 */
	@GetMapping(value = "/today", produces = MediaType.APPLICATION_JSON_VALUE)
	List<Restaurant> getAllRestaurantsWithDishes() {
		return restaurantService.getAllRestaurantWithDishesByDate(LocalDate.now());
	}

	/**
	 * Return
	 *
	 * @param id Id of Restaurant Entity
	 * @return Restaurant entity with dishes set
	 */
	@GetMapping(value = "/today/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	Restaurant getRestaurantWithDishes(@PathVariable String id) {
		return restaurantService.getRestaurantWithDishesByDate(LocalDate.now(), Long.parseLong(id));
	}


	@PostMapping(value = "/today")
	@PutMapping(value = "/today")
	@DeleteMapping(value = "/today")
	ResponseEntity<?> badRequestToday() {
		return methodNotAllowedResponse();
	}

	@PostMapping(value = "/today/{id}")
	@PutMapping(value = "/today/{id}")
	@DeleteMapping(value = "/today/{id}")
	ResponseEntity<?> badRequestTodayId() {
		return methodNotAllowedResponse();
	}

	@DeleteMapping
	ResponseEntity<?> badRequestRestaurant() {
		return methodNotAllowedResponse();
	}

	private ResponseEntity<?> methodNotAllowedResponse() {
		return new ResponseEntity<>("METHOD_NOT_ALLOWED", HttpStatus.METHOD_NOT_ALLOWED);
	}


}
