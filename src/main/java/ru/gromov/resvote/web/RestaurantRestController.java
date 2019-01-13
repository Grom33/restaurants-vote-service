package ru.gromov.resvote.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.gromov.resvote.model.Restaurant;
import ru.gromov.resvote.service.RestaurantService;
import ru.gromov.resvote.to.RestaurantTo;

import java.time.LocalDate;
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
	private final RestaurantService restaurantService;


	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RestaurantTo> getAllRestaurants(@RequestParam(value = "page", required = false) final Integer page,
	                                             @RequestParam(value = "size", required = false) final Integer size) {
		if (page == null && size == null) {
			return createListToFromListEntity(restaurantService.getAll());
		} else {
			final Page<Restaurant> result = restaurantService.getAllPaginated(page, size);
			return createListToFromListEntity(result.getContent());
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public RestaurantTo addRestaurant(@RequestBody final RestaurantTo restaurant) {
		return createToFromEntity(restaurantService.addRestaurant(createNewFromTo(restaurant)));
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RestaurantTo getRestaurant(@PathVariable final String id) {
		return createToFromEntity(restaurantService.getById(Long.valueOf(id)));
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable final String id,
	                                 @RequestBody final RestaurantTo restaurantTo) {
		Restaurant restaurant = createNewFromTo(restaurantTo);
		assureIdConsistent(restaurant, Long.valueOf(id));
		restaurantService.update(restaurant);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Restaurant> getRestaurantWithDishesByDate(
			@RequestParam(value = "date", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		if (date == null) date = LocalDate.now();
		return restaurantService.getAllRestaurantWithDishesByDate(date);
	}

	@DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(@PathVariable final String id) {
		restaurantService.delete(Long.valueOf(id));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping
	@DeleteMapping
	public ResponseEntity<?> notAllowedRequest() {
		return methodNotAllowedResponse();
	}

	@PostMapping(value = "/{id}")
	public ResponseEntity<?> notAllowedRequestWithId() {
		return methodNotAllowedResponse();
	}

	private ResponseEntity<?> methodNotAllowedResponse() {
		return new ResponseEntity<>("METHOD_NOT_ALLOWED", HttpStatus.METHOD_NOT_ALLOWED);
	}
}
