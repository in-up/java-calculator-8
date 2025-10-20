package calculator;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public static String readString() {
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String first = Console.readLine();

        String input = first;

        // 커스텀 구분자의 접미사가 개행 처리된 경우 한 줄 더 입력받음
        if (first != null && first.startsWith("//") && !first.contains("\n") && !first.contains("\\n")) {
            String second = Console.readLine();
            if (second != null) {
                input = first + "\n" + second;
            }
        }
        return input;
    }

    private InputView() {
    }
}
