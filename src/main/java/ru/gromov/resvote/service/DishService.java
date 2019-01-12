package ru.gromov.resvote.service;

import ru.gromov.resvote.model.Dish;

import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public interface DishService {

	Dish create(Dish dish);

	List<Dish> getAll();
}
