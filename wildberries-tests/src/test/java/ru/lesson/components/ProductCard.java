package ru.lesson.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.StaleElementReferenceException;
import ru.lesson.base.BasePage;
import ru.lesson.model.Product;
import ru.lesson.util.Money;

public class ProductCard extends BasePage {
    private static final String CARD_SELECTOR_TEMPLATE = "article.product-card[data-nm-id='%s']";
    private static final String ORDER_BUTTON_SELECTOR = "[data-testid='order-button']";
    private static final By NAME = By.cssSelector("[data-testid='productName']");
    private static final By PRICE = By.cssSelector("[data-testid='product-card-current-price']");
    private static final By ADD_BUTTON = By.cssSelector(".j-add-to-basket" + ORDER_BUTTON_SELECTOR);
    private static final int INITIAL_QUANTITY = 1;

    private final String article;
    private final By root;
    private final String rootSelector;

    public ProductCard(WebDriver driver, String article) {
        super(driver);
        this.article = article;
        rootSelector = CARD_SELECTOR_TEMPLATE.formatted(article);
        root = By.cssSelector(rootSelector);
    }

    public Product readProduct() {
        return wait.ignoring(StaleElementReferenceException.class).until(d -> {
            WebElement card = visible(root);
            scrollTo(card);
            String name = normalize(card.findElement(NAME).getText());
            String price = card.findElement(PRICE).getText();
            return name.isBlank() || price.isBlank() ? null : new Product(article, name, Money.rubles(price), INITIAL_QUANTITY);
        });
    }

    public boolean hasAddToCartButton() {
        return !visible(root).findElements(ADD_BUTTON).isEmpty();
    }

    public void addToCart() {
        click(By.cssSelector(rootSelector + " " + ORDER_BUTTON_SELECTOR));
    }
}
