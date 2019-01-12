package ru.gromov.resvote.service;

import ru.gromov.resvote.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public interface RestaurantService {
	List<Restaurant> getAll();

	List<Restaurant> getAllRestaurantWithDishesByDate(LocalDate now);

	Restaurant addRestaurant(Restaurant restaurant);

	Restaurant getRestaurantWithDishesByDate(LocalDate date, long id);
}
