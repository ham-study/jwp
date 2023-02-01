import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringCalculatorTest {
	private StringCalculator stringCalculator;

	@BeforeEach
	public void setup() {
		stringCalculator = new StringCalculator();
	}

	@Test
	public void addNullOrEmptyString() {
		assertEquals(0, stringCalculator.add(null));
		assertEquals(0, stringCalculator.add(""));
	}

	@Test
	public void addOnlyNumbers() {
		assertEquals(1, stringCalculator.add("1"));
	}

	@Test
	public void addNumberWithDefaultDelimiter() {
		assertEquals(3, stringCalculator.add("1,2"));
		assertEquals(6, stringCalculator.add("1,2:3"));
	}

	@Test
	public void addNumberWithCustomDelimiter() {
		assertEquals(6, stringCalculator.add("//;\n1;2;3"));
	}

	@Test
	public void addNegativeNumbers() {
		assertThrows(RuntimeException.class, () -> stringCalculator.add("-1,2,3"));
	}
}
