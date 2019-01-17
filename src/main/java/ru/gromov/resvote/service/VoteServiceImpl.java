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
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.model.Vote;
import ru.gromov.resvote.repository.VoteRepository;
import ru.gromov.resvote.security.SecurityService;
import ru.gromov.resvote.to.RestaurantWithVoteTo;
import ru.gromov.resvote.util.exception.DeadLineException;

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

	@Autowired
	private final SecurityService securityService;

	@Transactional(readOnly = true)
	@Override
	public List<Vote> getRestaurantVote(final long id, final LocalDate date) {
		log.info("Get list of vote by restaurant id {}, date {}");
		return voteRepository.findAllByRestaurantIdAndDateOrderByUser(id, date);
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@Transactional
	@Override
	public void deleteCurrentVoteOfUser() {
		log.info("Delete user vote by user");
		final User user = securityService.getLoggedUser();
		Optional<Vote> optionalVote = voteRepository.getByUser_IdAndDate(user.getId(), LocalDate.now());
		if (optionalVote.isPresent()) {
			Vote oldVote = optionalVote.get();
			voteRepository.deleteById(oldVote.getId());
		}
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@Transactional
	@Override
	public void makeVote(final long restaurantId, final LocalTime time) {
		log.info("Make user vote, restaurant id: {},current time {},  deadline is {}",
				restaurantId, time, LocalTime.parse(deadline));
		final User user = securityService.getLoggedUser();
		Vote vote;
		Optional<Vote> optionalVote = voteRepository.getByUser_IdAndDate(user.getId(), LocalDate.now());
		if (optionalVote.isPresent()) {
			log.info("User has already voted, change the decision");
			if (time.isAfter(LocalTime.parse(deadline))) {
				log.info("User {} try to vote after deadline!", user.getName());
				throw new DeadLineException("You can't change your vote after 11.00 AM!");
			}
			Vote oldVote = optionalVote.get();
			oldVote.setRestaurantId(restaurantId);
			vote = oldVote;
		} else {
			log.info("First user vote for today");
			vote = new Vote(LocalDate.now(), restaurantId, user);
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
	public List<RestaurantWithVoteTo> getVotedRestaurantsPaginated(final LocalDate date, int page, int size) {
		log.info("Get paginated list of vote result for date: {}, page: {}, size: {}", date, page, size);
		return voteRepository.getVotedRestaurantsPaginated(date, PageRequest.of(page, size)).getContent();
	}


}
