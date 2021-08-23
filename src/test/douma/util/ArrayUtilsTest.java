package douma.util;

import org.junit.Assert;
import org.junit.Test;

public class ArrayUtilsTest {
    @Test
    public void testMin() {
        double[] array = {14, 2.2, 2.1, 22, 13.4};
        double minimum = ArrayUtils.min(array);
        Assert.assertEquals(2.1, minimum, .001);

        array = new double[]{};
        minimum = ArrayUtils.min(array);
        Assert.assertEquals(Double.MAX_VALUE, minimum, .001);

    }

    @Test
    public void testZeroizeRowMinimumInMatrix() {
        double[][] matrix = {{1,2,3},{4,5,6}};
        matrix = ArrayUtils.zeroizeRowMinimumInMatrix(matrix);
        Assert.assertEquals(0, matrix[0][0], .001);
    }

    @Test
    public void testTranspose() {
        double[][] matrix = {{1,2,3},{4,5,6}};
        matrix = ArrayUtils.transpose(matrix);
        Assert.assertEquals(3, matrix.length);
        Assert.assertEquals(2, matrix[0].length);
        Assert.assertEquals(4, matrix[0][1], .001);
    }

    @Test
    public void testMarkZeroesWithStars() {
        double[][] matrix = {{1,2,3},{4,5,6}};
        int[][] markings = new int[2][3];
        markings = ArrayUtils.markZeroesWithStars(matrix, markings);
        Assert.assertEquals(0, markings[1][1]);

        matrix = new double[][]{{1, 0, 3}, {4, 0, 6}};
        markings = ArrayUtils.markZeroesWithStars(matrix, markings);
        Assert.assertEquals(1, markings[0][1]);
        Assert.assertEquals(0, markings[1][1]);

        markings = new int[3][3];
        matrix = new double[][]{{1,0,2}, {0,7.2, -3.1}, {4.9, -5.8, 0}};
        markings = ArrayUtils.markZeroesWithStars(matrix, markings);
        Assert.assertEquals(1, markings[0][1]);
        Assert.assertEquals(1, markings[1][0]);
        Assert.assertEquals(1, markings[2][2]);
        Assert.assertEquals(0, markings[1][1]);
        Assert.assertEquals(0, markings[0][0]);
        Assert.assertEquals(0, markings[2][1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCoverColumnsWithStarredZeroesShouldThrowException() {
        int[][] starredZeroes = {{0,0,1}, {2,0,0}, {1,2,0}};
        boolean[] coveredColumns = new boolean[2];
        coveredColumns = ArrayUtils.coverColumnsWithStarredZeroes(starredZeroes, coveredColumns);
    }

    @Test
    public void testCoverColumnsWithStarredZeroes() {
        int[][] starredZeroes = {{0,0,1}, {2,0,0}, {1,2,0}};
        boolean[] coveredColumns = new boolean[3];
        coveredColumns = ArrayUtils.coverColumnsWithStarredZeroes(starredZeroes, coveredColumns);
        Assert.assertTrue(coveredColumns[0]);
        Assert.assertFalse(coveredColumns[1]);
        Assert.assertTrue(coveredColumns[2]);
    }

    @Test
    public void testNumTrueValues() {
        boolean[] values = {false, false, false};
        int num = ArrayUtils.numTrueValues(values);
        Assert.assertEquals(0, num);

        values = new boolean[]{false, true, true, false};
        num = ArrayUtils.numTrueValues(values);
        Assert.assertEquals(2, num);
    }

    @Test
    public void testNumStarredValues() {
        int[] values = new int[5];
        int num = ArrayUtils.numStarred(values);
        Assert.assertEquals(0, num);

        values = new int[] {0, ArrayUtils.PRIME, ArrayUtils.STAR, ArrayUtils.STAR, 0, 0};
        num = ArrayUtils.numStarred(values);
        Assert.assertEquals(2, num);
    }

    @Test
    public void testHasStars() {
        int[] values = new int[5];
        boolean hasStar = ArrayUtils.hasStars(values);
        Assert.assertFalse(hasStar);

        values = new int[] {0, ArrayUtils.PRIME, ArrayUtils.STAR, ArrayUtils.STAR, 0, 0};
        hasStar = ArrayUtils.hasStars(values);
        Assert.assertTrue(hasStar);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetColumnFromArrayThrowsException() {
        int[][] array = {{0,0,1}, {2,0,0}, {1,2,0}};
        int[] column = ArrayUtils.getColumnFromArray(array, 3);
    }

    @Test
    public void testGetColumnFromArray() {
        int[][] array = {{0,0,1}, {2,0,0}, {1,2,0}};
        int[] column = ArrayUtils.getColumnFromArray(array, 1);
        Assert.assertEquals(0, column[0]);
        Assert.assertEquals(0, column[1]);
        Assert.assertEquals(2, column[2]);
    }
}
