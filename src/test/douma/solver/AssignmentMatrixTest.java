package douma.solver;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssignmentMatrixTest {
    @Test
    public void testInitializeFieldsAddressesLessThanNames() {
        List<String> addresses = new ArrayList<>(Arrays.asList("main", "elm"));
        List<String> names = new ArrayList<>(Arrays.asList("Moe", "Larry", "Curly"));
        AssignmentMatrix assignmentMatrix = new AssignmentMatrix(addresses, names);
        double[][] costMatrix = assignmentMatrix.getCostMatrix();
        int[][] markedZeroes = assignmentMatrix.getMarkedZeroes();
        boolean[] coveredRows = assignmentMatrix.getRowCoverings();
        boolean[] coveredCols = assignmentMatrix.getColCoverings();

        Assert.assertEquals(2, costMatrix.length);
        Assert.assertEquals(2, markedZeroes.length);
        Assert.assertEquals(2, coveredRows.length);
        Assert.assertEquals(3, coveredCols.length);
    }

    @Test
    public void testInitializeFieldsAddressesGreaterThanNames() {
        List<String> addresses = new ArrayList<>(Arrays.asList("main", "elm", "forest lane"));
        List<String> names = new ArrayList<>(Arrays.asList("Huck", "Tom"));
        AssignmentMatrix assignmentMatrix = new AssignmentMatrix(addresses, names);
        double[][] costMatrix = assignmentMatrix.getCostMatrix();
        int[][] markedZeroes = assignmentMatrix.getMarkedZeroes();
        boolean[] coveredRows = assignmentMatrix.getRowCoverings();
        boolean[] coveredCols = assignmentMatrix.getColCoverings();

        Assert.assertEquals(2, costMatrix.length);
        Assert.assertEquals(2, markedZeroes.length);
        Assert.assertEquals(2, coveredRows.length);
        Assert.assertEquals(3, coveredCols.length);
    }

}
