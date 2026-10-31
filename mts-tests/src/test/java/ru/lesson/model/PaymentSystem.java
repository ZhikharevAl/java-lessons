package ru.lesson.model;

public enum PaymentSystem {
    VISA("visa-system.svg"),
    MASTERCARD("mastercard-system.svg"),
    BELKART("belkart-system.svg"),
    MAESTRO("maestro-system.svg"),
    MIR("mir-system-ru.svg");

    private final String iconFile;

    PaymentSystem(String iconFile) {
        this.iconFile = iconFile;
    }

    public String getIconFile() {
        return iconFile;
    }
}
