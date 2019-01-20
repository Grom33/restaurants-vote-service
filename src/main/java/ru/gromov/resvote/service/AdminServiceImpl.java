package ru.gromov.resvote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.repository.UserRepository;
import ru.gromov.resvote.util.exception.AlreadyExistException;
import ru.gromov.resvote.util.exception.NotFoundException;

import java.util.*;

import static ru.gromov.resvote.util.ValidationUtil.checkNotFoundWithId;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Slf4j
@CacheConfig(cacheNames = {"user"})
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Secured("ROLE_ADMIN")
	@Override
	public List<User> getAll() {
		log.debug("Get list of users");
		return userRepository.findAll();
	}

	@CacheEvict(value = "user", allEntries = true)
	@Secured("ROLE_ADMIN")
	@Override
	public User create(final User user) {
		log.debug("Create user: {}", user);
		Assert.notNull(user, "user must not be null");
		checkAlreadyExist(user);
		if (user.getPassword() != null) user.setPassword(
				bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Secured("ROLE_ADMIN")
	@Override
	public User getById( final long id) {
		log.debug("Get user by id: {}", id);
		return userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(
						String.format("User with id %s not found", id)));
	}

	@CacheEvict(value = "user", allEntries = true)
	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public User update(final User user) {
		log.debug("Update user: {}", user);
		Assert.notNull(user, "user must not be null");
		User oldUser = getById(user.getId());
		oldUser.setName(user.getName());
		oldUser.setPassword(user.getPassword());
		return userRepository.save(oldUser);
	}

	@CacheEvict(value = "user", allEntries = true)
	@Secured("ROLE_ADMIN")
	@Override
	public void delete( final long id) {
		log.debug("Delete user by id: {}", id);
		checkNotFoundWithId(userRepository.delete(id) != 0, id);
	}

	private void checkAlreadyExist(final User user) {
		if (userRepository.findByEmail(user.getEmail()).isPresent())
			throw new AlreadyExistException(
					String.format("User with email: %s already exist!", user.getEmail()));
	}

}
