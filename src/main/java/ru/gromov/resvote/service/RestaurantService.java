package ru.gromov.resvote.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gromov.resvote.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

public interface RestaurantService {

	List<Restaurant> getAll();

	Page<Restaurant> getAllPaginated(int page, int size);

	List<Restaurant> getAllRestaurantWithDishesByDate(LocalDate now);

	Restaurant addRestaurant(Restaurant restaurant);

	Restaurant getRestaurantWithDishesByDate(LocalDate date, long id);

	Restaurant getById(long id);

	void update(Restaurant restaurant);

	void delete(long id);
}