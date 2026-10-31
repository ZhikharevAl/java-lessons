package ru.lesson.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.lesson.base.BaseTest;
import ru.lesson.model.PaymentService;
import ru.lesson.pages.OnlinePaymentPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentFormTest extends BaseTest {
    private OnlinePaymentPage page;

    @BeforeEach
    void openPaymentPage() {
        page = new OnlinePaymentPage(driver).open();
    }

    @ParameterizedTest(name = "{0}: надписи в пустых полях")
    @CsvSource({
            "CONNECTION, Номер телефона",
            "INTERNET, Номер абонента",
            "INSTALMENT, Номер счета на 44",
            "ARREARS, Номер счета на 2073"
    })
    @DisplayName("Незаполненные поля всех четырёх вариантов оплаты содержат ожидаемые надписи")
    void emptyFieldsHaveCorrectPlaceholders(PaymentService service, String firstPlaceholder) {
        page.selectService(service);
        assertAll("Форма: " + service.getTitle(),
                () -> assertEquals(service.getTitle(), page.getSelectedService(), "Выбрана неверная услуга"),
                () -> assertEquals(List.of("", "", ""), page.getVisibleFormValues(), "Поля должны быть пустыми"),
                () -> assertEquals(List.of(firstPlaceholder, "Сумма", "E-mail для отправки чека"),
                        page.getVisibleFormPlaceholders(), "Неверные надписи в полях формы"));
    }
}
