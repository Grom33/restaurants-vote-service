package ru.gromov.resvote.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.gromov.resvote.security.AuthorizedUser;
import ru.gromov.resvote.service.ProfileService;
import ru.gromov.resvote.to.UserTo;
import ru.gromov.resvote.util.UserUtil;

import javax.validation.Valid;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Slf4j
@RequestMapping("${settings.api_url.profile}")
@RestController
@RequiredArgsConstructor
public class UserRestController {

	@Autowired
	private final ProfileService userService;


	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public UserTo getLoggedUser(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
		log.debug("GET request: get logged user");
		return authorizedUser.getUserTo();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserTo updateLoggedUser(@Valid @RequestBody final UserTo user,
	                               @AuthenticationPrincipal AuthorizedUser authorizedUser) {
		log.info("PUT request: update logged user");
		return UserUtil.getTo(
				userService.updateLoggedUser(
						UserUtil.getUserFromTo(user), authorizedUser.getUserTo()));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserTo userRegistration(@Valid @RequestBody final UserTo user) {
		log.info("POST request: new user registration");
		return UserUtil.getTo(
				userService.userRegistration(
						UserUtil.getUserFromTo(user)));
	}
}
