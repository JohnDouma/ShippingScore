package douma.solver;

import douma.util.ArrayUtils;
import douma.util.Pair;
import douma.util.ScoreUtils;

import java.util.ArrayList;
import java.util.List;

import static douma.util.ArrayUtils.*;

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
    private List<String> addresses;
    private List<String> names;
    private boolean isTransposed;

    public AssignmentMatrix(final List<String> addresses, final List<String> names) {
        this.addresses = addresses;
        this.names = names;
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
            isTransposed = false;
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

    /**
     * Try to find additional assignments
     */
    public void increaseStarredZeroes() {
        boolean done = false;
        int rowIndexOfPrime = -1;
        int colIndexOfPrime = -1;
        // done will be set to true when either all zeroes are covered or there is an uncovered zero
        // with no starred zeroes in its row
        while (!done) {
            for (int i = 0; i < costMatrix.length; i++) {
                for (int j = 0; j < costMatrix[0].length; j++) {
                    if (costMatrix[i][j] == 0 && !rowCoverings[i] && !colCoverings[j]) {
                        markedZeroes[i][j] = ArrayUtils.PRIME;
                        if (ArrayUtils.hasStars(markedZeroes[i])) {
                            int indexOfStar = ArrayUtils.findIndexOf(markedZeroes[i], STAR);
                            rowCoverings[i] = true;
                            colCoverings[indexOfStar] = false;
                        } else {
                            rowIndexOfPrime = i;
                            colIndexOfPrime = j;
                            done = true;
                            break;
                        }
                    }
                }
            }

            if (done) {
                // We have primed zero in row without starred zero
                List<Pair<Integer, Integer>> sequenceOfPrimedAndStarredZeroes = new ArrayList<>();
                sequenceOfPrimedAndStarredZeroes.add(new Pair<>(rowIndexOfPrime, colIndexOfPrime));
                int[] column = ArrayUtils.getColumnFromArray(markedZeroes, colIndexOfPrime);
                rowIndexOfPrime = ArrayUtils.findIndexOf(column, STAR);
                while (rowIndexOfPrime != -1) {
                    sequenceOfPrimedAndStarredZeroes.add(new Pair<>(rowIndexOfPrime, colIndexOfPrime));
                    colIndexOfPrime = ArrayUtils.findIndexOf(markedZeroes[rowIndexOfPrime], PRIME);
                    sequenceOfPrimedAndStarredZeroes.add(new Pair<>(rowIndexOfPrime, colIndexOfPrime));
                    column = ArrayUtils.getColumnFromArray(markedZeroes, colIndexOfPrime);
                    rowIndexOfPrime = ArrayUtils.findIndexOf(column, STAR);
                }
                for (Pair<Integer, Integer> index: sequenceOfPrimedAndStarredZeroes) {
                    if (markedZeroes[index.first][index.second] == STAR) {
                        markedZeroes[index.first][index.second] = 0;
                    } else if (markedZeroes[index.first][index.second] == PRIME) {
                        markedZeroes[index.first][index.second] = STAR;
                    }
                }
                // Uncover rows and columns
                rowCoverings = new boolean[costMatrix.length];
                colCoverings = new boolean[costMatrix[0].length];
            } else {
                // add minimum uncovered value to each covered row of costMatrix and subtract
                // minimum uncovered value from each uncovered column
                double minimum = findMinimumUncoveredValue();
                for (int i = 0; i < rowCoverings.length; i++) {
                    if (rowCoverings[i]) {
                        costMatrix = ArrayUtils.addToRow(costMatrix, i, minimum);
                    }
                }
                for (int i = 0; i < colCoverings.length; i++) {
                    if (!colCoverings[i]) {
                        costMatrix = ArrayUtils.subtractFromColumn(costMatrix, i, minimum);
                    }
                }
            }
        }
    }

    /**
     * Return list of assignments of addresses to drivers
     */
    public List<Pair<String, String>> getAssignments() {
        List<Pair<String, String>> retList = new ArrayList<>();
        for (int i = 0; i < markedZeroes.length; i++) {
            for (int j = 0; j < markedZeroes[0].length; j++) {
                if (markedZeroes[i][j] == STAR) {
                    retList.add(isTransposed ? new Pair<>(addresses.get(j), names.get(i)) : new Pair<>(addresses.get(i), names.get(j)));
                }
            }
        }
        return retList;
    }

    private double findMinimumUncoveredValue() {
        double minimum = Double.MAX_VALUE;
        for (int i = 0; i < costMatrix.length; i++) {
            for (int j = 0; j < costMatrix[0].length; j++) {
                if (!rowCoverings[i] && !colCoverings[j] && costMatrix[i][j] < minimum) {
                    minimum = costMatrix[i][j];
                }
            }
        }
        return minimum;
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
