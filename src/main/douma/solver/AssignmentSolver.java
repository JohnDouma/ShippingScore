package douma.solver;

import douma.util.Pair;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods to assign deliveries to drivers
 */
public class AssignmentSolver {
    private final List<String> addresses;
    private final List<String> names;
    private List<Pair<String, String>> assignments;
    private AssignmentMatrix assignmentMatrix;

    private static enum STATE {
        INIT,
        ZEROIZE_MINIMA,
        MARK_ZEROES,
        COVER_COLUMNS,
        DONE
    }

    public AssignmentSolver(final List<String> addresses, final List<String> names) {
        this.addresses = new ArrayList<>(addresses);
        this.names = new ArrayList<>(names);
        this.assignments = null;
    }

    /**
     * Runs the Munkres Assignment algorithm to determine the optimal set of assignments
     * that maximizes the utility
     *
     * See https://brc2.com/the-algorithm-workshop/ for details about the algorithm
     *
     * @return the sum of the suitability scores associated with each assignment
     */
    public double solve() {
        STATE state = STATE.INIT;
        while (state != STATE.DONE) {
            switch (state) {
                case INIT:
                    state = initializeMatrix();
                    break;
                case ZEROIZE_MINIMA:
                    state = zeroizeRowMinima();
                    break;
                case MARK_ZEROES:
                    state = markZeroesWithStars();
                    break;
                case COVER_COLUMNS:
                    state = coverColumnsWithMarkedZeroes();
                    break;
                case DONE:
                    //TODO
                    break;
                default:
                    throw new NotImplementedException();
            }
        }
        return 0.0;
    }

    /**
     * Returns a list of the assignments determined by the algorithm
     * @return null if called
     */
    public List<Pair<String, String>> getAssignments() {
        return assignments;
    }

    // state transition functions; package-private for testing

    STATE initializeMatrix() {
        assignmentMatrix = new AssignmentMatrix(addresses, names);
        return STATE.ZEROIZE_MINIMA;
    }

    STATE zeroizeRowMinima() {
        assignmentMatrix.zeroizeRowMinimumInCostMatrix();
        return STATE.MARK_ZEROES;
    }

    STATE markZeroesWithStars() {
        assignmentMatrix.markZeroesWithStars();
        return STATE.COVER_COLUMNS;
    }

    STATE coverColumnsWithMarkedZeroes() {
        assignmentMatrix.coverColumnsWithStarredZero();
        return STATE.DONE; // TODO
    }
}
