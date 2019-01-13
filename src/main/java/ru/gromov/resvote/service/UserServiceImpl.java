package ru.gromov.resvote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.repository.UserRepository;
import ru.gromov.resvote.util.exception.UserNotFoundException;

import java.util.List;

import static ru.gromov.resvote.util.UserUtil.*;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	/*@Autowired
	private PasswordEncoder passwordEncoder;*/

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(
						String.format("User with name %s, not found!", email)
				));
		return getUserDetailsFromUser(user);
	}

	@Transactional(readOnly = true)
	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Transactional
	@Override
	public User create(User user) {
		Assert.notNull(user, "user must not be null");
		return userRepository.save(user);
	}

	@Transactional(readOnly = true)
	@Override
	public User getById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(
						String.format("User with id %s not found", id)));
	}

	@Transactional
	@Override
	public void update(User user) {
		Assert.notNull(user, "user must not be null");
		User oldUser = getById(user.getId());
		oldUser.setName(user.getName());
		oldUser.setEmail(user.getEmail());
		oldUser.setPassword(user.getPassword());
		userRepository.save(oldUser);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
}
