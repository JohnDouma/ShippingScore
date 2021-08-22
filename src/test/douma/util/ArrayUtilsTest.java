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
}
