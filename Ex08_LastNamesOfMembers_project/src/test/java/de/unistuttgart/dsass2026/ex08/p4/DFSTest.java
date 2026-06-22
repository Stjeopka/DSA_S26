package de.unistuttgart.dsass2026.ex08.p4;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;


public class DFSTest {

	@Test
	public void dfs_followsSmallestNodeIdWhenChoosingNextNode() {
		ArrayList<ArrayList<Integer>> graph = matrix(
				new int[] { 0, 1, 1, 0, 0 },
				new int[] { 0, 0, 0, 1, 0 },
				new int[] { 0, 0, 0, 0, 1 },
				new int[] { 0, 0, 0, 0, 1 },
				new int[] { 0, 0, 0, 0, 0 });

		int[] predecessor = TreeTraversal.dfs(graph, 0);

		assertArrayEquals(new int[] { -1, 0, 0, 1, 3 }, predecessor);
	}

	@Test
	public void dfs_marksUnreachableNodesWithMinusOne() {
		ArrayList<ArrayList<Integer>> graph = matrix(
				new int[] { 0, 1, 0, 0 },
				new int[] { 0, 0, 1, 0 },
				new int[] { 0, 0, 0, 0 },
				new int[] { 0, 0, 0, 0 });

		int[] predecessor = TreeTraversal.dfs(graph, 0);

		assertArrayEquals(new int[] { -1, 0, 1, -1 }, predecessor);
	}

	@Test(expected = IllegalArgumentException.class)
	public void dfs_throwsOnNonRectangularMatrix() {
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		graph.add(new ArrayList<>(Arrays.asList(0, 1, 0)));
		graph.add(new ArrayList<>(Arrays.asList(0, 0)));

		TreeTraversal.dfs(graph, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void dfs_throwsOnInvalidStartNode() {
		ArrayList<ArrayList<Integer>> graph = matrix(
				new int[] { 0, 1 },
				new int[] { 0, 0 });

		TreeTraversal.dfs(graph, 2);
	}

	private ArrayList<ArrayList<Integer>> matrix(int[]... rows) {
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
		for (int[] row : rows) {
			ArrayList<Integer> newRow = new ArrayList<>();
			for (int value : row) {
				newRow.add(value);
			}
			matrix.add(newRow);
		}
		return matrix;
	}

}