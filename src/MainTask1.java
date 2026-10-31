import java.util.logging.Logger;

public class MainTask1 {
    private static final Logger logger = Logger.getLogger(MainTask1.class.getName());

    public static void main(String[] args) {
        Dog dogBobik = new Dog("Бобик");
        Dog dogRex = new Dog("Рекс");
        Cat catMurka = new Cat("Мурка", 5);
        Cat catBarsik = new Cat("Барсик", 8);
        Cat catWhiskers = new Cat("Усатый", 3);

        dogBobik.run(150);
        dogBobik.run(600);
        dogBobik.swim(8);
        dogBobik.swim(15);

        catMurka.run(100);
        catMurka.run(250);
        catMurka.swim(5);

        logger.info("Всего животных: " + Animal.getAnimalCount());
        logger.info("Всего собак: " + Dog.getDogCount());
        logger.info("Всего котов: " + Cat.getCatCount());

        FoodBowl bowl = new FoodBowl(10);
        Cat[] cats = {catMurka, catBarsik, catWhiskers};

        for (Cat cat : cats) {
            cat.eat(bowl);
        }

        logger.info("Еды осталось в тарелке: " + bowl.getFood());
        for (Cat cat : cats) {
            logger.info(cat.getName() + " сыт: " + cat.isSatiated());
        }

        bowl.addFood(20);
        logger.info("После добавления еды: " + bowl.getFood());
    }
}
