package de.unistuttgart.dsass2026.ex05.p4;

public class Sorter {

    /**
     * Sorts the given list in ascending order using heap sort with a max-heap.
     */
    public static <T extends Comparable<T>> void heapSort(final ISimpleList<T> list) {
        int n = list.size();

        // build max-heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(list, i, n);
        }

        // repeatedly move the max element to the end and shrink the heap
        for (int end = n - 1; end > 0; end--) {
            list.swap(0, end);
            siftDown(list, 0, end);
        }
    }

    /**
     * Restores the max-heap property starting at index start,
     * considering only the first heapSize elements as the heap.
     */
    private static <T extends Comparable<T>> void siftDown(final ISimpleList<T> list, int start, int heapSize) {
        int parent = start;
        while (true) {
            int left = 2 * parent + 1;
            int right = 2 * parent + 2;
            int largest = parent;

            if (left < heapSize && list.get(left).compareTo(list.get(largest)) > 0) {
                largest = left;
            }
            if (right < heapSize && list.get(right).compareTo(list.get(largest)) > 0) {
                largest = right;
            }
            if (largest == parent) {
                return;
            }
            list.swap(parent, largest);
            parent = largest;
        }
    }

}