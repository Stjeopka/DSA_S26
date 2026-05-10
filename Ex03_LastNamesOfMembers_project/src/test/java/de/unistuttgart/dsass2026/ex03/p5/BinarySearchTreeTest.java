package de.unistuttgart.dsass2026.ex03.p5;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class BinarySearchTreeTest {

	@Rule
	public Timeout globalTimeout = new Timeout(5, TimeUnit.SECONDS);

	private BinarySearchTree<Integer> createTree(int... values) {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		for (int value : values) {
			tree.insert(value);
		}
		return tree;
	}

	private List<Integer> toList(Iterator<Integer> iterator) {
		List<Integer> values = new ArrayList<>();
		while (iterator.hasNext()) {
			values.add(iterator.next());
		}
		return values;
	}

	@Test
	public void preorderTraversal_returnsExpectedOrder() {
		BinarySearchTree<Integer> tree = createTree(8, 4, 12, 2, 6, 10, 14);

		List<Integer> traversal = toList(tree.iterator(TreeTraversalType.PREORDER));

		assertEquals(Arrays.asList(8, 4, 2, 6, 12, 10, 14), traversal);
	}

	@Test
	public void inorderTraversal_returnsExpectedOrder() {
		BinarySearchTree<Integer> tree = createTree(8, 4, 12, 2, 6, 10, 14);

		List<Integer> traversal = toList(tree.iterator(TreeTraversalType.INORDER));

		assertEquals(Arrays.asList(2, 4, 6, 8, 10, 12, 14), traversal);
	}

	@Test
	public void postorderTraversal_returnsExpectedOrder() {
		BinarySearchTree<Integer> tree = createTree(8, 4, 12, 2, 6, 10, 14);

		List<Integer> traversal = toList(tree.iterator(TreeTraversalType.POSTORDER));

		assertEquals(Arrays.asList(2, 6, 4, 10, 14, 12, 8), traversal);
	}

	@Test
	public void levelorderTraversal_returnsExpectedOrder() {
		BinarySearchTree<Integer> tree = createTree(8, 4, 12, 2, 6, 10, 14);

		List<Integer> traversal = toList(tree.iterator(TreeTraversalType.LEVELORDER));

		assertEquals(Arrays.asList(8, 4, 12, 2, 6, 10, 14), traversal);
	}

	@Test
	public void emptyTreeIterator_hasNoNext() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();

		assertFalse(tree.iterator(TreeTraversalType.INORDER).hasNext());
	}

	@Test(expected = NoSuchElementException.class)
	public void emptyTreeIterator_nextThrowsNoSuchElementException() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();

		tree.iterator(TreeTraversalType.INORDER).next();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void iteratorRemove_throwsUnsupportedOperationException() {
		BinarySearchTree<Integer> tree = createTree(8, 4, 12);
		Iterator<Integer> iterator = tree.iterator(TreeTraversalType.PREORDER);

		iterator.remove();
	}

	@Test(expected = IllegalArgumentException.class)
	public void iteratorWithNullTraversalType_throwsIllegalArgumentException() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();

		tree.iterator(null);
	}

	@Test
	public void isFull_emptyTreeIsFull() {
		BinarySearchTree<Integer> tree = new BinarySearchTree<>();

		assertTrue(tree.isFull());
	}

	@Test
	public void isFull_perfectTreeIsFull() {
		BinarySearchTree<Integer> tree = createTree(8, 4, 12, 2, 6, 10, 14);

		assertTrue(tree.isFull());
	}

	@Test
	public void isFull_treeWithSingleChildIsNotFull() {
		BinarySearchTree<Integer> tree = createTree(8, 4, 12, 2);

		assertFalse(tree.isFull());
	}

	@Test
	public void insert_duplicateValueIsIgnored() {
		BinarySearchTree<Integer> tree = createTree(8, 4, 12, 4, 12, 8, 2, 2);

		List<Integer> traversal = toList(tree.iterator(TreeTraversalType.INORDER));

		assertEquals(Arrays.asList(2, 4, 8, 12), traversal);
	}

}