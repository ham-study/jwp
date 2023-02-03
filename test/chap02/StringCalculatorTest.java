package chap02;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @Before
    public void setup() {
        stringCalculator = new StringCalculator();
    }

    @Test
    public void calculateTest() {
        // given
        final String input = "//;\n1:2:3;4,5,6:7;8:9,10";

        // when & then
        assertEquals(55, stringCalculator.calculate(input));
    }

    @Test
    public void splitTokensTest() {
        // given
        final String[] expected = {"1", "2", "3", "4", "5"};
        final List<String> tokens = List.of("1.2.3.4.5");
        final String separator = ".";

        // when
        List<String> result = stringCalculator.splitTokens(tokens, separator);
        String[] actual = result.toArray(new String[0]);

        // then
        assertArrayEquals(expected, actual);
    }

    @Test
    public void getCustomSeparatorTest() {
        // given
        final String expected = "?";
        final String customSeparator = "//?\n";

        // when & then
        assertEquals(expected, stringCalculator.getCustomSeparator(customSeparator));
    }

    @Test
    public void convertToIntValuesTest() {
        // given
        final int[] expected = {1, 2, 3, 4, 5};
        final List<String> tokens = List.of("1", "2", "3", "4", "5");

        // when
        List<Integer> result = stringCalculator.convertToIntValues(tokens);
        int[] actual = result.stream()
                .mapToInt(i -> i)
                .toArray();

        // then
        assertArrayEquals(expected, actual);
    }

    @Test
    public void validateValuesExceptionTest() {
        // given
        final List<Integer> values = List.of(1, 2, 3, 4, 5, -1);

        // when & then
        assertThrows(RuntimeException.class, () -> stringCalculator.validateValues(values));
    }

    @Test
    public void addAllValuesTest() {
        // given
        final int expected = 15;
        final List<Integer> values = List.of(1, 2, 3, 4, 5);

        // when
        int actual = stringCalculator.addAllValues(values);

        // then
        assertEquals(expected, actual);
    }
}