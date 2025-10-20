package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {

    @Nested
    @DisplayName("통합 테스트 (사용자 입출력)")
    class IntegrationTests {

        @Test
        @DisplayName("커스텀 구분자를 사용하여 계산을 성공한다.")
        void calculatesWithCustomDelimiter() {
            assertSimpleTest(() -> {
                run("//;\n1;2;3");
                assertThat(output()).contains("결과 : 6");
            });
        }

        @Test
        @DisplayName("기본 구분자를 사용하여 계산을 성공한다.")
        void calculatesWithDefaultDelimiters() {
            assertSimpleTest(() -> {
                run("1,2:3");
                assertThat(output()).contains("결과 : 6");
            });
        }

        @Test
        @DisplayName("커스텀 구분자로 단일 숫자를 입력했을 때 계산을 성공한다.")
        void singleNumberWithCustomDelimiter() {
            assertSimpleTest(() -> {
                run("//;\n1");
                assertThat(output()).contains("결과 : 1");
            });
        }
    }

    @Nested
    @DisplayName("단위 테스트 (StringCalculator.add)")
    class UnitTests {

        @ParameterizedTest
        @CsvSource(value = {"//;\n1;2;3#6", "//*\n1*2*3#6"}, delimiter = '#')
        @DisplayName("커스텀 구분자로 분리된 숫자의 합을 반환한다.")
        void customDelimiter_returnSum(String input, int expected) {
            assertThat(StringCalculator.add(input)).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvSource(value = {"1,2:3#6", "1:2,3#6"}, delimiter = '#')
        @DisplayName("기본 구분자(쉼표, 콜론)로 분리된 숫자의 합을 반환한다.")
        void defaultDelimiters_returnSum(String input, int expected) {
            assertThat(StringCalculator.add(input)).isEqualTo(expected);
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("null이나 빈 문자열이 입력되면 0을 반환한다.")
        void nullOrEmpty_returnsZero(String input) {
            assertThat(StringCalculator.add(input)).isZero();
        }

        @ParameterizedTest
        @ValueSource(strings = {"5", "10"})
        @DisplayName("숫자 하나만 입력되면 해당 숫자를 반환한다.")
        void singleNumber_returnsSame(String input) {
            assertThat(StringCalculator.add(input)).isEqualTo(Integer.parseInt(input));
        }
    }

    @Nested
    @DisplayName("예외 테스트")
    class ExceptionTests {

        @Test
        @DisplayName("숫자 이외의 값이 포함되어 있으면 예외를 발생시킨다. (로직)")
        void containsNonNumeric_throwsException() {
            assertThatThrownBy(() -> StringCalculator.add("1,a,2"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("음수가 포함되어 있으면 예외를 발생시킨다. (로직)")
        void containsNegative_throwsException() {
            assertThatThrownBy(() -> StringCalculator.add("-1,2,3"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("숫자가 아닌 값을 입력하면 예외가 발생한다. (입출력)")
        void nonNumericInput_throwsException() {
            assertSimpleTest(() ->
                    assertThatThrownBy(() -> runException("1,a,3"))
                            .isInstanceOf(IllegalArgumentException.class)
            );
        }

        @Test
        @DisplayName("음수를 입력하면 예외가 발생한다. (입출력)")
        void negativeInput_throwsException() {
            assertSimpleTest(() ->
                    assertThatThrownBy(() -> runException("-1,2,3"))
                            .isInstanceOf(IllegalArgumentException.class)
            );
        }
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
