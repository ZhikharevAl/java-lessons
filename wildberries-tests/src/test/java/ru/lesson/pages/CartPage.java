package ru.lesson.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.lesson.base.BasePage;
import ru.lesson.components.CartItem;
import ru.lesson.config.SiteConstants;
import ru.lesson.locators.CartLocators;
import ru.lesson.model.Product;
import ru.lesson.util.Money;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CartPage extends BasePage {
    private static final String EMPTY_CART_MESSAGE = "В корзине пока пусто";
    private static final By TOTAL = By.cssSelector(CartLocators.ROOT_SELECTOR
            + " button[class*='basketSummaryBtn--'] .mo-button__prices");
    private static final By COUNT = By.cssSelector(CartLocators.ROOT_SELECTOR + " [class*='basketSummaryTitle--']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage waitUntilOpen() {
        wait.until(ExpectedConditions.urlContains(SiteConstants.CART_PATH));
        wait.until(d -> {
            checkSiteAccess();
            return d.findElements(CartLocators.ROOT).stream().anyMatch(WebElement::isDisplayed);
        });
        return this;
    }

    public boolean isEmpty() {
        return visible(CartLocators.ROOT).getText().contains(EMPTY_CART_MESSAGE)
                && driver.findElements(CartLocators.ITEMS).isEmpty()
                && driver.findElements(CartLocators.HEADER_COUNTER).isEmpty();
    }

    public void waitForProducts(List<Product> expected) {
        Set<String> expectedArticles = expected.stream().map(Product::article).collect(Collectors.toSet());
        wait.withMessage("В корзине не появились добавленные товары: " + expectedArticles).until(d -> {
            checkSiteAccess();
            Set<String> actual = getItems().stream().map(CartItem::getArticle).collect(Collectors.toSet());
            return actual.containsAll(expectedArticles);
        });
        visible(TOTAL);
    }

    public List<Product> getProducts() {
        return getItems().stream().map(CartItem::readProduct).toList();
    }

    public BigDecimal getTotal() {
        return Money.rubles(nonEmptyText(TOTAL));
    }

    public int getTotalQuantity() {
        return Integer.parseInt(nonEmptyText(COUNT).replaceAll("[^0-9]", ""));
    }

    public void removeProducts(List<Product> products) {
        for (Product product : products) {
            By ownItem = By.cssSelector(CartLocators.ITEM_SELECTOR
                    + ":has(a[data-search='true'][href*='" + SiteConstants.CATALOG_PATH + product.article() + "/'])");
            // После каждого удаления React перестраивает список; заново находим оставшуюся строку.
            for (WebElement item : driver.findElements(ownItem)) {
                new CartItem(driver, item.getDomAttribute("data-basket-item-id")).remove();
            }
        }
    }

    private List<CartItem> getItems() {
        return driver.findElements(CartLocators.ITEMS).stream().filter(WebElement::isDisplayed)
                .map(item -> new CartItem(driver, item.getDomAttribute("data-basket-item-id"))).toList();
    }
}
