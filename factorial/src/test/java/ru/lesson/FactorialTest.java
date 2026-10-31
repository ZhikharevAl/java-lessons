package ru.lesson;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FactorialTest {

    @ParameterizedTest(name = "факториал {0} = {1}")
    @CsvSource({
            "0, 1",
            "1, 1",
            "2, 2",
            "3, 6",
            "4, 24",
            "5, 120",
            "10, 3628800",
            "20, 2432902008176640000"
    })
    @DisplayName("Факториал корректных значений")
    void calculatesFactorial(int n, long expected) {
        assertEquals(expected, Factorial.calculate(n));
    }

    @Test
    @DisplayName("Факториал отрицательного числа бросает исключение")
    void throwsOnNegative() {
        assertThrows(IllegalArgumentException.class, () -> Factorial.calculate(-1));
    }
}
