package douma.util;

import java.util.HashSet;

/**
 * Contains methods for operations on arrays
 */
public class ArrayUtils {

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
                    markMatrix[i][j] = 1;
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
    public boolean[] coverColumnsWithStarredZeroes(final int[][] starredZeroes, boolean[] coveredColumns) {
        if (starredZeroes[0].length != coveredColumns.length) {
            throw new IllegalArgumentException("The column size of starredZeroes must equal " +
                    "the length of coveredColumns");
        }

        for (int i = 0; i < starredZeroes.length; i++) {
            for (int j = 0; j < starredZeroes[0].length; j++) {
                if (starredZeroes[i][j] == 1) {
                    coveredColumns[j] = true;
                }
            }
        }
        return coveredColumns;
    }
}
