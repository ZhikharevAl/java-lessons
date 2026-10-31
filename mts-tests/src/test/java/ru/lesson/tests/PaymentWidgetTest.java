package ru.lesson.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.lesson.base.BaseTest;
import ru.lesson.data.PaymentTestData;
import ru.lesson.model.PaymentSystem;
import ru.lesson.pages.OnlinePaymentPage;
import ru.lesson.pages.PaymentWidgetPage;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentWidgetTest extends BaseTest {
    private PaymentWidgetPage widget;

    @BeforeEach
    void openPaymentWidget() {
        OnlinePaymentPage page = new OnlinePaymentPage(driver).open();
        page.selectConnectionService();
        page.fillConnectionForm(PaymentTestData.PHONE, PaymentTestData.AMOUNT, PaymentTestData.EMAIL);
        widget = page.clickContinue();
    }

    @Test
    @DisplayName("Окно оплаты содержит верные сумму, номер, надписи пустых полей карты и иконки")
    void connectionPaymentWidgetDisplaysCorrectDataAndCardForm() {
        assertAll("Окно оплаты услуг связи",
                () -> assertEquals(PaymentTestData.FORMATTED_AMOUNT, widget.getAmount(), "Неверная сумма"),
                () -> assertEquals("Оплатить " + PaymentTestData.FORMATTED_AMOUNT, widget.getPayButtonText(),
                        "Неверная сумма на кнопке оплаты"),
                () -> assertEquals("Оплата:УслугисвязиНомер:375" + PaymentTestData.PHONE,
                        widget.getDescription().replaceAll("\\s+", ""), "Неверная услуга или номер телефона"),
                () -> assertEquals(List.of("", "", "", ""), widget.getCardFieldValues(),
                        "Реквизиты карты должны быть незаполненными"),
                () -> assertEquals(List.of("Номер карты", "Срок действия", "CVC", "Имя и фамилия на карте"),
                        widget.getCardFieldLabels(), "Неверные надписи в полях карты"),
                () -> assertEquals("ММ / ГГ", widget.getExpirationPlaceholder(), "Неверная подсказка срока действия"),
                () -> assertAll("Иконки платёжных систем", Arrays.stream(PaymentSystem.values()).map(system -> () -> {
                    assertTrue(widget.isPaymentSystemIconDisplayed(system), "Не видна иконка " + system);
                    assertTrue(widget.isPaymentSystemIconLoaded(system), "Не загрузилась иконка " + system);
                })));
    }
}
