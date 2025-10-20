package calculator;

public class StringCalculator {
    private static final String DEFAULT_DELIMS = "[,:]";

    public static int add(String input) {
        // 공백 문자열 처리
        if (input == null || input.isBlank()) {
            return 0;
        }

        // 단일 숫자 처리
        if (input.matches("\\d+")) {
            return Integer.parseInt(input);
        }

        // 기본 구분자 처리
        if (containsDefaultDelimiter(input)) {
            String[] tokens = input.split(DEFAULT_DELIMS);
            int sum = 0;
            for (String token : tokens) {
                // TODO: 예외 정책
                sum += Integer.parseInt(token);
            }
            return sum;
        }

        // TODO: 커스텀 구분자 처리
        // TODO: 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException 처리

        throw new IllegalArgumentException("Illegal Argument Exception: " + input);
    }

    private static boolean containsDefaultDelimiter(String s) {
        return s.indexOf(',') >= 0 || s.indexOf(':') >= 0;
    }

    private StringCalculator() {
    }
}