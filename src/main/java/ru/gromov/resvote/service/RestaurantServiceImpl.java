package ru.gromov.resvote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gromov.resvote.model.Restaurant;
import ru.gromov.resvote.repository.RestaurantRepository;
import ru.gromov.resvote.util.exception.RestaurantNotFoundException;

import java.time.LocalDate;
import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
	@Autowired
	RestaurantRepository restaurantRepository;

	@Override
	public List<Restaurant> getAll() {
		return restaurantRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Restaurant> getAllPaginated(final int page, final int size) {
		return restaurantRepository.findAll(PageRequest.of(page, size));
	}

	@Override
	public List<Restaurant> getAllRestaurantWithDishesByDate(final LocalDate date) {
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

	@Transactional(readOnly = true)
	@Override
	public Restaurant getById(long id) {
		log.info("Get restaurant with id {}", id);

		return restaurantRepository.findById(id)
				.orElseThrow(() -> new RestaurantNotFoundException(
						String.format("Restaurant with id %s not found", id)));
	}

	@Transactional
	@Override
	public void update(Restaurant restaurant) {
		log.info("Update restaurant entity: {}", restaurant);

		Restaurant rest = getById(restaurant.getId());
		rest.setName(restaurant.getName());
		restaurantRepository.save(rest);
	}

	@Override
	public void delete(Long id) {
		log.info("Delete restaurant entity with id: {}", id);
		restaurantRepository.deleteById(id);
	}


}
