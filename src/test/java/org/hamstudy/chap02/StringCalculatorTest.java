package org.hamstudy.chap02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StringCalculatorTest {
	StringCalculator stringCalculator;

	@BeforeEach
	void init() {
		stringCalculator = new StringCalculator();
	}

	@Test
	void addWhenNull() {
		//when
		//then
		IllegalArgumentException illegalArgumentException =  assertThrows(
			IllegalArgumentException.class,
			() -> stringCalculator.add(null));
		assertEquals(illegalArgumentException.getMessage(), "text가 null일 수 없습니다.");
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "	"})
	void addWhenBlank(String text) {
		//when
		int result = stringCalculator.add(text);

		//then
		assertEquals(0, result);
	}

	@Test
	void addWhenDefaultDelimiterPatternWithNegativeValue() {
		//given
		String text = "1, -2, 3";

		//when
		//then
		IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> stringCalculator.add(text));
		assertTrue(illegalArgumentException.getMessage().startsWith("음수가 포함될 수 없습니다. 수: "));
	}

	@ParameterizedTest
	@ValueSource(strings = {"1,2,3", "1:2:3", "1 , 2 , 3", "1 : 2 : 3", "1,2,,3"})
	void addWhenDefaultDelimiterPattern(String text) {
		//when
		int result = stringCalculator.add(text);

		//then
		assertEquals(6, result);
	}

	@ParameterizedTest
	@ValueSource(strings = {"//;\na;b;c", "//;\n1:2:3"})
	void addWhenCustomDelimiterPatternWithNotInteger(String text) {
		//when
		//then
		IllegalArgumentException illegalArgumentException = assertThrows(
			IllegalArgumentException.class,
			() -> stringCalculator.add(text));
		assertTrue(illegalArgumentException.getMessage().startsWith("구분자로 구분된 토큰이 정수가 아닙니다. 토큰: "));
	}

	@ParameterizedTest
	@ValueSource(strings = {"/;\n1;2;3", "asdf"})
	void addWhenInvalidCustomDelimiterPattern(String text) {
		//when
		//then
		IllegalArgumentException illegalArgumentException = assertThrows(
			IllegalArgumentException.class,
			() -> stringCalculator.add(text));
		assertEquals("커스텀 구분자의 형식이 아닙니다. 형식: ^\\/\\/(.)\n(.*)", illegalArgumentException.getMessage());
	}

	@Test
	void addWhenCustomDelimiterPatternWithNegativeValue() {
		//given
		String text = "//;\n1;-2;3";

		//when
		//then
		IllegalArgumentException illegalArgumentException = assertThrows(
			IllegalArgumentException.class,
			() -> stringCalculator.add(text)
		);
		assertTrue(illegalArgumentException.getMessage().startsWith("음수가 포함될 수 없습니다. 수: "));
	}

	@ParameterizedTest
	@ValueSource(strings = "//;\n1;2 ;; 3")
	void addWhenCustomDelimiterPattern(String text) {
		//when
		int result = stringCalculator.add(text);

		//then
		assertEquals(6, result);
	}
}