public class ArraySumCalculator {
    public int sum(String[][] array) throws MyArraySizeException, MyArrayDataException {
        if (array.length != 4) {
            throw new MyArraySizeException(array.length, array.length > 0 ? array[0].length : 0);
        }
        for (String[] row : array) {
            if (row.length != 4) {
                throw new MyArraySizeException(array.length, row.length);
            }
        }

        int total = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                try {
                    total += Integer.parseInt(array[i][j].trim());
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j, array[i][j]);
                }
            }
        }
        return total;
    }
}
