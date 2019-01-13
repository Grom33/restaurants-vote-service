package ru.gromov.resvote.to;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class RestaurantTo implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	@NotBlank
	@Size(min = 2, max = 100)
	private String name;
}
