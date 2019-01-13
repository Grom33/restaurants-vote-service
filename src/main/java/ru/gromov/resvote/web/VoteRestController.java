package ru.gromov.resvote.web;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gromov.resvote.service.VoteService;
import ru.gromov.resvote.to.RestaurantWithVoteTo;
import ru.gromov.resvote.to.VoterTo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping(value = "api/v1/restaurants")
@RestController
@RequiredArgsConstructor
public class VoteRestController {

	@Autowired
	VoteService voteService;

	@GetMapping(value = "/{id}/vote")
	private List<VoterTo> getListVoteOfRestaurant(@PathVariable final String id,
	                                              @RequestParam(value = "date", required = false)
	                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		if (date == null) date = LocalDate.now();
		return voteService.getRestaurantVote(Long.valueOf(id), date).stream()
				.map(v -> new VoterTo(v.getUser().getId(), v.getUser().getName()))
				.collect(Collectors.toList());
	}

	@DeleteMapping(value = "/vote")
	private ResponseEntity<?> delete() {
		voteService.deleteCurrentVoteOfUser(1L);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping(value = "/{id}/vote")
	private ResponseEntity<?> makeVote(@PathVariable final String id) {
		voteService.makeVote(Long.valueOf(id));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/vote")
	private List<RestaurantWithVoteTo> getListVotedRestaurants(@RequestParam(value = "date", required = false)
	                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		if (date == null) date = LocalDate.now();
		return voteService.getVotedRestaurants(date);
	}

}
