package ru.gromov.resvote.web;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gromov.resvote.model.Dish;
import ru.gromov.resvote.service.DishService;

import java.time.LocalDate;
import java.util.List;

import static ru.gromov.resvote.util.ValidationUtil.*;

@Slf4j
@RequestMapping(value = "api/v1/restaurants")
@RestController
@RequiredArgsConstructor
public class DishRestController {
	@Autowired
	DishService dishService;

	@GetMapping(value = "/{restaurantId}/dishes")
	private List<Dish> getByRestaurant(@PathVariable final String restaurantId,
	                                   @RequestParam(value = "date", required = false)
	                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate date) {
		if (date == null) {
			return dishService.getByRestaurantId(Long.valueOf(restaurantId), LocalDate.now());
		} else {
			return dishService.getByRestaurantId(Long.valueOf(restaurantId), date);
		}
	}

	@GetMapping(value = "/dishes/{id}")
	private Dish get(@PathVariable final String id) {
		return dishService.getById(Long.valueOf(id));
	}

	@PutMapping(value = "/dishes/{id}")
	private ResponseEntity<?> update(@RequestBody final Dish dish,
	                                 @PathVariable final String id) {
		assureIdConsistent(dish, Long.valueOf(id));
		dishService.update(dish);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/dishes/{id}")
	private ResponseEntity<?> delete(@PathVariable final String id) {
		dishService.delete(Long.valueOf(id));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
