package ru.gromov.resvote.web;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.gromov.resvote.repository.VoteRepository;
import ru.gromov.resvote.service.VoteService;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.gromov.resvote.TestUtil.setDeadlineTime;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class VoteRestControllerTest extends AbstractRestControllerTest {

	private static final String VOTE_OF_RESTAURANT_ID_1 = "json/vote_of_rest_id_1.json";
	private static final String VOTED_RESTAURANTS_TODAY = "json/voted_restaurants_today.json";


	@Autowired
	private VoteService voteService;

	@Autowired
	private VoteRepository voteRepository;

	@SneakyThrows
	@Test
	public void getListVoteOfRestaurant() {
		final int restaurantId = 1;
		String json = util.getJsonString(util.getTestFile(VOTE_OF_RESTAURANT_ID_1).toPath());
		mockMvc.perform(get(REST_URL + "restaurants/" + restaurantId + "/vote"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(json));
	}

	@WithUserDetails("petr@mail.ru")
	@SneakyThrows
	@Test
	public void deleteVote() {
		final long restaurantId = 3L;
		final int expectedCount = 1;
		mockMvc.perform(delete(REST_URL + "restaurants/vote"))
				.andExpect(status().isNoContent());
		assertEquals(expectedCount,
				voteRepository.findAllVotersByRestaurantAndDate(restaurantId, LocalDate.now()).size());

	}

	@WithUserDetails(value = "ivan@mail.ru")
	@SneakyThrows
	@Test
	public void makeVote() {
		final int restaurantId = 1;
		setDeadlineTime(voteService, LocalTime.now().plusHours(1).toString());
		mockMvc.perform(post(REST_URL + "restaurants/" + restaurantId + "/vote"))
				.andExpect(status().isOk());
	}

	@WithUserDetails("petr@mail.ru")
	@SneakyThrows
	@Test
	public void changeVoteAfterDeadline() {
		final int restaurantId = 1;
		setDeadlineTime(voteService, LocalTime.now().minusHours(1).toString());
		mockMvc.perform(post(REST_URL + "restaurants/" + restaurantId + "/vote"))
				.andExpect(status().isForbidden());
	}

	@SneakyThrows
	@Test
	public void getListVotedRestaurants() {
		String json = util.getJsonString(util.getTestFile(VOTED_RESTAURANTS_TODAY).toPath());
		mockMvc.perform(get(REST_URL + "restaurants/vote")
				.param("date", LocalDate.now().toString()))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(json));
	}
}