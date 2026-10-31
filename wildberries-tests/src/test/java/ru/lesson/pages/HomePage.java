package ru.lesson.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.lesson.base.BasePage;
import ru.lesson.components.ProductCard;
import ru.lesson.config.SiteConstants;
import ru.lesson.locators.CartLocators;
import ru.lesson.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HomePage extends BasePage {
    private static final int MIN_PRODUCT_COUNT = 2;
    // Выбираем товары, для которых обычно не требуется выбирать размер одежды или обуви.
    private static final Pattern HOUSEHOLD_PRODUCT = Pattern.compile(
            "(?iu)штатив|швабр|шампун|гель|мыло|кофе|чайник|салфет|контейнер|пакет|тушь|корм|фильтр|наушник|очистител|наполнитель");
    private static final By CARDS = By.cssSelector("[data-testid='main-page'] article.product-card[data-nm-id]");
    private static final By LOGO = By.cssSelector("[data-testid='main-logo-wb']");
    private static final By CURRENCY = By.cssSelector("[data-testid='selected-currency']");
    private static final By ACCEPT_COOKIES = By.xpath("//*[self::button or self::a][normalize-space()='Окей']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        if (driver.getCurrentUrl().startsWith(SiteConstants.BASE_URL)) {
            checkSiteAccess();
            if (!driver.getCurrentUrl().equals(SiteConstants.BASE_URL)) {
                click(LOGO);
            }
        } else {
            driver.get(SiteConstants.BASE_URL);
        }
        wait.until(d -> {
            checkSiteAccess();
            return d.findElements(CARDS).stream().anyMatch(WebElement::isDisplayed);
        });
        dismissCookies();
        return this;
    }

    public String getCurrency() {
        return normalize(visible(CURRENCY).getText());
    }

    public List<ProductCard> selectProducts(int count) {
        if (count < MIN_PRODUCT_COUNT) {
            throw new IllegalArgumentException("Нужно выбрать минимум два разных товара");
        }
        List<String> articles = driver.findElements(CARDS).stream()
                .filter(WebElement::isDisplayed).map(card -> card.getDomAttribute("data-nm-id")).distinct().toList();
        List<ProductCard> selected = new ArrayList<>();
        for (String article : articles) {
            ProductCard card = new ProductCard(driver, article);
            if (!card.hasAddToCartButton()) {
                continue;
            }
            Product product = card.readProduct();
            if (!product.name().endsWith("…") && HOUSEHOLD_PRODUCT.matcher(product.name()).find()) {
                selected.add(card);
                if (selected.size() == count) {
                    return List.copyOf(selected);
                }
            }
        }
        throw new IllegalStateException("На главной странице найдено только " + selected.size()
                + " подходящих товаров из необходимых " + count);
    }

    public void addProduct(ProductCard card) {
        int expectedCount = getBasketCount() + 1;
        card.addToCart();
        wait.withMessage("После добавления товара счётчик корзины не стал равен " + expectedCount)
                .until(d -> getBasketCount() == expectedCount);
    }

    public int getBasketCount() {
        List<WebElement> counters = driver.findElements(CartLocators.HEADER_COUNTER);
        return counters.isEmpty() ? 0 : Integer.parseInt(normalize(counters.get(0).getText()));
    }

    public CartPage openCart() {
        click(CartLocators.HEADER_LINK);
        return new CartPage(driver).waitUntilOpen();
    }

    private void dismissCookies() {
        for (WebElement element : driver.findElements(ACCEPT_COOKIES)) {
            if (element.isDisplayed()) {
                click(ACCEPT_COOKIES);
                return;
            }
        }
    }
}
