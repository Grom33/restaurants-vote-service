package ru.gromov.resvote.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishTo {

	@NotBlank
	@Size(min = 2, max = 100)
	private String name;

	@Digits(integer = 10, fraction = 2)
	private BigDecimal price;

}
