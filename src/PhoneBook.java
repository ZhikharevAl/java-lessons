import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneBook {
    private final Map<String, List<String>> book = new HashMap<>();

    public void add(String lastName, String phone) {
        book.computeIfAbsent(lastName, k -> new ArrayList<>()).add(phone);
    }

    public List<String> get(String lastName) {
        return book.getOrDefault(lastName, List.of());
    }
}
