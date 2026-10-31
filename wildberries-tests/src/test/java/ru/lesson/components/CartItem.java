package ru.lesson.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.lesson.base.BasePage;
import ru.lesson.config.SiteConstants;
import ru.lesson.locators.CartLocators;
import ru.lesson.model.Product;
import ru.lesson.util.Money;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CartItem extends BasePage {
    private static final Pattern ARTICLE = Pattern.compile(Pattern.quote(SiteConstants.CATALOG_PATH) + "([0-9]+)/");
    private static final By TITLE = By.cssSelector("a[data-search='true']");
    private static final By NAME = By.cssSelector("[class*='goodInfoName--']");
    private static final By QUANTITY = By.cssSelector(".mo-input-count input");
    private static final By PRICE = By.cssSelector("[class*='listItemPrice--'] h4");
    private static final String REMOVE_BUTTON_SELECTOR = " [class*='basketItemControls--'] > button:last-child";
    private final By root;
    private final String rootSelector;

    public CartItem(WebDriver driver, String basketItemId) {
        super(driver);
        rootSelector = CartLocators.ITEM_SELECTOR + "[data-basket-item-id='" + basketItemId + "']";
        root = By.cssSelector(rootSelector);
    }

    public Product readProduct() {
        return wait.ignoring(StaleElementReferenceException.class).until(d -> {
            WebElement item = visible(root);
            scrollTo(item);
            WebElement title = item.findElement(TITLE);
            String name = normalize(title.findElement(NAME).getText());
            String quantity = item.findElement(QUANTITY).getDomProperty("value");
            String price = item.findElements(PRICE).stream()
                    .filter(WebElement::isDisplayed).map(WebElement::getText).filter(text -> !text.isBlank())
                    .findFirst().orElse("");
            if (name.isBlank() || quantity.isBlank() || price.isBlank()) {
                return null;
            }
            return new Product(articleFrom(title.getAttribute("href")), name, Money.rubles(price), Integer.parseInt(quantity));
        });
    }

    public String getArticle() {
        return articleFrom(visible(root).findElement(TITLE).getAttribute("href"));
    }

    private String articleFrom(String href) {
        Matcher article = ARTICLE.matcher(href);
        if (!article.find()) {
            throw new IllegalStateException("Не найден артикул товара в корзине: " + href);
        }
        return article.group(1);
    }

    public void remove() {
        // В проверенной версии интерфейса вторая кнопка — корзина удаления, первая — избранное.
        By remove = By.cssSelector(rootSelector + REMOVE_BUTTON_SELECTOR);
        click(remove);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(root));
    }
}
