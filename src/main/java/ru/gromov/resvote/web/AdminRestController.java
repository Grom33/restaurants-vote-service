package ru.gromov.resvote.web;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.service.ProfileService;
import ru.gromov.resvote.to.UserTo;
import ru.gromov.resvote.util.UserUtil;

import java.util.List;

@Slf4j
@RequestMapping(value = "api/v1/admin/users")
@RestController
@RequiredArgsConstructor
public class AdminRestController {
	@Autowired
	private final ProfileService userService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers() {
		return userService.getAll();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User create(@RequestBody final UserTo user) {
		return userService.create(UserUtil.getUserFromTo(user));
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUser(@PathVariable final String id) {
		return userService.getById(Long.valueOf(id));
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody final UserTo user) {
		userService.update(UserUtil.getUserFromTo(user));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(@PathVariable final String id) {
		userService.delete(Long.valueOf(id));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
