package ru.gromov.resvote.model;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true, exclude = "restaurant")
@Entity
@Table(name = "dishes")
@AllArgsConstructor
@NoArgsConstructor
public class Dish extends AbstractNamedEntity {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@NotNull
	@Column(name = "date", nullable = false)
	private LocalDate date;

	@NotNull
	@Digits(integer = 10, fraction = 2)
	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	private BigDecimal price;


	@JsonBackReference(value = "restaurant")
	@JoinColumn(name = "rest_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Restaurant restaurant;

	public Dish(String name, BigDecimal price, Restaurant restaurant, LocalDate now) {
		super(name);
		this.date = now;
		this.price = price;
		this.restaurant = restaurant;
	}
}
