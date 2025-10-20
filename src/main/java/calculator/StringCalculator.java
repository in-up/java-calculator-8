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
            return sumTokens(input.split(DEFAULT_DELIMS));
        }

        // 커스텀 구분자 처리
        if (input.startsWith("//")) {
            if (input.length() < 4) {
                throw new IllegalArgumentException("잘못된 커스텀 구분자 형식: " + input);
            }
            String delim = String.valueOf(input.charAt(2));

            int idx = input.indexOf('\n', 3);
            int jump = 1;
            if (idx < 0) {
                idx = input.indexOf("\\n", 3);
                jump = 2;
            }
            if (idx < 0) {
                throw new IllegalArgumentException("커스텀 구분자 뒤에는 개행이 필요합니다: " + input);
            }

            String numbers = input.substring(idx + jump);
            return sumTokens(numbers.split(java.util.regex.Pattern.quote(delim)));
        }

        // TODO: 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException 처리

        throw new IllegalArgumentException("Illegal Argument Exception: " + input);
    }

    private static boolean containsDefaultDelimiter(String s) {
        return s.indexOf(',') >= 0 || s.indexOf(':') >= 0;
    }

    private static int sumTokens(String[] tokens) {
        int sum = 0;
        for (String token : tokens) {
            // TODO: 예외 정책
            sum += Integer.parseInt(token);
        }
        return sum;
    }

    private StringCalculator() {
    }
}