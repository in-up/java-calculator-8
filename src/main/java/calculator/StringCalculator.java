package calculator;

public class StringCalculator {
    public static int add(String input) {
        // 공백 문자열 처리
        if (input == null || input.isBlank()) {
            return 0;
        }
        // TODO: 문자열에서 숫자를 추출하여 더하는 계산기를 구현
        // TODO: 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException 처리
        throw new IllegalArgumentException("Illegal Argument Exception: " + input);
    }
}