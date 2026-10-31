import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainLoginInput {
    private static final Logger logger = Logger.getLogger(MainLoginInput.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> logins = new ArrayList<>();

        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {
            logins.add(line);
        }

        List<String> fLogins = logins.stream()
                .filter(l -> l.startsWith("f"))
                .collect(Collectors.toList());

        logger.info("Логины на 'f': " + fLogins);
    }
}
