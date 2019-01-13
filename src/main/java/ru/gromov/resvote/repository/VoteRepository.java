package ru.gromov.resvote.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gromov.resvote.model.Vote;
import ru.gromov.resvote.to.RestaurantWithVoteTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

	List<Vote> findAllByRestaurantIdAndDateOrderByUser(Long id, LocalDate date);

	void deleteByUser_IdAndDate(Long id, LocalDate date);

	Optional<Vote> getByUser_IdAndDate(Long id, LocalDate date);

	@Query("SELECT new ru.gromov.resvote.to.RestaurantWithVoteTo(r.id, r.name, COUNT(v.id)) " +
			"FROM Vote v JOIN FETCH ru.gromov.resvote.model.Restaurant r ON r.id = v.restaurantId " +
			"WHERE v.date =:date GROUP BY r.id ORDER BY count(v.id) DESC")
	List<RestaurantWithVoteTo> getVotedRestaurants(@Param("date") LocalDate date);

	@Query("SELECT new ru.gromov.resvote.to.RestaurantWithVoteTo(r.id, r.name, COUNT(v.id)) " +
			"FROM Vote v JOIN FETCH ru.gromov.resvote.model.Restaurant r ON r.id = v.restaurantId " +
			"WHERE v.date =:date GROUP BY r.id ORDER BY count(v.id) DESC")
	Page<RestaurantWithVoteTo> getVotedRestaurantsPaginated(@Param("date") LocalDate date, Pageable pageable);
}
