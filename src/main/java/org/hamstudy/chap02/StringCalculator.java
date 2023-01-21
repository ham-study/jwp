package org.hamstudy.chap02;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class StringCalculator {
	private static final Pattern CUSTOM_SEPARATOR_PATTERN = Pattern.compile(
		"^(\\/\\/)(\\D)(\\n)(.*)"
	);
	private static Set<String> defaultSeparator = new HashSet<>();

	public StringCalculator() {
		defaultSeparator.addAll(List.of(",", ";"));
	}

	public boolean hasCustomSeparator(String input) {
		if (input == null || input.isBlank()) {
			throw new IllegalArgumentException("input should not be empty");
		}

		return CUSTOM_SEPARATOR_PATTERN
			.matcher(input)
			.matches();
	}
}
