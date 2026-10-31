public interface Shape {
    double perimeter();

    double area();

    String getFillColor();

    String getBorderColor();

    default String describe() {
        return String.format(
                "Периметр: %.2f, Площадь: %.2f, Цвет заливки: %s, Цвет границы: %s",
                perimeter(), area(), getFillColor(), getBorderColor()
        );
    }
}
