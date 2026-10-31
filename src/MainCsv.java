import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class MainCsv {
    private static final Logger logger = Logger.getLogger(MainCsv.class.getName());
    private static final String FILE_PATH = "data.csv";

    public static void main(String[] args) throws IOException {
        CsvService csvService = new CsvService();

        String[] header = {"id", "age", "salary", "experience"};
        int[][] data = {
                {1, 25, 50000, 3},
                {2, 34, 75000, 10},
                {3, 42, 90000, 18},
                {4, 29, 60000, 5},
                {5, 51, 120000, 25}
        };

        AppData original = new AppData(header, data);
        csvService.save(FILE_PATH, original);
        logger.info("Данные сохранены в " + FILE_PATH);

        AppData loaded = csvService.load(FILE_PATH);
        logger.info("Загружен заголовок: " + Arrays.toString(loaded.getHeader()));
        for (int[] row : loaded.getData()) {
            logger.info("Строка: " + Arrays.toString(row));
        }
    }
}
