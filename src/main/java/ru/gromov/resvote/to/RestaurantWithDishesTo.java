package ru.gromov.resvote.to;

import lombok.Data;
import ru.gromov.resvote.model.Dish;

import java.util.Set;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Data
public class RestaurantWithDishesTo {
	long id;
	String name;
	Set<Dish> dishes;
}
