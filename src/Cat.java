public class Cat extends Animal {
    private static int count = 0;
    private static final int MAX_RUN_DISTANCE = 200;
    private final int appetite;
    private boolean satiated = false;

    public Cat(String name, int appetite) {
        super(name);
        this.appetite = appetite;
        count++;
    }

    public static int getCatCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public boolean isSatiated() {
        return satiated;
    }

    public void eat(FoodBowl bowl) {
        if (bowl.consume(appetite)) {
            satiated = true;
        }
    }

    @Override
    public void run(int distance) {
        if (distance <= MAX_RUN_DISTANCE) {
            logger.info(name + " пробежал " + distance + " м.");
        } else {
            logger.info(name + " не может пробежать " + distance + " м. Максимум: " + MAX_RUN_DISTANCE + " м.");
        }
    }

    @Override
    public void swim(int distance) {
        logger.info(name + " не умеет плавать.");
    }
}
