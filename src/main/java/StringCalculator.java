import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class StringCalculator {

	/**
	 * 전달하는 문자를 구분자로 분리한 후 각 숫자의 합을 구해 반환.
	 * 1. 쉼표(,) 또는 콜론(:)을 구분자로 가지는 문자열을 전달하는 경우 구분자를 기준으로 분리한 각 숫자의 합을 반환.
	 * 2. 앞의 기본 구분자(쉽표, 콜론)외에 커스텀 구분자를 지정할 수 있다.
	 * 2-1. 커스텀 구분자. "//"와 "\n"사이에 위치하는 문자를 커스텀 구분자로 사용한다.
	 * 2-2. 예를 들어 "//;\n1:2:3" => 1+2+3 = 6
	 * 3. 음수를 전달하는 경우 RuntimeException으로 예외처리
	 *
	 */

	private static final String REGEX_CUSTOM_DELIMITER = "//(.)\n(.*)";
	private static final String REGEX_DEFAULT_DELIMITER = ",|:";

	public int add(String inputText) {
		if (StringUtils.isEmpty(inputText)) {
			return 0;
		}

		Matcher matcher = Pattern.compile(REGEX_CUSTOM_DELIMITER).matcher(inputText);

		if (matcher.find()) {
			return sumNumbersByCustomDelimiter(matcher);
		}

		return sumNumbersByDefaultDelimiter(inputText);
	}

	private int sumNumbersByCustomDelimiter(Matcher matcher) {
		String customDelimiter = matcher.group(1);
		String text = matcher.group(2);

		return sumSplitNumbers(customDelimiter, text);
	}

	private int sumNumbersByDefaultDelimiter(String inputText) {
		return sumSplitNumbers(REGEX_DEFAULT_DELIMITER, inputText);
	}

	private int sumSplitNumbers(String delimiter, String text) {
		return getSplitNumbers(delimiter, text)
			.stream()
			.mapToInt(this::toPositiveNumber)
			.sum();
	}

	private List<String> getSplitNumbers(String delimiter, String text) {
		return Arrays.asList(Pattern.compile(delimiter).split(text));
	}

	private int toPositiveNumber(String stringNumber) {
		int number = Integer.parseInt(stringNumber);
		if (number < 0) {
			throw new RuntimeException("inputText Contains negative numbers");
		}

		return number;
	}
}
