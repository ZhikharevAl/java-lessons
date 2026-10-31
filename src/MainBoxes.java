import java.util.logging.Logger;

public class MainBoxes {
    private static final Logger logger = Logger.getLogger(MainBoxes.class.getName());

    public static void main(String[] args) {
        Box<Apple> appleBox1 = new Box<>();
        Box<Apple> appleBox2 = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        for (int i = 0; i < 5; i++) {
            appleBox1.add(new Apple());
        }
        for (int i = 0; i < 10; i++) {
            orangeBox.add(new Orange());
        }

        logger.info("Яблок в коробке 1: " + appleBox1.getCount() + ", вес: " + appleBox1.getWeight());
        logger.info("Апельсинов в коробке: " + orangeBox.getCount() + ", вес: " + orangeBox.getWeight());
        logger.info("Коробки равны по весу: " + appleBox1.compare(orangeBox));

        appleBox1.transferTo(appleBox2);
        logger.info("После пересыпания — в коробке 1: " + appleBox1.getCount()
                + ", в коробке 2: " + appleBox2.getCount() + ", вес: " + appleBox2.getWeight());
    }
}
