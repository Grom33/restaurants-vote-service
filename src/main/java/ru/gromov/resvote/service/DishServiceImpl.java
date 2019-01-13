package ru.gromov.resvote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gromov.resvote.model.Dish;
import ru.gromov.resvote.repository.DishRepository;
import ru.gromov.resvote.util.exception.DishNotFoundException;

import java.time.LocalDate;
import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

	@Autowired
	private final DishRepository dishRepository;

	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public Dish create(final Dish dish) {
		return dishRepository.save(dish);
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@Transactional(readOnly = true)
	@Override
	public List<Dish> getAll() {
		return dishRepository.findAll();
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@Transactional(readOnly = true)
	@Override
	public Dish getById(final long id) {
		return dishRepository.findById(id).orElseThrow(() -> new DishNotFoundException(
				String.format("Dish with id %s not found!", id)));
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@Transactional(readOnly = true)
	@Override
	public List<Dish> getByRestaurantId(final long id, final LocalDate date) {
		return dishRepository.getByRestaurantId(id, date);
	}

	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public void update(final Dish dish) {
		Dish oldDish = getById(dish.getId());
		oldDish.setName(dish.getName());
		oldDish.setDate(dish.getDate());
		oldDish.setPrice(dish.getPrice());
		dishRepository.save(oldDish);
	}

	@Secured("ROLE_ADMIN")
	@Override
	public void delete(final long id) {
		dishRepository.deleteById(id);
	}

	@Secured("ROLE_ADMIN")
	@Override
	public List<Dish> createAll(final List<Dish> dishes) {
		return dishRepository.saveAll(dishes);
	}


}
