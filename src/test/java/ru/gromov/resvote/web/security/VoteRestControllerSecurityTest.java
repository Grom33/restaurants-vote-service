package ru.gromov.resvote.web.security;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import ru.gromov.resvote.service.VoteService;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.gromov.resvote.TestUtil.setDeadlineTime;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class VoteRestControllerSecurityTest extends AbstractSecurityControllerTest {

	@Autowired
	private VoteService voteService;


	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void deleteVoteByAnonymous() {
		expectedNestedException();
		mockMvc.perform(delete(REST_URL + "restaurants/vote"))
				.andExpect(status().isNoContent());
	}

	@WithAnonymousUser
	@SneakyThrows
	@Test
	public void makeVoteByAnonymous() {
		expectedNestedException();
		final int restaurantId = 1;
		setDeadlineTime(voteService, LocalTime.now().plusHours(1).toString());
		mockMvc.perform(post(REST_URL + "restaurants/" + restaurantId + "/vote"))
				.andExpect(status().isOk());
	}


}
