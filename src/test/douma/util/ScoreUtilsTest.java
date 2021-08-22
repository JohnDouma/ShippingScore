package douma.util;

import org.junit.Assert;
import org.junit.Test;

public class ScoreUtilsTest {

    @Test
    public void testScoreUtils() throws Exception {
        // even length address no common factors
        String address = "";
        String name = "Roger";
        double score = ScoreUtils.suitabilityScore(address, name);
        Assert.assertEquals(3.0, score, .001);

        // odd length address no common factors
        address = "OddRoad";
        name = "Roger";
        score = ScoreUtils.suitabilityScore(address, name);
        Assert.assertEquals(3.0, score, .001);

        // odd length address with common factor
        address = "OddRoad";
        name = "Kenneth";
        score = ScoreUtils.suitabilityScore(address, name);
        Assert.assertEquals(7.5, score, .001);

        // even length address with common factor
        address = "MainStreet";
        name = "Manny";
        score = ScoreUtils.suitabilityScore(address, name);
        Assert.assertEquals(2.25, score, .001);
    }
}