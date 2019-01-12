package ru.gromov.resvote.model;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true, exclude = "restaurant")
@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity {

	@NotNull
	//@DateTimeFormat(pattern = DateTimeUtil.DATE_PATTERN)
	@Column(name = "date", nullable = false)
	private LocalDate date;

	@NotNull
	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@NotNull
	@JsonBackReference(value = "restaurant")
	@JoinColumn(name = "rest_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Restaurant restaurant;

}
