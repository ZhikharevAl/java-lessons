import java.util.ArrayList;

public class Box<T extends Fruit> {
    private final ArrayList<T> fruits = new ArrayList<>();

    public void add(T fruit) {
        fruits.add(fruit);
    }

    public float getWeight() {
        if (fruits.isEmpty()) {
            return 0.0f;
        }
        return fruits.size() * fruits.get(0).getWeight();
    }

    public boolean compare(Box<?> other) {
        return Math.abs(getWeight() - other.getWeight()) < 0.0001f;
    }

    public void transferTo(Box<T> other) {
        other.fruits.addAll(fruits);
        fruits.clear();
    }

    public int getCount() {
        return fruits.size();
    }
}
