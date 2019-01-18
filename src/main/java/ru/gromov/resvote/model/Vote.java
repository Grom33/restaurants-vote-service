package ru.gromov.resvote.model;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "vote")
@NoArgsConstructor
public class Vote extends AbstractBaseEntity {

	@Column(name = "date", nullable = false)
	@NotNull
	private LocalDate date;

	@Column(name = "restaurant_id", nullable = false)
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@JoinColumn(name = "restaurant_id", nullable = false)
	private Long restaurantId;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Column(name = "user_id", nullable = false)
	@JoinColumn(name = "user_id", nullable = false)
	private long userId;

	public Vote(@NotNull LocalDate date, Long restaurantId, long userId) {
		this.date = date;
		this.restaurantId = restaurantId;
		this.userId = userId;
	}
}
