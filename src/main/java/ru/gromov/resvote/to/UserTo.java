package ru.gromov.resvote.to;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTo implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	@NotBlank
	@Size(min = 2, max = 100)
	private String name;

	@Email
	@NotBlank
	@Size(max = 100)
	private String email;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Size(min = 5, max = 32, message = "length must between 5 and 32 characters")
	private String password;


}
