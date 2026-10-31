import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainStreams {
    private static final Logger logger = Logger.getLogger(MainStreams.class.getName());

    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
    }

    private static void task1() {
        List<Integer> numbers = new Random().ints(20, 1, 101)
                .boxed()
                .collect(Collectors.toList());
        long evenCount = numbers.stream()
                .filter(n -> n % 2 == 0)
                .count();
        logger.info("Числа: " + numbers);
        logger.info("Количество чётных: " + evenCount);
    }

    private static void task2() {
        List<String> strings = Arrays.asList("Highload", "High", "Load", "Highload");

        long highCount = strings.stream()
                .filter("High"::equals)
                .count();
        logger.info("Количество вхождений 'High': " + highCount);

        Object first = strings.stream().findFirst().orElse("0");
        logger.info("Первый элемент: " + first);

        Object last = strings.stream().reduce((a, b) -> b).orElse("0");
        logger.info("Последний элемент: " + last);
    }

    private static void task3() {
        List<String> fStrings = Arrays.asList("f10", "f15", "f2", "f4", "f4");
        String[] sorted = fStrings.stream()
                .sorted(Comparator.comparingInt(s -> Integer.parseInt(s.substring(1))))
                .toArray(String[]::new);
        logger.info("Отсортированный массив: " + Arrays.toString(sorted));
    }

    private static void task4() {
        List<Student> students = Arrays.asList(
                new Student("Иван Петров",     "Мужской",   20),
                new Student("Анна Сидорова",   "Женский",   22),
                new Student("Алексей Козлов",  "Мужской",   25),
                new Student("Мария Новикова",  "Женский",   19),
                new Student("Дмитрий Орлов",   "Мужской",   17),
                new Student("Сергей Волков",   "Мужской",   28),
                new Student("Елена Морозова",  "Женский",   21),
                new Student("Никита Зайцев",   "Мужской",   23)
        );

        double avgAge = students.stream()
                .filter(s -> s.getGender().equals("Мужской"))
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
        logger.info("Средний возраст студентов мужского пола: " + avgAge);

        List<Student> conscripts = students.stream()
                .filter(s -> s.getGender().equals("Мужской"))
                .filter(s -> s.getAge() >= 18 && s.getAge() <= 27)
                .collect(Collectors.toList());
        logger.info("Студентам грозит повестка: " + conscripts);
    }
}
