package ru.gromov.resvote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
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
	private final RestaurantRepository restaurantRepository;

	@Autowired
	private final ProfileService profileService;


	@Cacheable("restaurant")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@Transactional(readOnly = true)
	@Override
	public List<Restaurant> getAll() {
		log.info("Get list of restaurants");
		return restaurantRepository.findAll();
	}

	@Cacheable("restaurant")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@Transactional(readOnly = true)
	@Override
	public List<Restaurant> getAllPaginated(final int page, final int size) {
		log.info("Get paginated list of restaurants");
		return restaurantRepository.findAll(PageRequest.of(page, size)).getContent();
	}

	@Cacheable("restaurant_with_dishes")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@Transactional(readOnly = true)
	@Override
	public List<Restaurant> getAllRestaurantWithDishesByDate(final LocalDate date) {
		return restaurantRepository.getAllRestaurantWithDishesByDate(date);
	}

	@CacheEvict(value = {"restaurant","restaurant_with_dishes" }, allEntries = true)
	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public Restaurant addRestaurant(final Restaurant restaurant) {
		return restaurantRepository.save(restaurant);
	}


	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@Transactional(readOnly = true)
	@Override
	public Restaurant getById(final long id) {
		log.info("Get restaurant with id {}", id);

		return restaurantRepository.findById(id)
				.orElseThrow(() -> new RestaurantNotFoundException(
						String.format("Restaurant with id %s not found", id)));
	}

	@CacheEvict(value = {"restaurant","restaurant_with_dishes" }, allEntries = true)
	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public void update(final Restaurant restaurant) {
		log.info("Update restaurant entity: {}", restaurant);
		Restaurant rest = getById(restaurant.getId());
		rest.setName(restaurant.getName());
		restaurantRepository.save(rest);
	}

	@CacheEvict(value = {"restaurant","restaurant_with_dishes" }, allEntries = true)
	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public void delete(final long id) {
		log.info("Delete restaurant entity with id: {}", id);
		restaurantRepository.deleteById(id);
	}

	@Cacheable("restaurant")
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@Transactional
	@Override
	public List<Restaurant> getAllRestaurantWithDishesByDatePaginated(LocalDate date, Integer page, Integer size) {
		return restaurantRepository.getAllRestaurantWithDishesByDatePaginated(date, PageRequest.of(page, size)).getContent();
	}
}
