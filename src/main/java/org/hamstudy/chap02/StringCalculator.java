package org.hamstudy.chap02;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringCalculator {
	public int calculate(String input) {
		List<Integer> numbers = Expression.of(input)
			.toNumbers();

		validatePositiveOrZero(numbers);

		return sum(numbers);
	}

	private int sum(List<Integer> numbers) {
		int sum = 0;
		for (Integer number : numbers) {
			sum = Calculator.add(sum, number);
		}

		return sum;
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
		private static final String DEFAULT_SEPARATORS = ",|;";
		private static final int REGEX_SEPARATOR_IDX = 1;
		private static final int REGEX_EXPRESSION_IDX = 2;

		public static Expression of(String input) {
			if (input == null || input.isBlank()) {
				throw new IllegalArgumentException("input should not be empty or blank");
			}

			Matcher matcher = CUSTOM_SEPARATOR_PATTERN.matcher(input);
			if (matcher.find()) {
				return new Expression(matcher.group(REGEX_SEPARATOR_IDX), matcher.group(REGEX_EXPRESSION_IDX));
			}

			return new Expression(DEFAULT_SEPARATORS, input);
		}

		public List<Integer> toNumbers() {
			return Arrays.stream(value.split(separator))
				.map(Integer::parseInt)
				.collect(Collectors.toList());
		}
	}
}
