public class FoodBowl {
    private int food;

    public FoodBowl(int food) {
        this.food = food;
    }

    public boolean consume(int amount) {
        if (food >= amount) {
            food -= amount;
            return true;
        }
        return false;
    }

    public void addFood(int amount) {
        food += amount;
    }

    public int getFood() {
        return food;
    }
}
