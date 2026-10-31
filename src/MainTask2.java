import java.util.logging.Logger;

public class MainTask2 {
    private static final Logger logger = Logger.getLogger(MainTask2.class.getName());

    public static void main(String[] args) {
        Shape circle = new Circle(5, "Красный", "Чёрный");
        Shape rectangle = new Rectangle(4, 6, "Синий", "Белый");
        Shape triangle = new Triangle(3, 4, 5, "Зелёный", "Жёлтый");

        logger.info("Круг: " + circle.describe());
        logger.info("Прямоугольник: " + rectangle.describe());
        logger.info("Треугольник: " + triangle.describe());
    }
}
