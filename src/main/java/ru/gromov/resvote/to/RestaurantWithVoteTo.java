package ru.gromov.resvote.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantWithVoteTo {
	private long restaurantId;
	private String name;
	private long vote;
}
