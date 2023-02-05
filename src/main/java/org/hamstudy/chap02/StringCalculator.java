package org.hamstudy.chap02;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
	public int calculate(String input) {
		List<Integer> numbers = Expression.of(input)
			.toNumbers();

		validatePositiveOrZero(numbers);

		return sum(numbers);
	}

	private int sum(List<Integer> numbers) {
		return numbers.stream()
			.mapToInt(Integer::intValue)
			.reduce(0, Calculator::add);
	}

	private void validatePositiveOrZero(List<Integer> numbers) {
		for (Integer number : numbers) {
			validatePositiveOrZero(number);
		}
	}

	private void validatePositiveOrZero(Integer number) {
		if (number < 0) {
			throw new IllegalArgumentException("number should be zero or positive");
		}
	}

	private record Expression(String separator, String value) {
		private static final Pattern CUSTOM_SEPARATOR_PATTERN = Pattern.compile(
			"//(\\D)\n(.+)"
		);
		private static final String DEFAULT_SEPARATORS = ",|:";
		private static final int REGEX_SEPARATOR_IDX = 1;
		private static final int REGEX_EXPRESSION_IDX = 2;

		public static Expression of(String input) {
			if (input == null || input.isBlank()) {
				return new Expression(null, null);
			}

			Matcher matcher = CUSTOM_SEPARATOR_PATTERN.matcher(input);
			if (matcher.find()) {
				return new Expression(matcher.group(REGEX_SEPARATOR_IDX), matcher.group(REGEX_EXPRESSION_IDX));
			}

			return new Expression(DEFAULT_SEPARATORS, input);
		}

		public List<Integer> toNumbers() {
			if (value == null || separator == null) {
				return Collections.emptyList();
			}

			return Arrays.stream(
					value.split(separator)
				)
				.map(Integer::parseInt)
				.toList();
		}
	}
}
