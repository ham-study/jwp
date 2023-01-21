package org.hamstudy.chap02;

import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
		"//;\n",
		"//!\n",
		"//@\n",
	})
	void testHasCustomSeparator(String input) {
		assertTrue(sut.hasCustomSeparator(input));
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"//1\n",
		"//2\n",
		"//3\n",
		"//30\n",
		"//aa\n",
		"//@#\n",
	})
	void testHasCustomSeparatorWhenSeparatorIsNotOneChar(String input) {
		assertFalse(sut.hasCustomSeparator(input));
	}
}