package de.unistuttgart.dsass2026.ex04.p2;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AVLTreeTest {

	@Test
	public void rebalanceReturnsSameNodeForBalancedSubtree() {
		AVLTree<Integer> tree = new AVLTree<>();
		AVLNode<Integer> root = n(2, n(1, null, null), n(3, null, null));

		AVLNode<Integer> rebalanced = tree.rebalance(root);

		assertSame(root, rebalanced);
		assertTreeAndBalanceValid(rebalanced);
	}

	@Test
	public void rebalancePerformsRightRotationForCase1() {
		AVLTree<Integer> tree = new AVLTree<>();
		AVLNode<Integer> root = n(30, n(20, n(10, null, null), null), null);

		AVLNode<Integer> rebalanced = tree.rebalance(root);
		AVLNode<Integer> expected = n(20, n(10, null, null), n(30, null, null));

		assertTrue(expected.subtreeEquals(rebalanced));
		assertTreeAndBalanceValid(rebalanced);
	}

	@Test
	public void rebalancePerformsLeftRightRotationForCase2() {
		AVLTree<Integer> tree = new AVLTree<>();
		AVLNode<Integer> root = n(30, n(10, null, n(20, null, null)), null);

		AVLNode<Integer> rebalanced = tree.rebalance(root);
		AVLNode<Integer> expected = n(20, n(10, null, null), n(30, null, null));

		assertTrue(expected.subtreeEquals(rebalanced));
		assertTreeAndBalanceValid(rebalanced);
	}

	@Test
	public void rebalancePerformsLeftRotationForCase3() {
		AVLTree<Integer> tree = new AVLTree<>();
		AVLNode<Integer> root = n(10, null, n(20, null, n(30, null, null)));

		AVLNode<Integer> rebalanced = tree.rebalance(root);
		AVLNode<Integer> expected = n(20, n(10, null, null), n(30, null, null));

		assertTrue(expected.subtreeEquals(rebalanced));
		assertTreeAndBalanceValid(rebalanced);
	}

	@Test
	public void rebalancePerformsRightLeftRotationForCase4() {
		AVLTree<Integer> tree = new AVLTree<>();
		AVLNode<Integer> root = n(10, null, n(30, n(20, null, null), null));

		AVLNode<Integer> rebalanced = tree.rebalance(root);
		AVLNode<Integer> expected = n(20, n(10, null, null), n(30, null, null));

		assertTrue(expected.subtreeEquals(rebalanced));
		assertTreeAndBalanceValid(rebalanced);
	}

	@Test
	public void removeLeafKeepsTreeValid() {
		AVLTree<Integer> tree = new AVLTree<>();
		tree.insert(20);
		tree.insert(10);
		tree.insert(30);
		tree.insert(5);
		tree.insert(15);
		tree.insert(25);
		tree.insert(35);

		tree.remove(5);

		AVLNode<Integer> expected = n(20, n(10, null, n(15, null, null)), n(30, n(25, null, null), n(35, null, null)));
		assertTrue(expected.subtreeEquals(tree.getRootNode()));
		assertTreeAndBalanceValid(tree.getRootNode());
	}

	@Test
	public void removeNodeWithOneChildBypassesNode() {
		AVLTree<Integer> tree = new AVLTree<>();
		tree.insert(20);
		tree.insert(10);
		tree.insert(30);
		tree.insert(25);

		tree.remove(30);

		AVLNode<Integer> expected = n(20, n(10, null, null), n(25, null, null));
		assertTrue(expected.subtreeEquals(tree.getRootNode()));
		assertTreeAndBalanceValid(tree.getRootNode());
	}

	@Test
	public void removeNodeWithTwoChildrenUsesInorderSuccessor() {
		AVLTree<Integer> tree = new AVLTree<>();
		AVLNode<Integer> root = n(25, n(20, n(10, null, null), n(22, null, null)), n(30, null, n(40, null, null)));
		tree.setRootNode(root);

		tree.remove(25);

		AVLNode<Integer> expected = n(30, n(20, n(10, null, null), n(22, null, null)), n(40, null, null));
		assertTrue(expected.subtreeEquals(tree.getRootNode()));
		assertTreeAndBalanceValid(tree.getRootNode());
	}

	@Test
	public void removeCanTriggerRebalanceTowardsRoot() {
		AVLTree<Integer> tree = new AVLTree<>();
		AVLNode<Integer> root = n(4, n(2, n(1, null, null), n(3, null, null)), n(6, n(5, null, null), n(7, null, null)));
		tree.setRootNode(root);

		tree.remove(7);
		tree.remove(6);
		tree.remove(5);

		AVLNode<Integer> expected = n(2, n(1, null, null), n(4, n(3, null, null), null));
		assertTrue(expected.subtreeEquals(tree.getRootNode()));
		assertTreeAndBalanceValid(tree.getRootNode());
	}

	@Test
	public void removeMissingKeyLeavesTreeUnchanged() {
		AVLTree<Integer> tree = new AVLTree<>();
		AVLNode<Integer> root = n(10, n(5, null, null), n(15, null, null));
		tree.setRootNode(root);

		tree.remove(42);

		AVLNode<Integer> expected = n(10, n(5, null, null), n(15, null, null));
		assertTrue(expected.subtreeEquals(tree.getRootNode()));
		assertTreeAndBalanceValid(tree.getRootNode());
	}

	private AVLNode<Integer> n(int key, AVLNode<Integer> left, AVLNode<Integer> right) {
		AVLNode<Integer> node = new AVLNode<>(key);
		node.setLeft(left);
		node.setRight(right);
		node.updateHeight();
		return node;
	}

	private void assertTreeAndBalanceValid(AVLNode<Integer> root) {
		assertTrue(isBst(root, null, null));
		assertTrue(isAvlBalanced(root));
	}

	private boolean isAvlBalanced(AVLNode<Integer> n) {
		if (n == null)
			return true;

		int balance = n.getBalance();
		return balance >= -1 && balance <= 1 && isAvlBalanced(n.getLeft()) && isAvlBalanced(n.getRight());
	}

	private boolean isBst(AVLNode<Integer> n, Integer minExclusive, Integer maxExclusive) {
		if (n == null)
			return true;

		int key = n.getKey();
		if (minExclusive != null && key <= minExclusive)
			return false;
		if (maxExclusive != null && key >= maxExclusive)
			return false;

		return isBst(n.getLeft(), minExclusive, key) && isBst(n.getRight(), key, maxExclusive);
	}

}