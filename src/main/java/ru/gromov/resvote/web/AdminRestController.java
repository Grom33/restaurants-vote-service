package ru.gromov.resvote.web;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.service.AdminService;
import ru.gromov.resvote.to.UserTo;
import ru.gromov.resvote.util.UserUtil;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequestMapping("${settings.api_url.admin}")
@RestController
@RequiredArgsConstructor
public class AdminRestController {
	@Autowired
	private final AdminService adminService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers() {
		log.info("GET request: get all users list");
		return adminService.getAll();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User createUser(@Valid @RequestBody final User user) {
		log.info("POST request: create user:{}", user);
		return adminService.create(user);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUser(@PathVariable final String id) {
		log.info("GET request: get user by id: {}", id);
		return adminService.getById(Long.valueOf(id));
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public User updateUser(@Valid @RequestBody final UserTo user) {
		log.info("PUT request: update user: {}", user);
		return adminService.update(UserUtil.getUserFromTo(user));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/{id}")
	public void deleteUser(@PathVariable final String id) {
		log.info("DELETE request: delete user by id: {}", id);
		adminService.delete(Long.valueOf(id));
	}
}
