package ru.gromov.resvote.model;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

public interface HasId {
	Long getId();

	void setId(Long id);

	default boolean isNew() {
		return getId() == null;
	}
}
