package ru.lesson.locators;

import org.openqa.selenium.By;

public final class CartLocators {
    public static final String ROOT_SELECTOR = "[data-testid='basket-page']";
    public static final String ITEM_SELECTOR = ROOT_SELECTOR + " .j-b-basket-item";
    public static final String HEADER_SELECTOR = "[data-testid='basket-header']";

    public static final By ROOT = By.cssSelector(ROOT_SELECTOR);
    public static final By ITEMS = By.cssSelector(ITEM_SELECTOR + "[data-basket-item-id]");
    public static final By HEADER_LINK = By.cssSelector(HEADER_SELECTOR);
    public static final By HEADER_COUNTER = By.cssSelector(HEADER_SELECTOR + " .navbar-pc__notify");

    private CartLocators() {
    }
}
