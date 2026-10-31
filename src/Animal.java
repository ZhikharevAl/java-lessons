import java.util.logging.Logger;

public abstract class Animal {
    protected static final Logger logger = Logger.getLogger(Animal.class.getName());
    private static int count = 0;
    protected String name;

    public Animal(String name) {
        this.name = name;
        count++;
    }

    public static int getAnimalCount() {
        return count;
    }

    public abstract void run(int distance);

    public abstract void swim(int distance);
}
