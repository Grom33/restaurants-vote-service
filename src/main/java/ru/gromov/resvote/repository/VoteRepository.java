package ru.gromov.resvote.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gromov.resvote.model.Vote;
import ru.gromov.resvote.to.RestaurantWithVoteTo;
import ru.gromov.resvote.to.VoterTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

	@Transactional(readOnly = true)
	@Query("SELECT new ru.gromov.resvote.to.VoterTo(u.id, u.name) " +
			"FROM Vote v JOIN FETCH ru.gromov.resvote.model.User u ON u.id = v.userId " +
			"WHERE v.date =:date AND v.restaurantId =:id ORDER BY u.name")
	List<VoterTo> findAllVotersByRestaurantAndDate(@Param("id")Long restaurantId,
	                                               @Param("date") LocalDate date);

	Optional<Vote> getByUserIdAndDate(Long id, LocalDate date);

	@Query("SELECT new ru.gromov.resvote.to.RestaurantWithVoteTo(r.id, r.name, COUNT(v.id)) " +
			"FROM Vote v JOIN FETCH ru.gromov.resvote.model.Restaurant r ON r.id = v.restaurantId " +
			"WHERE v.date =:date GROUP BY r.id ORDER BY count(v.id) DESC")
	List<RestaurantWithVoteTo> getVotedRestaurants(@Param("date") LocalDate date);


	@Query("SELECT new ru.gromov.resvote.to.RestaurantWithVoteTo(r.id, r.name, COUNT(v.id)) " +
			"FROM Vote v JOIN FETCH ru.gromov.resvote.model.Restaurant r ON r.id = v.restaurantId " +
			"WHERE v.date =:date GROUP BY r.id ORDER BY count(v.id) DESC")
	Page<RestaurantWithVoteTo> getVotedRestaurantsPaginated(@Param("date") LocalDate date, Pageable pageable);
}
