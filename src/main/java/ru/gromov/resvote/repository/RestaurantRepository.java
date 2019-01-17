package ru.gromov.resvote.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gromov.resvote.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	@Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.dishes d WHERE d.date =:date ORDER BY r.name")
	List<Restaurant> getAllRestaurantWithDishesByDate(@Param("date") LocalDate date);

	@Query("SELECT DISTINCT r FROM Restaurant r JOIN r.dishes d WHERE d.date =:date ORDER BY r.name")
	Page<Restaurant> getAllRestaurantWithDishesByDatePaginated(@Param("date") LocalDate date, Pageable pageable);

	Optional<Restaurant> findByName(String name);
}
