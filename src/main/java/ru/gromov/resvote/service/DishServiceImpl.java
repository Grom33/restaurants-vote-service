package ru.gromov.resvote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.gromov.resvote.model.Dish;
import ru.gromov.resvote.repository.DishRepository;
import ru.gromov.resvote.util.exception.NotFoundException;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

import static ru.gromov.resvote.util.ValidationUtil.checkNotFoundWithId;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

	@Autowired
	private final DishRepository dishRepository;

	@Transactional(readOnly = true)
	@Override
	public Dish getById(@Positive final long id) {
		log.info("Get dish by Id: {}", id);
		return dishRepository.findById(id).orElseThrow(() -> new NotFoundException(
				String.format("Dish with id %s not found!", id)));
	}

	@Transactional(readOnly = true)
	@Override
	public List<Dish> getByRestaurantId(@Positive final long id, final LocalDate date) {
		log.info("Get dishes by restaurant Id: {}", id);
		return dishRepository.getByRestaurantId(id, date);
	}

	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public Dish update(final Dish dish) {
		log.info("Update dish: {}", dish);
		Assert.notNull(dish, "Dish must not be null");
		Dish oldDish = getById(dish.getId());
		oldDish.setName(dish.getName());
		oldDish.setDate(dish.getDate());
		oldDish.setPrice(dish.getPrice());
		return dishRepository.save(oldDish);
	}


	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public void delete(@Positive final long id) {
		log.info("Delete dish by id: {}", id);
		checkNotFoundWithId(dishRepository.delete(id) != 0, id);
	}

	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public List<Dish> createAll(final List<Dish> dishes) {
		log.info("Bulk create dishes, dishes count: {}", dishes.size());
		Assert.notEmpty(dishes, "Dishes must not be null");
		return dishRepository.saveAll(dishes);
	}


}
