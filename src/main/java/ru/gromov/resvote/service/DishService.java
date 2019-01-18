package ru.gromov.resvote.service;

import ru.gromov.resvote.model.Dish;

import java.time.LocalDate;
import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public interface DishService {

	Dish getById(long id);

	List<Dish> getByRestaurantId(long id, LocalDate date);

	Dish update(Dish dish);

	void delete(long id);

	List<Dish> createAll(List<Dish> dishes);
}
