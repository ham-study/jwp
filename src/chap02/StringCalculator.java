package chap02;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StringCalculator {

    private static final int ZERO = 0;

    private static final String SEPARATOR_COMMA = ",";
    private static final String SEPARATOR_COLON = ":";

    private static final Pattern CUSTOM_SEPARATOR_PATTERN = Pattern.compile("//.\\n");

    public StringCalculator() {
    }

    public int calculate(String input) {
        List<String> tokens = new ArrayList<>();

        tokens.add(input);

        Matcher matcher = CUSTOM_SEPARATOR_PATTERN.matcher(input);

        if (matcher.find()) {
            String customSeparator = getCustomSeparator(matcher.group());

            tokens.clear();

            tokens.add(input.substring(4));

            tokens = splitTokens(tokens, customSeparator);
        }

        tokens = splitTokens(tokens, SEPARATOR_COLON);

        tokens = splitTokens(tokens, SEPARATOR_COMMA);

        List<Integer> values = convertToIntValues(tokens);

        validateValues(values);

        return addAllValues(values);
    }

    public List<String> splitTokens(List<String> tokens, String separator) {
        return tokens.stream()
                .flatMap(token -> Stream.of(StringUtils.split(token, separator)))
                .toList();
    }

    public String getCustomSeparator(String s) {
        return StringUtils.substring(s, 2, 3);
    }

    public List<Integer> convertToIntValues(List<String> tokens) {
        return tokens.stream()
                .map(Integer::parseInt)
                .toList();
    }

    public void validateValues(List<Integer> values) {
        for (int value : values) {
            if (value < ZERO) {
                throw new RuntimeException("Contains minus value, " + value);
            }
        }
    }

    public int addAllValues(List<Integer> values) {
        return values.stream()
                .reduce(Integer::sum)
                .orElse(ZERO);
    }
}
