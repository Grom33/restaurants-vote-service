package ru.gromov.resvote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gromov.resvote.model.Restaurant;
import ru.gromov.resvote.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
	@Autowired
	RestaurantRepository restaurantRepository;

	@Override
	public List<Restaurant> getAll() {
		return restaurantRepository.findAll();
	}

	@Override
	public List<Restaurant> getAllRestaurantWithDishesByDate(LocalDate date) {
		return restaurantRepository.getAllRestaurantWithDishesByDate(date);
	}

	@Override
	public Restaurant addRestaurant(Restaurant restaurant) {
		return restaurantRepository.save(restaurant);
	}

	@Override
	public Restaurant getRestaurantWithDishesByDate(LocalDate date, long id) {
		return restaurantRepository.getRestaurantWithDishesByDate(date, id);
	}
}
