public class Student {
    private final String name;
    private final String gender;
    private final int age;

    public Student(String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return name + " (" + gender + ", " + age + " лет)";
    }
}
