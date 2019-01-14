package ru.gromov.resvote;

import java.io.File;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class TestUtil {


	public  File getTestFile(String fileNmae) {
		return new File(getClass().getClassLoader()
				.getResource(fileNmae).getFile());
	}
}
