package ru.lesson;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

public class FactorialTest {

    @DataProvider(name = "factorialData")
    public Object[][] factorialData() {
        return new Object[][]{
                {0, 1L},
                {1, 1L},
                {2, 2L},
                {3, 6L},
                {4, 24L},
                {5, 120L},
                {10, 3628800L},
                {20, 2432902008176640000L}
        };
    }

    @Test(dataProvider = "factorialData")
    public void calculatesFactorial(int n, long expected) {
        assertEquals(expected, Factorial.calculate(n));
    }

    @Test
    public void throwsOnNegative() {
        IllegalArgumentException thrown = expectThrows(
                IllegalArgumentException.class,
                () -> Factorial.calculate(-1)
        );
        assertEquals(thrown.getMessage(), "Факториал определён только для неотрицательных чисел: -1");
    }
}
