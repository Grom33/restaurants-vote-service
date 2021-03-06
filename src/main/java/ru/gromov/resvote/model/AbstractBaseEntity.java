package ru.gromov.resvote.model;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@MappedSuperclass
// http://stackoverflow.com/questions/594597/hibernate-annotations-which-is-better-field-or-property-access
@Access(AccessType.FIELD)
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractBaseEntity implements HasId {

	private static final int START_SEQ = 100000;

	@Id
	@SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
	protected Long id;

	@JsonIgnore
	@Override
	public boolean isNew() {
		return getId() == null;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AbstractBaseEntity that = (AbstractBaseEntity) o;
		return getId() != null && getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		return getId() == null ? 0 : Long.hashCode(getId());
	}
}
