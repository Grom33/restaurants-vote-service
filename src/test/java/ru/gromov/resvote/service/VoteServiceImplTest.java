package ru.gromov.resvote.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import ru.gromov.resvote.AbstractTest;
import ru.gromov.resvote.to.RestaurantWithVoteTo;
import ru.gromov.resvote.util.exception.DeadLineException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class VoteServiceImplTest extends AbstractTest {

	private static final String VOTE_OF_RESTAURANT_ID_1 = "json/vote_of_rest_id_1.json";
	private static final String VOTED_RESTAURANTS_TODAY = "json/voted_restaurants_today.json";

	@Autowired
	VoteService voteService;


	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getRestaurantVote() {
		final long restaurantId = 1L;
		final int expectedCount = 2;
		assertEquals(voteService.getRestaurantVote(restaurantId, LocalDate.now()).size(), expectedCount);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void deleteCurrentVoteOfUser() {
		final long restaurantId = 1L;
		final int expectedCount = 1;
		final long userId = 2L;
		voteService.deleteCurrentVoteOfUser(userId);
		assertEquals(voteService.getRestaurantVote(restaurantId, LocalDate.now()).size(), expectedCount);
	}

	@WithMockUser(value = "admin@mail.ru", roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void makeVote() {
		final long restaurantId = 1L;
		final int expectedCount = 3;
		VoteServiceImpl.setDeadline(LocalTime.now().plusHours(1));
		voteService.makeVote(restaurantId, LocalTime.now());
		assertEquals(voteService.getRestaurantVote(restaurantId, LocalDate.now()).size(), expectedCount);
	}

	@WithMockUser(value = "ivan@mail.ru")
	@SneakyThrows
	@Test
	public void changeVote() {
		final long restaurantId = 1L;
		final int expectedCount = 2;
		VoteServiceImpl.setDeadline(LocalTime.now().plusHours(1));
		voteService.makeVote(restaurantId, LocalTime.now());
		assertEquals(voteService.getRestaurantVote(restaurantId, LocalDate.now()).size(), expectedCount);
	}

	@WithMockUser(value = "admin@mail.ru", roles = {"ADMIN"})
	@SneakyThrows
	@Test(expected = DeadLineException.class)
	public void makeVoteAfterDeadline() {
		final long restaurantId = 1L;
		final int expectedCount = 3;
		VoteServiceImpl.setDeadline(LocalTime.now().minusHours(1));
		voteService.makeVote(restaurantId, LocalTime.now());
		assertEquals(voteService.getRestaurantVote(restaurantId, LocalDate.now()).size(), expectedCount);
	}

	@WithMockUser(roles = {"ADMIN"})
	@SneakyThrows
	@Test
	public void getVotedRestaurants() {
		List<RestaurantWithVoteTo> votedRestaurants = objectMapper.readValue(
				util.getTestFile(VOTED_RESTAURANTS_TODAY), new TypeReference<List<RestaurantWithVoteTo>>() {
				});
		assertThat(voteService.getVotedRestaurants(LocalDate.now())).isEqualTo(votedRestaurants);
	}

	@WithMockUser
	@Test
	public void getVotedRestaurantsPaginated() {
		final int page = 0;
		final int size = 3;
		final int countOfList = 3;
		assertEquals(
				voteService.getVotedRestaurantsPaginated(LocalDate.now(), page, size).size(),
				countOfList);
	}
}