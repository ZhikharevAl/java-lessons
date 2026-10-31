import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class WordCounter {
    private static final Logger logger = Logger.getLogger(WordCounter.class.getName());

    public void analyze(String[] words) {
        Set<String> unique = new LinkedHashSet<>(Arrays.asList(words));

        Map<String, Integer> frequency = new LinkedHashMap<>();
        for (String word : words) {
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }

        logger.info("Уникальные слова: " + unique);
        for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
            logger.info("\"" + entry.getKey() + "\" — " + entry.getValue() + " раз(а)");
        }
    }
}
