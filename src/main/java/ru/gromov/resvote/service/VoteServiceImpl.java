package ru.gromov.resvote.service;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.model.Vote;
import ru.gromov.resvote.repository.UserRepository;
import ru.gromov.resvote.repository.VoteRepository;
import ru.gromov.resvote.security.SecurityService;
import ru.gromov.resvote.to.RestaurantWithVoteTo;
import ru.gromov.resvote.util.exception.DeadLineException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Secured({"ROLE_ADMIN", "ROLE_USER"})
@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

	private final LocalTime DEADLINE = LocalTime.of(11, 0, 0);

	@Autowired
	private final VoteRepository voteRepository;

	@Autowired
	private final SecurityService securityService;

	@Transactional(readOnly = true)
	@Override
	public List<Vote> getRestaurantVote(final long id, final LocalDate date) {
		return voteRepository.findAllByRestaurantIdAndDateOrderByUser(id, date);
	}

	@Transactional
	@Override
	public void deleteCurrentVoteOfUser(final long id) {
		voteRepository.deleteByUser_IdAndDate(id, LocalDate.now());
	}


	@Transactional
	@Override
	public void makeVote(final long id) {
		final User user = securityService.getLoggedUser();
		Vote vote;
		if (LocalTime.now().isAfter(DEADLINE))
			throw new DeadLineException("You can't vote after 11.00!");
		Optional<Vote> optionalVote = voteRepository.getByUser_IdAndDate(user.getId(), LocalDate.now());
		if (optionalVote.isPresent()) {
			Vote oldVote = optionalVote.get();
			oldVote.setRestaurantId(id);
			vote = oldVote;
		} else {
			vote = new Vote(LocalDate.now(), id, user);
		}
		voteRepository.save(vote);
	}

	@Transactional(readOnly = true)
	@Override
	public List<RestaurantWithVoteTo> getVotedRestaurants(final LocalDate date) {
		return voteRepository.getVotedRestaurants(date);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<RestaurantWithVoteTo> getVotedRestaurantsPaginated(final LocalDate date, int page, int size) {
		return voteRepository.getVotedRestaurantsPaginated(date, PageRequest.of(page, size));
	}


}
