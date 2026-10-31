public class MyArraySizeException extends Exception {
    public MyArraySizeException(int rows, int cols) {
        super("Неверный размер массива: " + rows + "x" + cols + ". Ожидается 4x4.");
    }
}
