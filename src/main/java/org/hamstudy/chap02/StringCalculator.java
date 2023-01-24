package org.hamstudy.chap02;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
	private static final List<String> DEFAULT_DELIMITERS = List.of(",", ":");
	private static final String DEFAULT_DELIMITERS_REGEX = "[" + String.join("", DEFAULT_DELIMITERS) +"]";
	private static final Pattern WHITE_SPACE_PATTERN = Pattern.compile("\\s");
	private static final String VALID_CUSTOM_DELIMITER_REGEX = "^\\/\\/(.)\n(.*)";
	private static final Pattern CUSTOM_DELEMITER_PATTERN = Pattern.compile(VALID_CUSTOM_DELIMITER_REGEX);

	public int add(String text) {
		if (Objects.isNull(text)) {

			throw new IllegalArgumentException("text가 null일 수 없습니다.");
		}

		if (text.isBlank()) {

			return 0;
		}

		if (isDefaultDelimiterPattern(text)) {
			List<Integer> digits = Arrays.stream(text.split(DEFAULT_DELIMITERS_REGEX))
				.filter((token) -> !token.isBlank())
				.map(String::trim)
				.map(Integer::parseInt)
				.toList();

			assertContainsOnlyPositive(digits);

			return digits.stream()
				.reduce(0, Integer::sum);
		}

		Matcher matcher = CUSTOM_DELEMITER_PATTERN.matcher(text);
		assertValidCustomDelimiterMatcher(matcher);

		String customDelimiter = matcher.group(1);
		String expression = matcher.group(2);
		List<String> stringTokens = Arrays.stream(expression.split(customDelimiter))
			.filter((token) -> !token.isBlank())
			.map(String::trim)
			.toList();

		assertContainsOnlyInteger(stringTokens);

		List<Integer> integerTokens = stringTokens.stream()
			.map(Integer::parseInt)
			.toList();

		assertContainsOnlyPositive(integerTokens);

		return integerTokens.stream()
			.reduce(0, Integer::sum);
	}

	private boolean isDefaultDelimiterPattern(String text) {

		return text.chars()
			.allMatch((intChar) ->
				Character.isDigit(intChar)
				|| (char)intChar == '-'
				|| DEFAULT_DELIMITERS.contains(String.valueOf((char)intChar))
				|| WHITE_SPACE_PATTERN.matcher(String.valueOf((char)intChar)).matches()
			);
	}

	private void assertContainsOnlyPositive(List<Integer> digits) {
		digits.forEach((digit) -> {
			if (digit < 0) {

				throw new IllegalArgumentException(String.format("음수가 포함될 수 없습니다. 수: %s",digit));
			}
		});
	}

	private void assertContainsOnlyInteger(List<String> tokens) {
		tokens.forEach(this::assertInteger);
	}

	private void assertInteger(String token) {
		try{
			Integer.parseInt(token);
		} catch(NumberFormatException numberFormatException) {

			throw new IllegalArgumentException(String.format("구분자로 구분된 토큰이 정수가 아닙니다. 토큰: %s", token), numberFormatException);
		}
	}

	private void assertValidCustomDelimiterMatcher(Matcher matcher) {
		if (!matcher.find()) {

			throw new IllegalArgumentException(String.format("커스텀 구분자의 형식이 아닙니다. 형식: %s", VALID_CUSTOM_DELIMITER_REGEX));
		}
	}
}
