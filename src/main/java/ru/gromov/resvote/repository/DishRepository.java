package ru.gromov.resvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gromov.resvote.model.Dish;

import java.time.LocalDate;
import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

	@Transactional(readOnly = true)
	@Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.date=:date ORDER BY d.date DESC")
	List<Dish> getByRestaurantId(@Param("restaurantId") long id, @Param("date") LocalDate date);

	@Secured("ROLE_ADMIN")
	@Transactional
	@Modifying
	@Query("DELETE FROM Dish d WHERE d.id=:id")
	int delete(@Param("id") long id);
}
