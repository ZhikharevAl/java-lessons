package ru.lesson.model;

public enum PaymentService {
    CONNECTION("Услуги связи", "pay-connection"),
    INTERNET("Домашний интернет", "pay-internet"),
    INSTALMENT("Рассрочка", "pay-instalment"),
    ARREARS("Задолженность", "pay-arrears");

    private final String title;
    private final String formId;

    PaymentService(String title, String formId) {
        this.title = title;
        this.formId = formId;
    }

    public String getTitle() {
        return title;
    }

    public String getFormId() {
        return formId;
    }

    @Override
    public String toString() {
        return title;
    }
}
