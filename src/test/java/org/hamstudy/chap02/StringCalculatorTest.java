package org.hamstudy.chap02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class StringCalculatorTest {
	private StringCalculator sut;

	@BeforeEach
	void setUp() {
		sut = new StringCalculator();
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"//!\n1!2!3",
		"//%\n1%2%3",
		"1:2:3",
		"1,2,3"
	})
	void testCalculate(String input) {
		int result = sut.calculate(input);
		assertEquals(result, 1 + 2 + 3);
	}

	@ParameterizedTest
	@NullAndEmptySource
	void testCalculateWhenInputIsNullOrEmpty(String input) {
		int result = sut.calculate(input);

		assertEquals(result, 0);
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"-1,1,1",
		"1:2:3:-5",
		"//;\n1;2;-3"
	})
	void testCalculateWhenNegative(String input) {
		assertThrows(
			IllegalArgumentException.class,
			() -> sut.calculate(input)
		);
	}
}