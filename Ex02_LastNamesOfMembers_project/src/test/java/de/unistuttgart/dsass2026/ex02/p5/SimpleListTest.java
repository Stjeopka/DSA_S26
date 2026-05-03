package de.unistuttgart.dsass2026.ex02.p5;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class SimpleListTest {

	private <T extends Comparable<T>> SimpleList<T> listOf(T... elements) {
		SimpleList<T> list = new SimpleList<>();
		for (int i = elements.length - 1; i >= 0; i--) {
			list.prepend(elements[i]);
		}
		return list;
	}

	private <T extends Comparable<T>> void assertAscending(ISimpleList<T> list) {
		for (int i = 0; i < list.getSize() - 1; i++) {
			assertTrue(
				"Expected list[" + i + "] <= list[" + (i + 1) + "] but got "
					+ list.getElement(i) + " > " + list.getElement(i + 1),
				list.getElement(i).compareTo(list.getElement(i + 1)) <= 0
			);
		}
	}

	private <T extends Comparable<T>> String listToString(ISimpleList<T> list) {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < list.getSize(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
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

	@Test
	public void prepend_andGetSize_workCorrectly() {
		SimpleList<Integer> list = new SimpleList<>();
		assertEquals(0, list.getSize());

		list.prepend(10);
		assertEquals(1, list.getSize());
		assertEquals(10, (int) list.getElement(0));

		list.prepend(20);
		assertEquals(2, list.getSize());
		assertEquals(20, (int) list.getElement(0));
		assertEquals(10, (int) list.getElement(1));
	}

	@Test
	public void getElement_throwsForInvalidIndex() {
		SimpleList<Integer> list = listOf(1, 2, 3);
		assertThrows(IndexOutOfBoundsException.class, () -> list.getElement(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.getElement(3));
	}

	@Test
	public void sort_emptyList() {
		SimpleList<Integer> list = new SimpleList<>();
		sortAndLog("BubbleSortLinkedList", list, list::sort);
		assertEquals(0, list.getSize());
	}

	@Test
	public void sort_singleElement() {
		SimpleList<Integer> list = listOf(42);
		sortAndLog("BubbleSortLinkedList", list, list::sort);
		assertEquals(1, list.getSize());
		assertEquals(42, (int) list.getElement(0));
	}

	@Test
	public void sort_alreadySorted() {
		SimpleList<Integer> list = listOf(1, 2, 3, 4, 5);
		sortAndLog("BubbleSortLinkedList", list, list::sort);
		assertAscending(list);
	}

	@Test
	public void sort_reverseSorted() {
		SimpleList<Integer> list = listOf(5, 4, 3, 2, 1);
		sortAndLog("BubbleSortLinkedList", list, list::sort);
		assertAscending(list);
	}

	@Test
	public void sort_withDuplicates() {
		SimpleList<Integer> list = listOf(3, 1, 2, 2, 3, 1);
		sortAndLog("BubbleSortLinkedList", list, list::sort);
		assertAscending(list);
	}

	@Test
	public void sort_strings() {
		SimpleList<String> list = listOf("banana", "apple", "date", "cherry");
		sortAndLog("BubbleSortLinkedList", list, list::sort);
		assertAscending(list);
		assertEquals("apple", list.getElement(0));
		assertEquals("banana", list.getElement(1));
		assertEquals("cherry", list.getElement(2));
		assertEquals("date", list.getElement(3));
	}

	@Test
	public void sort_largeRandom_matchesJavaSortedReference() {
		Random rng = new Random(42);
		List<Integer> values = new ArrayList<>();
		for (int i = 0; i < 200; i++) {
			values.add(rng.nextInt(1000) - 500);
		}

		SimpleList<Integer> list = listOf(values.toArray(new Integer[0]));
		ArrayList<Integer> expected = new ArrayList<>(values);
		Collections.sort(expected);

		sortAndLog("BubbleSortLinkedList", list, list::sort);
		assertAscending(list);

		for (int i = 0; i < expected.size(); i++) {
			assertEquals("Mismatch at index " + i, expected.get(i), list.getElement(i));
		}
	}

}