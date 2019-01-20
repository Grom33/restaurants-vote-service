package ru.gromov.resvote.service;

import ru.gromov.resvote.to.RestaurantWithVoteTo;
import ru.gromov.resvote.to.VoterTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

public interface VoteService {

	void deleteCurrentVoteOfUser(long userId);

	void makeVote(long restaurantId, LocalTime time, long userId);

	List<RestaurantWithVoteTo> getVotedRestaurants(LocalDate date);

	List<RestaurantWithVoteTo> getVotedRestaurantsPaginated(LocalDate date, int page, int size);

	List<VoterTo> findAllVotersByRestaurantAndDate(long id, LocalDate date);
}

