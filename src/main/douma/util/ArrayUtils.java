package douma.util;

import java.util.HashSet;

/**
 * Contains methods for operations on arrays
 */
public class ArrayUtils {

    public static int STAR = 1;
    public static int PRIME = 2;

    /**
     * Finds the minimum value in an array of doubles
     * @param array non-null array of doubles
     *
     * @return minimum value in the array, Double.MAX_VALUE if the array is empty
     * @throws NullPointerException if array is null
     */
    public static double min(double[] array) {
        double minimum = Double.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < minimum) {
                minimum = array[i];
            }
        }
        return minimum;
    }

    /**
     * Subtracts the minimum value of each row from all values in the respective row
     *
     * Precondition: matrix not null
     */
    public static double[][] zeroizeRowMinimumInMatrix(double[][] matrix) {
        double minimum = 0;
        for (int i = 0; i < matrix.length; i++) {
            minimum = ArrayUtils.min(matrix[i]);
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] -= minimum;
            }
        }

        return matrix;
    }

    /*
     * Returns the transpose of the input matrix
     *
     * @param matrix - non-null double[][]
     */
    public static double[][] transpose(double[][] matrix) {
        double[][] tempMatrix = matrix;
        int m = matrix.length;
        int n = matrix[0].length;

        matrix = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = tempMatrix[j][i];
            }
        }

        return matrix;
    }

    /**
     * Marks each unmarked zero in an uncovered row and column by setting the associated element in the markMatrix
     * to 1 (See https://brc2.com/the-algorithm-workshop/) to see why this is called a star
     *
     * @param matrix non-null contains doubles, some of which may be 0
     * @param markMatrix non-null keeps track of the markings
     *
     * @return new marking matrix with marked zeroes
     */
    public static int[][] markZeroesWithStars(final double[][] matrix, int[][] markMatrix) {
        HashSet<Integer> markedRows = new HashSet<>();
        HashSet<Integer> markedCols = new HashSet<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0 && !markedRows.contains(i) && !markedCols.contains(j)) {
                    markMatrix[i][j] = STAR;
                    markedRows.add(i);
                    markedCols.add(j);
                }
            }
        }

        return markMatrix;
    }

    /**
     * Marks columns as covered if they contain starred zeroes
     *
     * @param starredZeroes non-null array of integers
     * @param coveredColumns non-null boolean array whose size must be equal to the column size of
     *                       starredZeroes
     * @return boolean array whose values are true for covered columns, false otherwise
     * @throws IllegalArgumentException if the column length of starredZeroes is not equal to the length
     *         of coveredColumns
     */
    public static boolean[] coverColumnsWithStarredZeroes(final int[][] starredZeroes, boolean[] coveredColumns) {
        if (starredZeroes[0].length != coveredColumns.length) {
            throw new IllegalArgumentException("The column size of starredZeroes must equal " +
                    "the length of coveredColumns");
        }

        for (int i = 0; i < starredZeroes.length; i++) {
            for (int j = 0; j < starredZeroes[0].length; j++) {
                if (starredZeroes[i][j] == STAR) {
                    coveredColumns[j] = true;
                }
            }
        }
        return coveredColumns;
    }

    /**
     * Return the number of <code>true</code></> elements in a boolean array
     * @param array non-null array of boolean values
     */
    public static int numTrueValues(final boolean[] array) {
        int numTrue = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i]) {
                numTrue++;
            }
        }
        return numTrue;
    }

    /**
     * Return number of entries in an integer array equal to STAR. The humber 1 represents a star
     * in accordance with the Munkres Assignment algorithm. See https://brc2.com/the-algorithm-workshop/
     * for details.
     *
     * @param array non-null integer array
     *
     * @return number of entries equal to STAR
     */
    public static int numStarred(final int[] array) {
        int numStars = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == STAR) {
                numStars++;
            }
        }
        return numStars;
    }

    /**
     * Returns, <code>true</code> if the array has any elements equal to START, <code>false</code> otherwise
     * @param array non-null array of integers
     */
    public static boolean hasStars(final int[] array) {
        return numStarred(array) > 0;
    }

    /**
     * Returns the index of the first instance of <code>value</code> found in the input array
     * @param array non-null integer array
     *
     * @return index of first STAR found, -1 if not found
     */
    public static int findIndexOf(int[] array, int value) {
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Extracts
     * @param array non-null integer array
     * @param colNumber column number to retrieve
     * @throws IndexOutOfBoundsException if colNumber is greater than the number of columns of array
     *
     */
    public static int[] getColumnFromArray(final int[][] array, int colNumber) {
        int[] retArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            retArray[i] = array[i][colNumber];
        }
        return retArray;
    }

    /**
     * Subtract value from each entry in the given column of the input array
     *
     * @param array non-null array of doubles
     * @param colNumber column from which to subtract values
     * @param value number to be subtracted
     *
     * @throws IndexOutOfBoundsException if colNumber is invalid
     */
    public static double[][] subtractFromColumn(double[][] array, int colNumber, double value) {
        for (int i = 0; i < array.length; i++) {
            array[i][colNumber] -= value;
        }

        return array;
    }

    /**
     * Add value to each entry in the given row of the input array
     *
     * @param array non-null array of doubles
     * @param rowNumber row to which to add values
     * @param value number to be added
     *
     * @throws IndexOutOfBoundsException if colNumber is invalid
     */
    public static double[][] addToRow(double[][] array, int rowNumber, double value) {
        for (int i = 0; i < array[0].length; i++) {
            array[rowNumber][i] += value;
        }
        return array;
    }
}
