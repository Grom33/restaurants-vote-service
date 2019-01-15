package ru.gromov.resvote.model;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import lombok.*;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

	@NotBlank
	@Size(min = 1, max = 100)
	@Column(name = "name", nullable = false)
	protected String name;

	public AbstractNamedEntity(Long id, @NotBlank @Size(min = 1, max = 100) @SafeHtml String name) {
		super(id);
		this.name = name;
	}
}
