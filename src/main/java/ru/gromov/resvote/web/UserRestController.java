package ru.gromov.resvote.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.service.UserService;
import ru.gromov.resvote.to.UserTo;
import ru.gromov.resvote.util.UserUtil;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Slf4j
@RequestMapping(value = "api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserRestController {

	@Autowired
	private final UserService userService;


	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public User getLoggedUser() {
		return userService.getLoggedUser();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateLoggedUser(@RequestBody final UserTo user) {
		userService.updateLoggedUser(UserUtil.getUserFromTo(user));
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
