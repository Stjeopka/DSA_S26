package de.unistuttgart.dsass2026.ex01.p5;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class SorterTest {

    // ── helpers ──────────────────────────────────────────────────────────────

    private <T extends Comparable<T>> AbstractSortCheckerList<T> listOf(T... elements) {
        SelectionSortCheckerList<T> list = new SelectionSortCheckerList<>();
        for (T e : elements) list.append(e);
        return list;
    }

    private <T extends Comparable<T>> String listToString(ISimpleList<T> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.getSize(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(list.getElement(i));
        }
        sb.append("]");
        return sb.toString();
    }

    private <T extends Comparable<T>> void sortAndLog(String algorithm, ISimpleList<T> list, Runnable sortCall) {
        String before = listToString(list);
        sortCall.run();
        String after = listToString(list);
        System.out.println("[" + algorithm + "] input:  " + before);
        System.out.println("[" + algorithm + "] output: " + after);
    }

    private <T extends Comparable<T>> void assertDescending(ISimpleList<T> list) {
        for (int i = 0; i < list.getSize() - 1; i++) {
            assertTrue(
                "Expected list[" + i + "] >= list[" + (i+1) + "] but got "
                    + list.getElement(i) + " < " + list.getElement(i+1),
                list.getElement(i).compareTo(list.getElement(i + 1)) >= 0
            );
        }
    }

    // ── reverseSelectionSort ─────────────────────────────────────────────────

    @Test
    public void selectionSort_normalCase() {
        ISimpleList<Integer> list = listOf(3, 1, 4, 1, 5, 9, 2, 6);
        sortAndLog("SelectionSort", list, () -> Sorter.reverseSelectionSort(list));
        assertDescending(list);
    }

    @Test
    public void selectionSort_alreadySorted() {
        ISimpleList<Integer> list = listOf(9, 7, 5, 3, 1);
        sortAndLog("SelectionSort", list, () -> Sorter.reverseSelectionSort(list));
        assertDescending(list);
    }

    @Test
    public void selectionSort_reverseSorted() {
        ISimpleList<Integer> list = listOf(1, 2, 3, 4, 5);
        sortAndLog("SelectionSort", list, () -> Sorter.reverseSelectionSort(list));
        assertDescending(list);
    }

    @Test
    public void selectionSort_singleElement() {
        ISimpleList<Integer> list = listOf(42);
        sortAndLog("SelectionSort", list, () -> Sorter.reverseSelectionSort(list));
        assertEquals(42, (int) list.getElement(0));
    }

    @Test
    public void selectionSort_duplicates() {
        ISimpleList<Integer> list = listOf(5, 5, 5, 5);
        sortAndLog("SelectionSort", list, () -> Sorter.reverseSelectionSort(list));
        assertDescending(list);
    }

    @Test
    public void selectionSort_largeRandom() {
        Random rng = new Random(42);
        SelectionSortCheckerList<Integer> list = new SelectionSortCheckerList<>();
        for (int i = 0; i < 200; i++) list.append(rng.nextInt(1000));
        sortAndLog("SelectionSort", list, () -> Sorter.reverseSelectionSort(list));
        assertDescending(list);
    }

    // ── reverseInsertionSort ─────────────────────────────────────────────────

    @Test
    public void insertionSort_normalCase() {
        ISimpleList<Integer> list = listOf(3, 1, 4, 1, 5, 9, 2, 6);
        sortAndLog("InsertionSort", list, () -> Sorter.reverseInsertionSort(list));
        assertDescending(list);
    }

    @Test
    public void insertionSort_alreadySorted() {
        ISimpleList<Integer> list = listOf(9, 7, 5, 3, 1);
        sortAndLog("InsertionSort", list, () -> Sorter.reverseInsertionSort(list));
        assertDescending(list);
    }

    @Test
    public void insertionSort_reverseSorted() {
        ISimpleList<Integer> list = listOf(1, 2, 3, 4, 5);
        sortAndLog("InsertionSort", list, () -> Sorter.reverseInsertionSort(list));
        assertDescending(list);
    }

    @Test
    public void insertionSort_singleElement() {
        ISimpleList<Integer> list = listOf(7);
        sortAndLog("InsertionSort", list, () -> Sorter.reverseInsertionSort(list));
        assertEquals(7, (int) list.getElement(0));
    }

    @Test
    public void insertionSort_duplicates() {
        ISimpleList<Integer> list = listOf(3, 3, 1, 1, 2, 2);
        sortAndLog("InsertionSort", list, () -> Sorter.reverseInsertionSort(list));
        assertDescending(list);
    }

    @Test
    public void insertionSort_strings() {
        ISimpleList<String> list = new SelectionSortCheckerList<>();
        for (String s : new String[]{"banana", "apple", "cherry", "date"}) list.append(s);
        sortAndLog("InsertionSort", list, () -> Sorter.reverseInsertionSort(list));
        assertDescending(list);
    }

    // ── reverseBubbleSort ────────────────────────────────────────────────────

    @Test
    public void bubbleSort_normalCase() {
        ISimpleList<Integer> list = listOf(3, 1, 4, 1, 5, 9, 2, 6);
        sortAndLog("BubbleSort", list, () -> Sorter.reverseBubbleSort(list));
        assertDescending(list);
    }

    @Test
    public void bubbleSort_alreadySorted() {
        ISimpleList<Integer> list = listOf(9, 7, 5, 3, 1);
        sortAndLog("BubbleSort", list, () -> Sorter.reverseBubbleSort(list));
        assertDescending(list);
    }

    @Test
    public void bubbleSort_reverseSorted() {
        ISimpleList<Integer> list = listOf(1, 2, 3, 4, 5);
        sortAndLog("BubbleSort", list, () -> Sorter.reverseBubbleSort(list));
        assertDescending(list);
    }

    @Test
    public void bubbleSort_singleElement() {
        ISimpleList<Integer> list = listOf(99);
        sortAndLog("BubbleSort", list, () -> Sorter.reverseBubbleSort(list));
        assertEquals(99, (int) list.getElement(0));
    }

    @Test
    public void bubbleSort_allEqual() {
        ISimpleList<Integer> list = listOf(7, 7, 7);
        sortAndLog("BubbleSort", list, () -> Sorter.reverseBubbleSort(list));
        assertDescending(list);
    }

    @Test
    public void bubbleSort_largeRandom() {
        Random rng = new Random(7);
        SelectionSortCheckerList<Integer> list = new SelectionSortCheckerList<>();
        for (int i = 0; i < 200; i++) list.append(rng.nextInt(1000));
        sortAndLog("BubbleSort", list, () -> Sorter.reverseBubbleSort(list));
        assertDescending(list);
    }
}