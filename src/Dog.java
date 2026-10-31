public class Dog extends Animal {
    private static int count = 0;
    private static final int MAX_RUN_DISTANCE = 500;
    private static final int MAX_SWIM_DISTANCE = 10;

    public Dog(String name) {
        super(name);
        count++;
    }

    public static int getDogCount() {
        return count;
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
        if (distance <= MAX_SWIM_DISTANCE) {
            logger.info(name + " проплыл " + distance + " м.");
        } else {
            logger.info(name + " не может проплыть " + distance + " м. Максимум: " + MAX_SWIM_DISTANCE + " м.");
        }
    }
}
