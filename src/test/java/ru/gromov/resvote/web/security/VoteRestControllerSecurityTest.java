package ru.gromov.resvote.web.security;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gromov.resvote.service.VoteService;

import java.time.LocalTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.gromov.resvote.TestUtil.setDeadlineTime;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class VoteRestControllerSecurityTest extends AbstractSecurityControllerTest {

	@Autowired
	private VoteService voteService;

	@SneakyThrows
	@Test
	public void deleteVoteByAnonymous() {
		mockMvc.perform(delete(REST_URL + "restaurants/vote")
				.with(anonymous()))
				.andExpect(status().isUnauthorized());
	}

	@SneakyThrows
	@Test
	public void makeVoteByAnonymous() {
		final int restaurantId = 1;
		setDeadlineTime(voteService, LocalTime.now().plusHours(1).toString());
		mockMvc.perform(post(REST_URL + "restaurants/" + restaurantId + "/vote")
				.with(anonymous()))
				.andExpect(status().isUnauthorized());
	}
}
