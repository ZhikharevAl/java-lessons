package ru.lesson.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.lesson.util.Money;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {
    @ParameterizedTest(name = "Цена {0} = {1} RUB")
    @CsvSource(delimiter = '|', value = {
            "1 234 ₽ | 1234.00",
            "1\u00a0234 ₽ | 1234.00",
            "1\u202f234,50 ₽ | 1234.50",
            "999.90 руб. | 999.90",
            "42 р. | 42.00",
            "0 ₽ | 0.00"
    })
    void parsesDisplayedRublesWithoutLosingKopecks(String text, String expected) {
        assertEquals(new BigDecimal(expected), Money.rubles(text));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"Нет в наличии", "1 000 ₽ 2 000 ₽", "1 23 ₽", "-10 ₽", "12.345 ₽", "$10"})
    void rejectsMissingAndAmbiguousPrices(String text) {
        assertThrows(IllegalArgumentException.class, () -> Money.rubles(text));
    }
}
