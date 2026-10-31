public class Employee {
    private String fullName;
    private String position;
    private String email;
    private String phone;
    private double salary;
    private int age;

    public Employee(String fullName, String position, String email, String phone, double salary, int age) {
        this.fullName = fullName;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "ФИО: " + fullName + "\n" +
               "Должность: " + position + "\n" +
               "Email: " + email + "\n" +
               "Телефон: " + phone + "\n" +
               "Зарплата: " + salary + "\n" +
               "Возраст: " + age + "\n" +
               "----------------------------";
    }
}
