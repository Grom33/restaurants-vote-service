package ru.gromov.resvote.service;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.gromov.resvote.model.Vote;
import ru.gromov.resvote.repository.VoteRepository;
import ru.gromov.resvote.security.AuthorizedUser;
import ru.gromov.resvote.to.RestaurantWithVoteTo;
import ru.gromov.resvote.to.VoterTo;
import ru.gromov.resvote.util.exception.DeadLineException;
import ru.gromov.resvote.util.exception.NotFoundException;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

	@Value("${settings.deadline}")
	private String deadline;

	@Autowired
	private final VoteRepository voteRepository;

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@Transactional
	@Override
	public void deleteCurrentVoteOfUser() {
		log.info("Delete user vote by user");
		Optional<Vote> optionalVote = voteRepository.getByUserIdAndDate(AuthorizedUser.id(), LocalDate.now());
		if (optionalVote.isPresent()) {
			Vote oldVote = optionalVote.get();
			voteRepository.deleteById(oldVote.getId());
		} else {
			throw new NotFoundException("User did not vote today!");
		}
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@Transactional
	@Override
	public void makeVote(@Positive final long restaurantId, final LocalTime time) {
		log.info("Make user vote, restaurant id: {},current time {},  deadline is {}",
				restaurantId, time, LocalTime.parse(deadline));
		Vote vote;
		Optional<Vote> optionalVote = voteRepository.getByUserIdAndDate(AuthorizedUser.id(), LocalDate.now());
		if (optionalVote.isPresent()) {
			log.info("User has already voted, change the decision");
			if (time.isAfter(LocalTime.parse(deadline))) {
				log.info("User {} try to vote after deadline!", AuthorizedUser.get().getUserTo().getName());
				throw new DeadLineException("You can't change your vote after 11.00 AM!");
			}
			Vote oldVote = optionalVote.get();
			oldVote.setRestaurantId(restaurantId);
			vote = oldVote;
		} else {
			log.info("First user vote for today");
			vote = new Vote(LocalDate.now(), restaurantId, AuthorizedUser.id());
		}
		voteRepository.save(vote);
	}

	@Transactional(readOnly = true)
	@Override
	public List<RestaurantWithVoteTo> getVotedRestaurants(final LocalDate date) {
		log.info("Get list of vote result for date: {}", date);
		return voteRepository.getVotedRestaurants(date);
	}

	@Transactional(readOnly = true)
	@Override
	public List<RestaurantWithVoteTo> getVotedRestaurantsPaginated(
			final LocalDate date, int page, @Positive int size) {
		log.info("Get paginated list of vote result for date: {}, page: {}, size: {}", date, page, size);
		return voteRepository.getVotedRestaurantsPaginated(date, PageRequest.of(page, size)).getContent();
	}

	@Transactional(readOnly = true)
	@Override
	public List<VoterTo> findAllVotersByRestaurantAndDate(@Positive final long id, final LocalDate date) {
		log.info("Get list voters of restaurant id:{} for date: {}", id, date);
		if (id == 0) throw new IllegalArgumentException("restaurantId must be greater than zero");
		return voteRepository.findAllVotersByRestaurantAndDate(id, date);
	}


}
