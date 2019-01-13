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

import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Slf4j
@RequestMapping(value = "api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserRestController {

	@Autowired
	private UserService userService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	private List<User> getAllUsers() {
		return userService.getAll();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	private User create(@RequestBody final UserTo user) {
		return userService.create(UserUtil.getUserFromTo(user));
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	private User getUser(@PathVariable final String id) {
		return userService.getById(Long.valueOf(id));
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<?> update(@RequestBody final UserTo user) {
		userService.update(UserUtil.getUserFromTo(user));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<?> delete(@PathVariable final String id) {
		userService.delete(Long.valueOf(id));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
