public class MyArrayDataException extends Exception {
    public MyArrayDataException(int row, int col, String value) {
        super("Неверные данные в ячейке [" + row + "][" + col + "]: \"" + value + "\"");
    }
}
