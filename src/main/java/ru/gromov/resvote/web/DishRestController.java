package ru.gromov.resvote.web;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gromov.resvote.model.Dish;
import ru.gromov.resvote.model.Restaurant;
import ru.gromov.resvote.service.DishService;
import ru.gromov.resvote.service.RestaurantService;
import ru.gromov.resvote.to.DishTo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ru.gromov.resvote.util.ValidationUtil.*;

@Slf4j
@RequestMapping(value = "api/v1/restaurants")
@RestController
@RequiredArgsConstructor
public class DishRestController {
	@Autowired
	private final DishService dishService;

	@Autowired
	private final RestaurantService restaurantService;

	@GetMapping(value = "/{restaurantId}/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Dish> getByRestaurant(@PathVariable final String restaurantId,
	                                  @RequestParam(value = "date", required = false)
	                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate date) {
		log.info("GET request: get dishes list by restaurant id: {}, date: {}", restaurantId, date);
		LocalDate useDate = date;
		if (date == null) useDate = LocalDate.now();
		return dishService.getByRestaurantId(Long.valueOf(restaurantId), useDate);
	}

	@PostMapping(value = "/{restaurantId}/dishes", produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Dish> create(@PathVariable final String restaurantId,
	                         @RequestBody final List<DishTo> dishes) {
		log.info("POST request: add dishes to restaurant, id: {}, dishes count: {}", restaurantId, dishes.size());
		Restaurant restaurant = restaurantService.getById(Long.valueOf(restaurantId));

		List<Dish> dishList = dishes.stream()
				.map(dish -> new Dish(
						dish.getName(), dish.getPrice(), restaurant, LocalDate.now()
				)).collect(Collectors.toList());
		return dishService.createAll(dishList);
	}

	@GetMapping(value = "/dishes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Dish get(@PathVariable final String id) {
		log.info("GET request: get dish by id {}", id);
		return dishService.getById(Long.valueOf(id));
	}

	@PutMapping(value = "/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody final Dish dish,
	                                @PathVariable final String id) {
		log.info("PUT request: update dish by id {}, dish:", id, dish);
		assureIdConsistent(dish, Long.valueOf(id));
		dishService.update(dish);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/dishes/{id}")
	public ResponseEntity<?> delete(@PathVariable final String id) {
		log.info("DELETE request: delete dish by id: {}", id);
		dishService.delete(Long.valueOf(id));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
