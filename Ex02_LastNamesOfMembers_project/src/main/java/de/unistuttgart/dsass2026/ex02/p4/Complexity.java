package de.unistuttgart.dsass2026.ex02.p4;

public class Complexity {

    /**
     *
     * @param n some integer
     * @return some new number based on n
     */
    public static int couldBeBetter1(int n) {
        int result = 0;
        for (int i = 0; i <= n; i++) {
            result += 2;
        }
        return result;
    }

    /**
     *
     * @param n some integer
     * @return some new number based on n
     */
    public static int couldBeBetter2(int n) {
        int temp = 1;
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result = 0;
            for (int j = 1; j <= i; j++) {
                result += temp;
            }
            temp = result;
        }
        return result;
    }

    /**
     *
     * @param n some non-negative integer
     * @return some new number based on n
     */
    public static int couldBeBetter3(int n) {
        if (n < 0) throw new IllegalArgumentException("n must not be negative!");
        else if (n <= 1) return n;
        else return couldBeBetter3(n-1) + couldBeBetter3(n-2);
    }

    /**
     *
     * @param n some integer
     * @return same number as couldBeBetter1(n), but faster
     */
    public static int isDoneBetter1(int n) {
        if (n < 0) {
            return 0;
        }
        return 2 * (n + 1);
    }

    /**
     *
     * @param n some integer
     * @return same number as couldBeBetter2(n), but faster
     */
    public static int isDoneBetter2(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    /**
     *
     * @param n some non-negative integer
     * @return same number as couldBeBetter3(n), but faster
     */
    public static int isDoneBetter3(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must not be negative!");
        }
        if (n <= 1) {
            return n;
        }

        int previous = 0;
        int current = 1;
        for (int i = 2; i <= n; i++) {
            int next = previous + current;
            previous = current;
            current = next;
        }
        return current;
    }

}