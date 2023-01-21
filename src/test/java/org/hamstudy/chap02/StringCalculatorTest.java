package org.hamstudy.chap02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
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
		"//%\n1%2%3"
	})
	void testCalculate(String input) {
		int result = sut.calculate(input);
		assertEquals(result, 1 + 2 + 3);
	}
}