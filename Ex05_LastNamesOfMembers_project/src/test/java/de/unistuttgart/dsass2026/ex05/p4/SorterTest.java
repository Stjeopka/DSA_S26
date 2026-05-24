package de.unistuttgart.dsass2026.ex05.p4;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SorterTest {

    @Test
    public void sortsEmptyList() {
        SimpleList<Integer> list = new SimpleList<>();
        Sorter.heapSort(list);
        assertEquals(0, list.size());
    }

    @Test
    public void sortsSingleElement() {
        SimpleList<Integer> list = new SimpleList<>();
        list.append(42);
        Sorter.heapSort(list);
        assertEquals(Integer.valueOf(42), list.get(0));
    }

    @Test
    public void sortsAlreadySortedList() {
        SimpleList<Integer> list = fill(1, 2, 3, 4, 5);
        Sorter.heapSort(list);
        assertOrder(list, 1, 2, 3, 4, 5);
    }

    @Test
    public void sortsReversedList() {
        SimpleList<Integer> list = fill(5, 4, 3, 2, 1);
        Sorter.heapSort(list);
        assertOrder(list, 1, 2, 3, 4, 5);
    }

    @Test
    public void sortsListWithDuplicates() {
        SimpleList<Integer> list = fill(3, 1, 2, 3, 1, 2);
        Sorter.heapSort(list);
        assertOrder(list, 1, 1, 2, 2, 3, 3);
    }

    @Test
    public void sortsStrings() {
        SimpleList<String> list = new SimpleList<>();
        list.append("banana");
        list.append("apple");
        list.append("cherry");
        Sorter.heapSort(list);
        assertEquals("apple", list.get(0));
        assertEquals("banana", list.get(1));
        assertEquals("cherry", list.get(2));
    }

    private static SimpleList<Integer> fill(int... values) {
        SimpleList<Integer> list = new SimpleList<>();
        for (int v : values) {
            list.append(v);
        }
        return list;
    }

    private static void assertOrder(SimpleList<Integer> list, int... expected) {
        assertEquals(expected.length, list.size());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(Integer.valueOf(expected[i]), list.get(i));
        }
    }

}