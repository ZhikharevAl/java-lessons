package ru.lesson.model;

import java.math.BigDecimal;

public record Product(String article, String name, BigDecimal unitPrice, int quantity) {
    public Product {
        if (article == null || article.isBlank() || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Товар должен содержать артикул и название");
        }
        if (unitPrice == null || unitPrice.signum() <= 0 || quantity < 1) {
            throw new IllegalArgumentException("Некорректные данные товара: " + article);
        }
    }

    public BigDecimal total() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
