package ru.gromov.resvote.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.gromov.resvote.AbstractTest;

import javax.annotation.PostConstruct;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */


public abstract class AbstractRestControllerTest extends AbstractTest {

	protected static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

	protected final String REST_URL = "/api/v1/";

	@Autowired
	protected WebApplicationContext webApplicationContext;

	protected MockMvc mockMvc;

	static {
		CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
		CHARACTER_ENCODING_FILTER.setForceEncoding(true);
	}

	@PostConstruct
	private void postConstruct() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.addFilter(CHARACTER_ENCODING_FILTER)
				//.apply(springSecurity())
				.build();
	}
}
