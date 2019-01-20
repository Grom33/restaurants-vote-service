package ru.gromov.resvote.web.security;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gromov.resvote.web.AbstractRestControllerTest;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
@ContextConfiguration
@WebAppConfiguration
public abstract class AbstractSecurityControllerTest extends AbstractRestControllerTest {
	@PostConstruct
	private void postConstruct() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.addFilter(CHARACTER_ENCODING_FILTER)
				.apply(springSecurity())
				.build();
	}
}
