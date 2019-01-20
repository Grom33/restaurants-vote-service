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
import org.springframework.web.bind.annotation.*;
import ru.gromov.resvote.model.Dish;
import ru.gromov.resvote.model.Restaurant;
import ru.gromov.resvote.repository.DishRepository;
import ru.gromov.resvote.service.RestaurantService;
import ru.gromov.resvote.to.DishTo;
import ru.gromov.resvote.util.exception.NotFoundException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ru.gromov.resvote.util.ValidationUtil.*;

@Slf4j
@RequestMapping("${settings.api_url.restaurants}")
@RestController
@RequiredArgsConstructor
public class DishRestController {
	@Autowired
	private final DishRepository dishRepository;

	@Autowired
	private final RestaurantService restaurantService;

	@GetMapping(value = "/{restaurantId}/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Dish> getByRestaurant(@PathVariable final String restaurantId,
	                                  @RequestParam(value = "date", required = false)
	                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate date) {
		log.debug("GET request: get dishes list by restaurant id: {}, date: {}", restaurantId, date);

		LocalDate useDate = date;
		final long restId = Long.valueOf(restaurantId);
		if (date == null) useDate = LocalDate.now();
		return dishRepository.getByRestaurantId(restId, useDate);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/{restaurantId}/dishes", produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Dish> create(@PathVariable final String restaurantId,
	                         @Valid @RequestBody final List<DishTo> dishes) {
		log.debug("POST request: add dishes to restaurant, id: {}, dishes count: {}", restaurantId, dishes.size());
		final long restId = Long.valueOf(restaurantId);
		Restaurant restaurant = restaurantService.getById(restId);

		List<Dish> dishList = dishes.stream()
				.map(dish -> new Dish(
						dish.getName(), dish.getPrice(), restaurant, LocalDate.now()
				)).collect(Collectors.toList());
		return dishRepository.saveAll(dishList);
	}

	@GetMapping(value = "/dishes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Dish get(@PathVariable final String id) {
		log.debug("GET request: get dish by id {}", id);
		final long dishId = Long.valueOf(id);
		return dishRepository.findById(dishId).orElseThrow(() -> new NotFoundException(
				String.format("Dish with id %s not found!", id)));
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = "/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Dish update(@Valid @RequestBody final Dish dish,
	                   @PathVariable final String id) {
		log.debug("PUT request: update dish by id {}, dish:", id, dish);
		final long dishId = Long.valueOf(id);
		assureIdConsistent(dish, dishId);
		Dish oldDish = dishRepository.findById(dishId).orElseThrow(() -> new NotFoundException(
				String.format("Dish with id %s not found!", dishId)));
		oldDish.setName(dish.getName());
		oldDish.setDate(dish.getDate());
		oldDish.setPrice(dish.getPrice());
		return dishRepository.save(oldDish);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/dishes/{id}")
	public void delete(@PathVariable final String id) {
		log.debug("DELETE request: delete dish by id: {}", id);
		final long dishId = Long.valueOf(id);
		checkNotFoundWithId(dishRepository.delete(dishId) != 0, dishId);
	}

}
