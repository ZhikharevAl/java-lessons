import java.util.Arrays;

public class Homework {

    public static void main(String[] args) {
        System.out.println("Задание 1: " + isSumInRange(5, 7)); // true

        System.out.print("Задание 2: ");
        printNumberSign(0);

        System.out.println("Задание 3: " + isNegative(-15)); // true

        System.out.println("Задание 4:");
        printStringMultipleTimes("Java", 3);

        System.out.println("Задание 5 (2024): " + isLeapYear(2024)); // true
        System.out.println("Задание 5 (1900): " + isLeapYear(1900)); // false

        System.out.println("Задание 6:");
        invertBinaryArray();

        System.out.println("Задание 7:");
        fillArrayUpTo100();

        System.out.println("Задание 8:");
        multiplyLessThanSix();

        System.out.println("Задание 9:");
        fillMatrixDiagonals(5);

        System.out.println("Задание 10: " + Arrays.toString(createArray(5, 42)));
    }

    public static boolean isSumInRange(int a, int b) {
        int sum = a + b;
        return sum >= 10 && sum <= 20;
    }

    public static void printNumberSign(int number) {
        if (number >= 0) {
            System.out.println("Положительное число");
        } else {
            System.out.println("Отрицательное число");
        }
    }

    public static boolean isNegative(int number) {
        return number < 0;
    }

    public static void printStringMultipleTimes(String str, int count) {
        for (int i = 0; i < count; i++) {
            System.out.println(str);
        }
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static void invertBinaryArray() {
        int[] arr = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                arr[i] = 1;
            } else {
                arr[i] = 0;
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void fillArrayUpTo100() {
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void multiplyLessThanSix() {
        int[] arr = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 6) {
                arr[i] *= 2;
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void fillMatrixDiagonals(int size) {
        int[][] matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            matrix[i][i] = 1;
            matrix[i][size - 1 - i] = 1;
        }

        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static int[] createArray(int len, int initialValue) {
        int[] result = new int[len];
        Arrays.fill(result, initialValue);
        return result;
    }
}
