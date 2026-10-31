package ru.lesson.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

public final class Money {
    private static final Pattern WHITESPACE = Pattern.compile("[\\s\\p{Zs}]+");
    private static final Pattern CURRENCY_SUFFIX = Pattern.compile("(?iu)\\s*(₽|руб\\.?|р\\.?)$");
    private static final Pattern RUBLES_AMOUNT = Pattern.compile("(?:[0-9]{1,3}(?: [0-9]{3})+|[0-9]+)(?:[.,][0-9]{1,2})?");
    private static final int RUBLES_SCALE = 2;

    private Money() {
    }

    public static BigDecimal rubles(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Цена отсутствует");
        }
        String normalized = WHITESPACE.matcher(text).replaceAll(" ").trim();
        String number = CURRENCY_SUFFIX.matcher(normalized).replaceFirst("").trim();
        if (!RUBLES_AMOUNT.matcher(number).matches()) {
            throw new IllegalArgumentException("Ожидалась одна цена в рублях, получено: " + text);
        }
        return new BigDecimal(number.replace(" ", "").replace(',', '.'))
                .setScale(RUBLES_SCALE, RoundingMode.UNNECESSARY);
    }
}
