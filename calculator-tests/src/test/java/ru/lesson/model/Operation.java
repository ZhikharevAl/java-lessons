package ru.lesson.model;

public enum Operation {
    ADD("op_add"),
    SUBTRACT("op_sub"),
    MULTIPLY("op_mul"),
    DIVIDE("op_div");

    private final String buttonId;

    Operation(String buttonId) {
        this.buttonId = buttonId;
    }

    public String buttonId() {
        return buttonId;
    }
}
