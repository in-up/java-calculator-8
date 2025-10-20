package calculator;

public class Application {
    public static void main(String[] args) {
        String input = InputView.readString();
        int result = StringCalculator.add(input);
        OutputView.printResult(result);
    }
}