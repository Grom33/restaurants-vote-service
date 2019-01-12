package ru.gromov.resvote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
	private DishRepository dishRepository;


	@Transactional
	@Override
	public Dish create(final Dish dish) {
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Dish> getAll() {
		return dishRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Dish getById(final long id) {
		return dishRepository.findById(id).orElseThrow(() -> new DishNotFoundException(
				String.format("Dish with id %s not found!", id)));
	}

	@Transactional(readOnly = true)
	@Override
	public List<Dish> getByRestaurantId(Long id, LocalDate date) {
		return dishRepository.getByRestaurantId(id, date);
	}

	@Transactional
	@Override
	public void update(Dish dish) {
		Dish oldDish = getById(dish.getId());
		oldDish.setName(dish.getName());
		oldDish.setDate(dish.getDate());
		oldDish.setPrice(dish.getPrice());
		dishRepository.save(oldDish);
	}

	@Override
	public void delete(Long id) {
		dishRepository.deleteById(id);
	}


}
