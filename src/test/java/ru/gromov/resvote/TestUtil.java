package ru.gromov.resvote;

import lombok.SneakyThrows;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;
import ru.gromov.resvote.service.VoteServiceImpl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class TestUtil {


	public File getTestFile(String fileNmae) {
		return new File(getClass().getClassLoader()
				.getResource(fileNmae).getFile());
	}

	@SneakyThrows
	public String getJsonString(Path path) {
		StringBuilder contentBuilder = new StringBuilder();
		try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		}
		return contentBuilder.toString();
	}

	public static String getContent(ResultActions action) throws UnsupportedEncodingException {
		return action.andReturn().getResponse().getContentAsString();
	}

	public static void setDeadlineTime(String timeIso) {
		ReflectionTestUtils.setField(
				VoteServiceImpl.class,
				"deadline",
				timeIso);
	}
}
