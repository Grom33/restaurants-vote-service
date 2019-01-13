package ru.gromov.resvote.service;

import ru.gromov.resvote.model.Vote;
import ru.gromov.resvote.to.RestaurantWithVoteTo;

import java.time.LocalDate;
import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public interface VoteService {

	List<Vote> getRestaurantVote(long id, LocalDate date);

	void deleteCurrentVoteOfUser(long id);

	void makeVote(long id);

	List<RestaurantWithVoteTo> getVotedRestaurants(LocalDate date);
}

