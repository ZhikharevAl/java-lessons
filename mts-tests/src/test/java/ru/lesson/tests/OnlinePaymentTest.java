package ru.lesson.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.lesson.base.BaseTest;
import ru.lesson.data.PaymentTestData;
import ru.lesson.pages.OnlinePaymentPage;
import ru.lesson.pages.PaymentWidgetPage;
import ru.lesson.pages.ServiceInfoPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OnlinePaymentTest extends BaseTest {
    private static final String SERVICE_INFO_URL =
            "https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/";

    private OnlinePaymentPage page;

    @BeforeEach
    void openPaymentPage() {
        page = new OnlinePaymentPage(driver).open();
    }

    @Test
    @DisplayName("Блок называется «Онлайн пополнение без комиссии»")
    void blockTitleIsCorrect() {
        assertEquals("Онлайн пополнение без комиссии", page.getBlockTitle(), "Неверный заголовок блока");
    }

    @Test
    @DisplayName("Все пять логотипов платёжных систем видимы и загружены")
    void paymentSystemLogosAreDisplayed() {
        List<String> expectedNames = List.of("Visa", "Verified By Visa", "MasterCard", "MasterCard Secure Code", "Белкарт");
        List<String> actualNames = page.getPartnerLogoNames();
        assertEquals(expectedNames.size(), actualNames.size(), "Неверное количество логотипов");
        assertTrue(actualNames.containsAll(expectedNames), "Неверный набор логотипов: " + actualNames);
        assertAll("Отображение и загрузка логотипов", expectedNames.stream().map(name -> () -> {
            assertTrue(page.isPartnerLogoDisplayed(name), "Не виден логотип " + name);
            assertTrue(page.isPartnerLogoLoaded(name), "Не загрузилось изображение " + name);
        }));
    }

    @Test
    @DisplayName("Ссылка «Подробнее о сервисе» открывает описание сервиса")
    void moreInfoLinkWorks() {
        assertEquals(SERVICE_INFO_URL, page.getMoreInfoHref(), "Неверный адрес ссылки");
        ServiceInfoPage infoPage = page.clickMoreInfo();
        assertAll("Страница описания сервиса",
                () -> assertEquals(SERVICE_INFO_URL, infoPage.getUrl(), "Неверный адрес страницы"),
                () -> assertEquals("Порядок оплаты и безопасность интернет платежей", infoPage.getTitle(),
                        "Неверное название страницы"),
                () -> assertEquals("Оплата банковской картой", infoPage.getPaymentHeading(),
                        "Не открылось содержимое страницы описания сервиса"));
    }

    @Test
    @DisplayName("«Продолжить» открывает оплату услуг связи с указанными номером и суммой")
    void continueButtonOpensPaymentPage() {
        page.selectConnectionService();
        assertEquals("Услуги связи", page.getSelectedService());
        page.fillConnectionForm(PaymentTestData.PHONE, PaymentTestData.AMOUNT, PaymentTestData.EMAIL);
        PaymentWidgetPage widget = page.clickContinue();
        assertAll("Данные в загруженном платёжном виджете",
                () -> assertEquals(PaymentTestData.FORMATTED_AMOUNT, widget.getAmount(), "Неверная сумма пополнения"),
                () -> assertTrue(widget.getDescription().contains("Услуги связи"), "Неверная услуга"),
                () -> assertTrue(widget.getDescription().replaceAll("\\s+", "").contains("Номер:375" + PaymentTestData.PHONE),
                        "В виджет передан неверный номер телефона"));
    }
}
