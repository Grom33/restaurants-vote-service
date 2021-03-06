package ru.gromov.resvote.service;

import ru.gromov.resvote.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

public interface RestaurantService {

	List<Restaurant> getAll();

	List<Restaurant> getAllPaginated(int page, int size);

	List<Restaurant> getAllRestaurantWithDishesByDate(LocalDate date);

	Restaurant getRestaurantWithDishesByDate(long id, LocalDate date);

	Restaurant create(Restaurant restaurant);

	Restaurant getById(long id);

	Restaurant update(Restaurant restaurant);

	void delete(long id);

	List<Restaurant> getAllRestaurantWithDishesByDatePaginated(LocalDate date, Integer page, Integer size);
}