package ru.gromov.resvote.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Getter
@Setter
@ToString
@Entity
@Table(name = "restaurants")
@NamedEntityGraph(name = "graph.dishes.items", attributeNodes = @NamedAttributeNode("dishes"))
public class Restaurant extends AbstractNamedEntity {

	//@JsonInclude(JsonInclude.Include.NON_EMPTY)
	//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	//@OrderBy("date DESC")
	@JsonManagedReference(value = "restaurant")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	private Set<Dish> dishes;

}
