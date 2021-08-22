package douma.solver;

import douma.util.ArrayUtils;
import douma.util.ScoreUtils;

import java.util.List;

/**
 * Matrix representation used in the assignment problem. In addition to operations associated
 * with two dimensional arrays, it allows operations used in the Munkres Assignment algorithm such
 * as covering rows and starring or priming zeroes.
 */
public class AssignmentMatrix {
    private double[][] costMatrix;
    private int[][] markedZeroes;
    private boolean[] rowCoverings;
    private boolean[] colCoverings;

    // The algorithm expects the column size to be greater than or equal to the row size. If there
    // are more addressed than drivers we transpose the matrix.
    private boolean isTransposed;

    public AssignmentMatrix(final List<String> addresses, final List<String> names) {
        initializeFields(addresses, names);
    }

    /*
     * The cost matrix, (costMatrix) is initialized so that the number of rows is less than or
     * equal to the number of columns. We initialize the matrix with the negative values returned
     * by ScoreUtils.suitabilityScore because the Munkres Assignment algorithm returns a minimum and
     * we require a maximum.
     *
     * The markedZeroes matrix is initialized to contain zeroes.
     *
     * The vectors rowCoverings and colCoverings are initialized so that all entries are false.
     */
    void initializeFields(final List<String> addresses, final List<String> names) {
        isTransposed = false;
        costMatrix = new double[addresses.size()][names.size()];
        for (int i = 0; i < addresses.size(); i++) {
            for (int j = 0; j < names.size(); j++) {
                costMatrix[i][j] = -1 * ScoreUtils.suitabilityScore(addresses.get(i), names.get(j));
            }
        }

        if (names.size() < addresses.size()) {
            isTransposed = true;
            costMatrix = ArrayUtils.transpose(costMatrix);
            markedZeroes = new int[names.size()][addresses.size()]; // initialize to all zeroes
            rowCoverings = new boolean[names.size()];
            colCoverings = new boolean[addresses.size()];
        } else {
            markedZeroes = new int[addresses.size()][names.size()]; // initialize to all zeroes
            rowCoverings = new boolean[addresses.size()];
            colCoverings = new boolean[names.size()];
        }
    }

    /**
     * Returns the number of assignments necessary for a solution to the assignment problem
     */
    public int getNumAssignmentsNecessaryForSolution() {
        return costMatrix.length;
    }

    /**
     * Subtracts the minimum value of each row from all values in the respective row
     *
     * Precondition: costMatrix must have been initialized
     */
    public void zeroizeRowMinimumInCostMatrix() {
        costMatrix = ArrayUtils.zeroizeRowMinimumInMatrix(costMatrix);
    }

    /**
     * Set the entries corresponding to unmarked zeroes in the costMatrix to 1 (represents *) in
     * the markedZeroes matrix
     */
    public void markZeroesWithStars() {
        markedZeroes = ArrayUtils.markZeroesWithStars(costMatrix, markedZeroes);
    }

    /**
     * Marks columns with starred zeroes as covered
     */
    public int coverColumnsWithStarredZero() {
        colCoverings = ArrayUtils.coverColumnsWithStarredZeroes(markedZeroes, colCoverings);
        return ArrayUtils.numTrueValues(colCoverings);
    }

    // Getters for testing

    double[][] getCostMatrix() {
        return costMatrix;
    }

    int[][] getMarkedZeroes() {
        return markedZeroes;
    }

    boolean[] getRowCoverings() {
        return rowCoverings;
    }

    boolean[] getColCoverings() {
        return colCoverings;
    }
}
