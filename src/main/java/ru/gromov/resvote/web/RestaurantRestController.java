package ru.gromov.resvote.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gromov.resvote.model.Restaurant;
import ru.gromov.resvote.service.RestaurantService;
import ru.gromov.resvote.to.RestaurantTo;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static ru.gromov.resvote.util.RestaurantUtil.*;
import static ru.gromov.resvote.util.ValidationUtil.*;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Slf4j
@RequestMapping("${settings.api_url.restaurants}")
@RestController
@RequiredArgsConstructor
public class RestaurantRestController {

	@Autowired
	private final RestaurantService restaurantService;


	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RestaurantTo> getAll(@RequestParam(value = "page", required = false) final Integer page,
	                                 @RequestParam(value = "size", required = false) final Integer size) {
		log.info("GET request: Get list of restaurants");
		if (page == null && size == null) {
			return createListToFromListEntity(restaurantService.getAll());
		} else {
			return createListToFromListEntity(restaurantService.getAllPaginated(page, size));
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public RestaurantTo create(@Valid @RequestBody final RestaurantTo restaurant) {
		log.info("POST request: Add restaurant: {}", restaurant);
		return createToFromEntity(restaurantService.create(createNewFromTo(restaurant)));
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RestaurantTo get(@PathVariable final String id) {
		log.info("GET request: get restaurant by id: {}", id);
		return createToFromEntity(restaurantService.getById(Long.valueOf(id)));
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public RestaurantTo update(@PathVariable final String id,
	                           @Valid @RequestBody final RestaurantTo restaurantTo) {
		log.info("PUT request: update restaurant by id: {}, restaurant: {}", id, restaurantTo);
		Restaurant restaurant = createNewFromTo(restaurantTo);
		assureIdConsistent(restaurant, Long.valueOf(id));
		return createToFromEntity(restaurantService.update(restaurant));
	}

	@GetMapping(value = "/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Restaurant> getAllWithDishesByDate(
			@RequestParam(value = "date", required = false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			@RequestParam(value = "page", required = false) final Integer page,
			@RequestParam(value = "size", required = false) final Integer size) {
		log.info("GET request: get list of restaurants with dishes by date: {}", date);

		LocalDate useDate = date;
		if (useDate == null) useDate = LocalDate.now();
		if (page == null && size == null) {
			return restaurantService.getAllRestaurantWithDishesByDate(useDate);
		} else {
			return restaurantService.getAllRestaurantWithDishesByDatePaginated(useDate, page, size);
		}
	}

	@GetMapping(value = "/{id}/detail", produces = MediaType.APPLICATION_JSON_VALUE)
	public Restaurant getWithDishesByDate(@PathVariable final String id,
	                                      @RequestParam(value = "date", required = false)
	                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		log.info("GET request: get restaurant with dishes by date: {}", date);
		LocalDate useDate = date;
		if (useDate == null) useDate = LocalDate.now();
		return restaurantService.getRestaurantWithDishesByDate(Long.valueOf(id), useDate);
	}


	@DeleteMapping(value = "/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable final String id) {
		log.info("DELETE request: delete restaurant by id: {}", id);
		restaurantService.delete(Long.valueOf(id));
	}
}
