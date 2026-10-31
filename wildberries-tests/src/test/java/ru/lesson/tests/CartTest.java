package ru.lesson.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.lesson.base.BaseTest;
import ru.lesson.components.ProductCard;
import ru.lesson.config.SiteConstants;
import ru.lesson.model.Product;
import ru.lesson.pages.CartPage;
import ru.lesson.pages.HomePage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("ui")
class CartTest extends BaseTest {
    private static final int DEFAULT_PRODUCT_COUNT = 3;
    private final List<Product> addedProducts = new ArrayList<>();

    @Test
    @DisplayName("Товары с главной страницы сохраняют в корзине названия, количество, цены и общую сумму")
    void productsAddedFromHomepageMatchCartAndTotal() {
        int productCount = Integer.getInteger("productsCount", DEFAULT_PRODUCT_COUNT);
        HomePage home = new HomePage(driver).open();
        assertEquals(SiteConstants.CURRENCY, home.getCurrency(), "Тест рассчитан на цены в рублях");
        assertTrue(home.openCart().isEmpty(), "Для теста нужен отдельный профиль с пустой корзиной");
        home.open();
        for (ProductCard card : home.selectProducts(productCount)) {
            // Ожидания берём из карточки ДО добавления, а не из проверяемой корзины.
            addedProducts.add(card.readProduct());
            home.addProduct(card);
        }
        CartPage cart = home.openCart();
        cart.waitForProducts(addedProducts);
        List<Product> actualProducts = cart.getProducts();
        Map<String, Product> actualByArticle = actualProducts.stream()
                .collect(Collectors.toMap(Product::article, Function.identity()));
        BigDecimal expectedTotal = addedProducts.stream().map(Product::total).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal actualLinesTotal = actualProducts.stream().map(Product::total).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal displayedTotal = cart.getTotal();
        int actualQuantity = actualProducts.stream().mapToInt(Product::quantity).sum();

        assertAll("Состав и итог корзины",
                () -> assertEquals(productCount, actualProducts.size(), "Неверное количество разных товаров"),
                () -> assertEquals(addedProducts.stream().map(Product::article).collect(Collectors.toSet()),
                        actualByArticle.keySet(), "В корзине другой набор артикулов"),
                () -> assertEquals(productCount, actualQuantity, "Неверное количество единиц товаров"),
                () -> assertEquals(productCount, cart.getTotalQuantity(), "Неверное количество товаров в итоговом блоке"),
                () -> assertEquals(0, expectedTotal.compareTo(displayedTotal), "Итог отличается от суммы выбранных товаров"),
                () -> assertEquals(0, actualLinesTotal.compareTo(displayedTotal), "Итог отличается от суммы строк корзины"),
                () -> assertAll("Данные каждого товара", addedProducts.stream().map(expected -> () -> {
                    assertTrue(actualByArticle.containsKey(expected.article()), "Не найден товар " + expected.article());
                    Product actual = actualByArticle.get(expected.article());
                    assertAll("Товар " + expected.article(),
                            () -> assertEquals(expected.name(), actual.name(), "Неверное название"),
                            () -> assertEquals(expected.quantity(), actual.quantity(), "Неверное количество"),
                            () -> assertEquals(0, expected.unitPrice().compareTo(actual.unitPrice()), "Неверная цена товара"));
                })));
    }

    @AfterEach
    void removeOnlyProductsAddedByThisTest() {
        if (driver != null && !addedProducts.isEmpty()) {
            CartPage cart = driver.getCurrentUrl().contains(SiteConstants.CART_PATH)
                    ? new CartPage(driver).waitUntilOpen() : new HomePage(driver).openCart();
            cart.removeProducts(addedProducts);
        }
    }
}
