import java.util.logging.Logger;

public class MainExceptions {
    private static final Logger logger = Logger.getLogger(MainExceptions.class.getName());

    public static void main(String[] args) {
        ArraySumCalculator calculator = new ArraySumCalculator();

        String[][] validArray = {
                {"1",  "2",  "3",  "4"},
                {"5",  "6",  "7",  "8"},
                {"9",  "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        String[][] wrongSizeArray = {
                {"1", "2", "3"},
                {"4", "5", "6"},
                {"7", "8", "9"}
        };

        String[][] invalidDataArray = {
                {"1",  "2",   "3",  "4"},
                {"5",  "abc", "7",  "8"},
                {"9",  "10",  "11", "12"},
                {"13", "14",  "15", "16"}
        };

        try {
            int result = calculator.sum(validArray);
            logger.info("Сумма корректного массива: " + result);
        } catch (MyArraySizeException | MyArrayDataException e) {
            logger.severe(e.getMessage());
        }

        try {
            calculator.sum(wrongSizeArray);
        } catch (MyArraySizeException e) {
            logger.severe("MyArraySizeException: " + e.getMessage());
        } catch (MyArrayDataException e) {
            logger.severe("MyArrayDataException: " + e.getMessage());
        }

        try {
            calculator.sum(invalidDataArray);
        } catch (MyArraySizeException e) {
            logger.severe("MyArraySizeException: " + e.getMessage());
        } catch (MyArrayDataException e) {
            logger.severe("MyArrayDataException: " + e.getMessage());
        }
    }
}
