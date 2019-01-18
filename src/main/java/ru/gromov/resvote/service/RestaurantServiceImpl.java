package ru.gromov.resvote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.gromov.resvote.model.Restaurant;
import ru.gromov.resvote.repository.RestaurantRepository;
import ru.gromov.resvote.util.exception.AlreadyExistException;
import ru.gromov.resvote.util.exception.NotFoundException;


import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;

import static ru.gromov.resvote.util.ValidationUtil.checkNotFoundWithId;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Slf4j
@CacheConfig(cacheNames = {"restaurant", "restaurant_with_dishes"})
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private final RestaurantRepository restaurantRepository;

	@Cacheable("restaurant")
	@Transactional(readOnly = true)
	@Override
	public List<Restaurant> getAll() {
		log.info("Get list of restaurants");
		return restaurantRepository.findAll();
	}

	@Cacheable("restaurant")
	@Transactional(readOnly = true)
	@Override
	public List<Restaurant> getAllPaginated(@PositiveOrZero final int page, @Positive final int size) {
		log.info("Get paginated list of restaurants. Page: {}, size: {}", page, size);
		return restaurantRepository.findAll(PageRequest.of(page, size)).getContent();
	}

	@Cacheable("restaurant_with_dishes")
	@Transactional(readOnly = true)
	@Override
	public List<Restaurant> getAllRestaurantWithDishesByDate(final LocalDate date) {
		log.info("Get list of restaurants with dishes by date: {}", date);
		return restaurantRepository.getAllRestaurantWithDishesByDate(date);
	}

	@Transactional(readOnly = true)
	@Cacheable("restaurant_with_dishes")
	@Override
	public Restaurant getRestaurantWithDishesByDate(@Positive long id, LocalDate date) {
		return restaurantRepository.getRestaurantWithDishesByDate(id, date)
				.orElseThrow(() -> new NotFoundException(
						String.format("Restaurant with id %s not found", id)));
	}

	@CacheEvict(value = {"restaurant", "restaurant_with_dishes"}, allEntries = true)
	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public Restaurant create(final Restaurant restaurant) {
		log.info("Add restaurant: {}", restaurant);
		Assert.notNull(restaurant, "Restaurant must not be null");
		if (restaurantRepository.findByName(restaurant.getName()).isPresent()) {
			throw new AlreadyExistException(
					String.format("Restaurant with name: %s already exist!", restaurant.getName())
			);
		}
		return restaurantRepository.save(restaurant);
	}

	@Transactional(readOnly = true)
	@Override
	public Restaurant getById(@Positive final long id) {
		log.info("Get restaurant with id {}", id);
		return restaurantRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(
						String.format("Restaurant with id %s not found", id)));
	}

	@CacheEvict(value = {"restaurant", "restaurant_with_dishes"}, allEntries = true)
	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public void update(final Restaurant restaurant) {
		log.info("Update restaurant entity: {}", restaurant);
		Restaurant rest = getById(restaurant.getId());
		rest.setName(restaurant.getName());
		restaurantRepository.save(rest);
	}

	@CacheEvict(value = {"restaurant", "restaurant_with_dishes"}, allEntries = true)
	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public void delete(@Positive final long id) {
		log.info("Delete restaurant entity with id: {}", id);
		checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
	}

	@Cacheable("restaurant")
	@Transactional(readOnly = true)
	@Override
	public List<Restaurant> getAllRestaurantWithDishesByDatePaginated(
			LocalDate date, @PositiveOrZero Integer page, @Positive Integer size) {
		log.info("Get paginated list of restaurants with dishes by date: {}, page: {}, size: {}", date, page, size);
		return restaurantRepository.getAllRestaurantWithDishesByDatePaginated(date, PageRequest.of(page, size)).getContent();
	}
}
