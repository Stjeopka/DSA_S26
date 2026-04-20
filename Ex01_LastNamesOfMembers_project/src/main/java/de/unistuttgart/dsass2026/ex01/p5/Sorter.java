package de.unistuttgart.dsass2026.ex01.p5;

public class Sorter {

    /**
     * Performs SelectionSort to sort the list in **descending** order.
     * Works directly (in-place) on list object, hence there's no return value.
     *
     * @param <T>  The type of list elements
     * @param list List to sort; potentially unsorted list at first, sorted
     *             list in the end
     */
    public static <T extends Comparable<T>> void reverseSelectionSort(ISimpleList<T> list) {
        int n = list.getSize();
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (list.getElement(j).compareTo(list.getElement(maxIdx)) > 0) {
                    maxIdx = j;
                }
            }
            if (maxIdx != i) {
                list.swapElements(i, maxIdx);
            }
        }
    }

    /**
     * Performs InsertionSort to sort the list in **descending** order.
     * Works directly (in-place) on list object, hence there's no return value.
     *
     * @param <T>  The type of list elements
     * @param list List to sort; potentially unsorted list at first, sorted
     *             list in the end
     */
    public static <T extends Comparable<T>> void reverseInsertionSort(ISimpleList<T> list) {
        int n = list.getSize();
        for (int i = 1; i < n; i++) {
            int j = i;
            while (j > 0 && list.getElement(j).compareTo(list.getElement(j - 1)) > 0) {
                list.swapElements(j, j - 1);
                j--;
            }
        }
    }

    /**
     * Performs BubbleSort to sort the list in **descending** order.
     * Works directly (in-place) on list object, hence there's no return value.
     *
     * @param <T>  The type of list elements
     * @param list List to sort; potentially unsorted list at first, sorted
     *             list in the end
     */
    public static <T extends Comparable<T>> void reverseBubbleSort(ISimpleList<T> list) {
        int n = list.getSize();
        boolean changed;
        do {
            changed = false;
            for (int i = 0; i < n - 1; i++) {
                if (list.getElement(i).compareTo(list.getElement(i + 1)) < 0) {
                    list.swapElements(i, i + 1);
                    changed = true;
                }
            }
        } while (changed);
    }
}