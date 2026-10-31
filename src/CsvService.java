import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvService {
    private static final String DELIMITER = ";";

    public void save(String filePath, AppData data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath, false), StandardCharsets.UTF_8))) {
            writer.write(String.join(DELIMITER, data.getHeader()));
            writer.newLine();
            for (int[] row : data.getData()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    if (i > 0) sb.append(DELIMITER);
                    sb.append(row[i]);
                }
                writer.write(sb.toString());
                writer.newLine();
            }
        }
    }

    public AppData load(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String headerLine = reader.readLine();
            String[] header = headerLine.split(DELIMITER);

            List<int[]> rows = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(DELIMITER);
                int[] row = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    row[i] = Integer.parseInt(parts[i].trim());
                }
                rows.add(row);
            }

            int[][] data = rows.toArray(new int[0][]);
            return new AppData(header, data);
        }
    }
}
