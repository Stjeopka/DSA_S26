package de.unistuttgart.dsass2026.ex09.p4;

/**
 * Boyer–Moore string matching.
 *
 * <p>This class combines the two classic Boyer–Moore heuristics:
 * <ul>
 *   <li><b>bad-character rule</b>, driven by the {@code last} table, and</li>
 *   <li><b>(strong) good-suffix rule</b>, driven by the {@code shift} table
 *       (with {@code suffix} holding the border positions used to build it).</li>
 * </ul>
 *
 *
 * <p><b>Alphabet assumption:</b> characters are treated as values in
 * {@code 0 .. ALPHABET_SIZE-1} (extended ASCII). Patterns containing code
 * points above 255 are out of scope for this exercise.
 */
public class BoyerMoore {

    /**
     * Size of the alphabet (extended ASCII).
     */
    private static final int ALPHABET_SIZE = 256;

    /**
     * The pattern being searched for, as a character array.
     */
    private final char[] pattern;

    /**
     * Pattern length (cached for convenience).
     */
    private final int patternLength;

    /**
     * Bad-character table, indexed by character value. Length {@code ALPHABET_SIZE}.
     * Filled by {@link #initLast()}.
     */
    private final int[] last;

    /**
     * Good-suffix shift table, indexed by pattern position. Length {@code m + 1}.
     * Filled by {@link #bmShiftSuffix()}.
     */
    private final int[] shift;

    /**
     * Border-position helper table, indexed by pattern position. Length {@code m + 1}.
     * Filled by {@link #bmShiftSuffix()} and used to compute {@code shift}.
     */
    private final int[] suffix;

    /**
     * Builds a matcher for {@code pattern} and runs all preprocessing.
     *
     * @param pattern the (non-null) pattern to search for; may be empty
     */
    public BoyerMoore(String pattern) {
        this.pattern = pattern.toCharArray();
        this.patternLength = this.pattern.length;
        this.last = new int[ALPHABET_SIZE];
        this.shift = new int[patternLength + 1];
        this.suffix = new int[patternLength + 1];
        initLast();
        bmShiftSuffix();
    }

    /**
     * Fills the bad-character table {@link #last}.
     */
    private void initLast() {
        for (int i = 0; i < last.length; i++) {
            last[i] = -1;
        }

        for (int i = 0; i < patternLength; i++) {
            last[pattern[i]] = i;
        }
    }

    /**
     * Fills the good-suffix tables {@link #shift} and {@link #suffix}.
     */
    private void bmShiftSuffix() {
        int m = patternLength;
        int i = m;
        int j = m + 1;
        suffix[i] = j;

        while (i > 0) {
            while (j <= m && pattern[i - 1] != pattern[j - 1]) {
                if (shift[j] == 0) {
                    shift[j] = j - i;
                }
                j = suffix[j];
            }
            i--;
            j--;
            suffix[i] = j;
        }

        // Fill the remaining shifts with the next widest border.
        j = suffix[0];
        for (i = 0; i <= m; i++) {
            if (shift[i] == 0) {
                shift[i] = j;
            }
            if (i == j) {
                j = suffix[j];
            }
        }
    }

    /**
     * Returns the index of the first occurrence of the pattern in {@code text},
     * or {@code -1} if the pattern does not occur. An empty pattern matches at 0.
     *
     * @param text the (non-null) text to search in
     * @return the 0-based index of the first match, or {@code -1}
     */
    public int search(String text) {
        char[] t = text.toCharArray();
        int n = t.length;
        if (patternLength == 0) {
            return 0;
        }

        int s = 0;
        while (s <= n - patternLength) {
            int j = patternLength - 1;
            while (j >= 0 && pattern[j] == t[s + j]) {
                j--;
            }
            if (j < 0) {
                return s;
            }
            int badCharShift = j - last[t[s + j]];
            int goodSuffixShift = shift[j + 1];
            s += Math.max(Math.max(1, badCharShift), goodSuffixShift);
        }
        return -1;
    }
}