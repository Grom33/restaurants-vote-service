package ru.gromov.resvote.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gromov.resvote.model.Restaurant;
import ru.gromov.resvote.service.RestaurantService;
import ru.gromov.resvote.to.RestaurantTo;

import java.util.List;

import static ru.gromov.resvote.util.RestaurantUtil.*;
import static ru.gromov.resvote.util.ValidationUtil.*;

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

	@GetMapping(params = {"page", "size"}, produces = MediaType.APPLICATION_JSON_VALUE)
	private List<RestaurantTo> getAllRestaurants(@RequestParam("page") final int page,
	                                             @RequestParam("size") final int size) {
		final Page<Restaurant> result = restaurantService.getAllPaginated(page, size);
		return createListToFromListEntity(result.getContent());
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	private RestaurantTo addRestaurant(@RequestBody final RestaurantTo restaurant) {
		return createToFromEntity(restaurantService.addRestaurant(createNewFromTo(restaurant)));
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private RestaurantTo getRestaurant(@PathVariable String id) {
		return createToFromEntity(restaurantService.getById(Long.valueOf(id)));
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	private void updateRestaurant(@PathVariable final String id, @RequestBody final RestaurantTo restaurantTo) {
		Restaurant restaurant = createNewFromTo(restaurantTo);
		assureIdConsistent(restaurant, Long.valueOf(id));
		restaurantService.update(restaurant);
	}

	@DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	private void deleteRestaurant(@PathVariable final String id) {
		restaurantService.delete(Long.valueOf(id));
	}

	@PutMapping
	@DeleteMapping
	private ResponseEntity<?> notAllowedRequest() {
		return methodNotAllowedResponse();
	}

	@PostMapping(value = "/{id}")
	private ResponseEntity<?> notAllowedRequestWithId() {
		return methodNotAllowedResponse();
	}

	private ResponseEntity<?> methodNotAllowedResponse() {
		return new ResponseEntity<>("METHOD_NOT_ALLOWED", HttpStatus.METHOD_NOT_ALLOWED);
	}
}
