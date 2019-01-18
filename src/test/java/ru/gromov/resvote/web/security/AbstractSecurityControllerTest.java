package ru.gromov.resvote.web.security;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import ru.gromov.resvote.web.AbstractRestControllerTest;

import static org.hamcrest.CoreMatchers.isA;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public abstract class AbstractSecurityControllerTest extends AbstractRestControllerTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	protected void expectedNestedException() {
		expectedException.expectCause(isA((Class) org.springframework.security.access.AccessDeniedException.class));
	}
}
