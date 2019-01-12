package ru.gromov.resvote.util;

import ru.gromov.resvote.model.Restaurant;
import ru.gromov.resvote.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

public class RestaurantUtil {
	private RestaurantUtil() {
	}

	public static Restaurant createNewFromTo(RestaurantTo restaurant) {
		return new Restaurant(restaurant.getId(), restaurant.getName());
	}

	public static RestaurantTo createToFromEntity(Restaurant restaurant) {
		return new RestaurantTo(restaurant.getId(), restaurant.getName());
	}

	public static List<RestaurantTo> createListToFromListEntity(List<Restaurant> restaurants) {
		return restaurants.stream()
				.map(RestaurantUtil::createToFromEntity)
				.collect(Collectors.toList());
	}


}
