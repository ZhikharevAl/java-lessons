public class Park {
    private String name;

    public Park(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public class Attraction {
        private String attractionName;
        private String workingHours;
        private double price;

        public Attraction(String attractionName, String workingHours, double price) {
            this.attractionName = attractionName;
            this.workingHours = workingHours;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Парк: " + name + "\n" +
                   "Аттракцион: " + attractionName + "\n" +
                   "Время работы: " + workingHours + "\n" +
                   "Стоимость: " + price + " руб." + "\n" +
                   "----------------------------";
        }
    }
}
