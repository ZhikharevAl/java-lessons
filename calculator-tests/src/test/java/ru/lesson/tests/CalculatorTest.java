package ru.lesson.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.lesson.base.BaseTest;
import ru.lesson.model.Operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Арифметические действия в Google Калькуляторе")
class CalculatorTest extends BaseTest {
    @ParameterizedTest(name = "[{index}] {0} {1} {2} = {3}")
    @CsvSource({
            "12, ADD, 8, 20",
            "7, SUBTRACT, 12, -5",
            "9, MULTIPLY, 7, 63",
            "7, DIVIDE, 2, 3.5"
    })
    void shouldDisplayExpectedResult(String left, Operation operation, String right, String expected) {
        String displayed = calculator.calculate(left, operation, right).displayedResult();
        String actual = displayed.strip().replace('\u2212', '-').replace(',', '.');

        assertEquals(expected, actual, () -> "Неверный результат на экране: " + displayed);
    }
}
