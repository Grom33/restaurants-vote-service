package ru.gromov.resvote.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;


/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Getter
@Setter
@ToString(callSuper = true, exclude = "dishes")
@Entity
@Table(name = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends AbstractNamedEntity {

	//@JsonInclude(JsonInclude.Include.NON_EMPTY)
	//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	@OrderBy("name DESC")
	@JsonManagedReference(value = "restaurant")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	private Set<Dish> dishes;

	public Restaurant(Long id, @NotBlank @Size(min = 1, max = 100) @SafeHtml String name) {
		super(id, name);
	}
}
