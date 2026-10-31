public class Main {
    public static void main(String[] args) {
        Employee[] employees = new Employee[5];

        employees[0] = new Employee("Иванов Иван Иванович", "Инженер", "ivanov@mail.ru", "89231234567", 45000, 35);
        employees[1] = new Employee("Петров Пётр Петрович", "Менеджер", "petrov@mail.ru", "89167654321", 55000, 42);
        employees[2] = new Employee("Сидорова Анна Сергеевна", "Бухгалтер", "sidorova@mail.ru", "89091112233", 40000, 51);
        employees[3] = new Employee("Козлов Дмитрий Олегович", "Директор", "kozlov@mail.ru", "89264445566", 90000, 48);
        employees[4] = new Employee("Смирнова Елена Вячеславовна", "Аналитик", "smirnova@mail.ru", "89037778899", 60000, 29);

        System.out.println("Сотрудники старше 40 лет:");
        System.out.println("----------------------------");
        for (Employee emp : employees) {
            if (emp.getAge() > 40) {
                System.out.println(emp);
            }
        }

        Park park = new Park("Парк Горького");
        Park.Attraction rollerCoaster = park.new Attraction("Американские горки", "10:00 - 22:00", 350);
        Park.Attraction ferrisWheel = park.new Attraction("Колесо обозрения", "09:00 - 23:00", 200);
        Park.Attraction carousel = park.new Attraction("Карусель", "10:00 - 20:00", 150);

        System.out.println("Аттракционы парка:");
        System.out.println("----------------------------");
        System.out.println(rollerCoaster);
        System.out.println(ferrisWheel);
        System.out.println(carousel);
    }
}
