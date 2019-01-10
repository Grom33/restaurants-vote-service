package ru.gromov.resvote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gromov.resvote.model.Restaurant;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Repository
public interface ResaurantRepository extends JpaRepository<Restaurant, Long> {

}
