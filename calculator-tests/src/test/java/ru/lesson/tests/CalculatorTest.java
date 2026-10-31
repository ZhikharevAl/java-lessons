package ru.lesson.tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.lesson.base.BaseTest;
import ru.lesson.model.Operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Арифметические действия в Google Калькуляторе")
@Epic("Google Калькулятор")
@Feature("Арифметические действия")
class CalculatorTest extends BaseTest {
    @Description("Проверка результата на экране после ввода выражения и нажатия кнопки равно")
    @Severity(SeverityLevel.NORMAL)
    @ParameterizedTest(name = "[{index}] {0} {1} {2} = {3}")
    @CsvSource({
            "12, ADD, 8, 20",
            "7, SUBTRACT, 12, -5",
            "9, MULTIPLY, 7, 63",
            "7, DIVIDE, 2, 3.5"
    })
    void shouldDisplayExpectedResult(String left, Operation operation, String right, String expected) {
        Allure.parameter("Первое число", left);
        Allure.parameter("Операция", operation);
        Allure.parameter("Второе число", right);
        Allure.parameter("Ожидаемый результат", expected);
        String displayed = calculator.calculate(left, operation, right).displayedResult();
        String actual = displayed.strip().replace('\u2212', '-').replace(',', '.');

        Allure.step("Сравнить результат с ожидаемым: " + expected, () -> {
            Allure.addAttachment("Результат на экране", displayed);
            assertEquals(expected, actual, () -> "Неверный результат на экране: " + displayed);
        });
    }
}
