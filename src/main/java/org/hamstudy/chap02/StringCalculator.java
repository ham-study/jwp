package org.hamstudy.chap02;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
	private static final Pattern CUSTOM_SEPARATOR_PATTERN = Pattern.compile(
		"//(\\D)\n(.+)"
	);
	private static final String DEFAULT_SEPARATORS = ",|;";
	private static final int REGEX_SEPARATOR_IDX = 1;
	private static final int REGEX_EXPRESSION_IDX = 2;

	public int calculate(String input) {
		if (input == null || input.isBlank()) {
			throw new IllegalArgumentException(String.format("input should not be empty or blank. input: %s", input));
		}

		String separator = DEFAULT_SEPARATORS;
		String expression = input;

		Matcher matcher = CUSTOM_SEPARATOR_PATTERN.matcher(input);
		if (matcher.find()) {
			separator = matcher.group(REGEX_SEPARATOR_IDX);
			expression = matcher.group(REGEX_EXPRESSION_IDX);
		}

		return sum(toNumbers(separator, expression));
	}

	private int sum(List<Integer> numbers) {
		int sum = 0;
		for (Integer number : numbers) {
			validatePositiveOrZero(number);
			sum = Calculator.add(sum, number);
		}

		return sum;
	}

	private void validatePositiveOrZero(Integer number) {
		if (number < 0) {
			throw new IllegalArgumentException("number should be zero or positive");
		}
	}

	private List<Integer> toNumbers(String separator, String expression) {
		return Arrays.stream(
				expression.split(separator)
			)
			.map(Integer::parseInt)
			.toList();
	}
}
