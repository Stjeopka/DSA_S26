package de.unistuttgart.dsass2026.ex03.p5;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class BinaryTreeNodeTest {

	@Rule
	public Timeout globalTimeout = new Timeout(5, TimeUnit.SECONDS);

	@Test
	public void defaultConstructor_initializesAllFieldsWithNull() {
		BinaryTreeNode<Integer> node = new BinaryTreeNode<>();

		assertNull(node.getValue());
		assertNull(node.getLeftChild());
		assertNull(node.getRightChild());
	}

	@Test
	public void setValueAndGetValue_roundTrip() {
		BinaryTreeNode<Integer> node = new BinaryTreeNode<>();

		node.setValue(42);

		assertEquals(Integer.valueOf(42), node.getValue());
	}

	@Test
	public void setChildrenAndGetChildren_roundTrip() {
		BinaryTreeNode<Integer> parent = new BinaryTreeNode<>();
		BinaryTreeNode<Integer> left = new BinaryTreeNode<>();
		BinaryTreeNode<Integer> right = new BinaryTreeNode<>();

		left.setValue(1);
		right.setValue(2);
		parent.setLeftChild(left);
		parent.setRightChild(right);

		assertSame(left, parent.getLeftChild());
		assertSame(right, parent.getRightChild());
	}

}