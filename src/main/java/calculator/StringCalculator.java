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
                throw new IllegalArgumentException("커스텀 구분자는 1글자 이상일 수 없습니다: " + input);
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

        throw new IllegalArgumentException("처리 가능한 조건의 문자열에 해당하지 않습니다: " + input);
    }

    private static boolean containsDefaultDelimiter(String s) {
        return s.indexOf(',') >= 0 || s.indexOf(':') >= 0;
    }

    private static int sumTokens(String[] tokens) {
        int sum = 0;
        for (String token : tokens) {
            // 빈 토큰인 경우
            if (token == null || token.isBlank()) {
                throw new IllegalArgumentException("빈 토큰은 허용되지 않습니다: (빈 토큰)");
            }
            // 양수 또는 0이 아닌 경우
            if (!token.matches("\\d+")) {
                throw new IllegalArgumentException("숫자가 아닌 값이 포함되어 있습니다: " + token);
            }

            int v = Integer.parseInt(token);
            // 음수인 경우
            if (v < 0) {
                throw new IllegalArgumentException("음수는 허용되지 않습니다: " + v);
            }
            sum += v;
        }
        return sum;
    }

    private StringCalculator() {
    }
}