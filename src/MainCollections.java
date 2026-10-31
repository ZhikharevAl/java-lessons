import java.util.List;
import java.util.logging.Logger;

public class MainCollections {
    private static final Logger logger = Logger.getLogger(MainCollections.class.getName());

    public static void main(String[] args) {
        String[] words = {
                "кот", "пёс", "кот", "птица", "кот",
                "пёс", "рыба", "птица", "змея", "кот",
                "рыба", "пёс", "хомяк", "змея", "кот",
                "птица", "хомяк", "рыба", "пёс", "змея"
        };

        WordCounter counter = new WordCounter();
        counter.analyze(words);

        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Иванов", "+7 900 111-22-33");
        phoneBook.add("Петров", "+7 900 444-55-66");
        phoneBook.add("Иванов", "+7 900 777-88-99");
        phoneBook.add("Сидоров", "+7 900 000-11-22");
        phoneBook.add("Петров", "+7 900 333-44-55");

        String[] lookups = {"Иванов", "Петров", "Сидоров", "Козлов"};
        for (String name : lookups) {
            List<String> phones = phoneBook.get(name);
            if (phones.isEmpty()) {
                logger.info(name + ": не найден");
            } else {
                logger.info(name + ": " + phones);
            }
        }
    }
}
