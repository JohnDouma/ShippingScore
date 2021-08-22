package douma.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Collection of static functions to compute suitability scores between addresses and drivers
 */
public class ScoreUtils {

    private static final Character[] VOWELS = new Character[] { 'a', 'e', 'i', 'o', 'u' };
    private static final Character[] CONSONANTS = new Character[] { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l',
            'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z' };

    private static final Set<Character> VOWEL_SET = new HashSet<>(Arrays.asList(VOWELS));
    private static final Set<Character> CONSONANT_SET = new HashSet<>(Arrays.asList(CONSONANTS));

    /**
     * Returns suitability score for assignment of driver to address
     *      If the length of the name is even the score will be the number of vowels contained
     *      in the name multiplied by 1.5, otherwise the score will be the number of consonants in name.
     *      Note: if the length of address and the length of name have any common factors other than 1
     *      the score will be multiplied by 1.5
     *
     * @param address - String representing address to which package must be shipped
     * @param name = String representing name of driver that will deliver package
     */
    public static double suitabilityScore(String address, String name) {
        double score = 0.0;
        if (isEvenLength(address)) {
            score += (1.5*numVowels(name));
        } else {
            score += numConsonants(name);
        }
        if (hasCommonFactor(address.length(), name.length())) {
            score *= 1.5;
        }
        return score;
    }

    /*
     * Returns true if the length of the input string is even, false otherwise.
     */
    private static boolean isEvenLength(String str) {
        return str == null || (str.length() % 2) == 0;
    }

    /*
     * Returns number of consonants in input string
     */
    private static int numConsonants(String str) {
        if (str == null) return 0;
        String lower = str.toLowerCase();
        int numConstants = 0;
        for (int i = 0; i < lower.length(); i++) {
            if (CONSONANT_SET.contains(lower.charAt(i))) {
                numConstants++;
            }
        }
        return numConstants;
    }

    /*
     * Returns number of vowels in input string
     */
    private static int numVowels(String str) {
        if (str == null) return 0;
        String lower = str.toLowerCase();
        int numVowels = 0;
        for (int i = 0; i < lower.length(); i++) {
            if (VOWEL_SET.contains(lower.charAt(i))) {
                numVowels++;
            }
        }
        return numVowels;
    }

    /*
     * Returns true if the two integers have any common factors other than 1
     */
    private static boolean hasCommonFactor(int a, int b) {
        int min = Math.min(a, b);
        for (int i = 2; i <= min; i++) {
            if (a%i == 0 && b%i == 0) {
                return true;
            }
        }
        return false;
    }
}
