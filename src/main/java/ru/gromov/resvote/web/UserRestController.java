package ru.gromov.resvote.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
	private final ProfileService profileService;


	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public UserTo getLoggedUser() {
		log.info("GET request: get logged user");
		return profileService.getLoggedUser();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserTo updateLoggedUser(@Valid @RequestBody final UserTo user) {
		log.info("PUT request: update logged user");
		return UserUtil.getTo(
				profileService.updateLoggedUser(
						UserUtil.getUserFromTo(user)));

	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserTo userRegistration(@Valid @RequestBody final UserTo user) {
		log.info("POST request: new user registration");
		return UserUtil.getTo(
				profileService.userRegistration(
						UserUtil.getUserFromTo(user)));
	}
}
