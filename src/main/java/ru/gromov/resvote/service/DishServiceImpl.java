package ru.gromov.resvote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gromov.resvote.repository.DishRepository;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

	@Autowired
	private DishRepository dishRepository;


}
