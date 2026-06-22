package de.unistuttgart.dsass2026.ex08.p4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;


public class FordFulkersonTest {

	@Test
	public void calculate_returnsExpectedMaxFlowForClassicExample() {
		ArrayList<ArrayList<Integer>> graph = matrix(
				new int[] { 0, 16, 13, 0, 0, 0 },
				new int[] { 0, 0, 10, 12, 0, 0 },
				new int[] { 0, 4, 0, 0, 14, 0 },
				new int[] { 0, 0, 9, 0, 0, 20 },
				new int[] { 0, 0, 0, 7, 0, 4 },
				new int[] { 0, 0, 0, 0, 0, 0 });

		FordFulkerson fordFulkerson = new FordFulkerson();
		int maxFlow = fordFulkerson.calculate(graph, 0, 5);

		assertEquals(23, maxFlow);
	}

	@Test
	public void calculate_doesNotModifyInputGraph_andFlowMatchesExpectedSimpleCase() {
		ArrayList<ArrayList<Integer>> graph = matrix(
				new int[] { 0, 3, 2, 0 },
				new int[] { 0, 0, 1, 2 },
				new int[] { 0, 0, 0, 3 },
				new int[] { 0, 0, 0, 0 });

		ArrayList<ArrayList<Integer>> original = copyMatrix(graph);

		FordFulkerson fordFulkerson = new FordFulkerson();
		int maxFlow = fordFulkerson.calculate(graph, 0, 3);
		ArrayList<ArrayList<Integer>> flow = fordFulkerson.flow();

		assertEquals(5, maxFlow);
		assertEquals(original, graph);
		assertEquals(3, (int) flow.get(0).get(1));
		assertEquals(2, (int) flow.get(0).get(2));
		assertEquals(2, (int) flow.get(1).get(3));
		assertEquals(3, (int) flow.get(2).get(3));
	}

	@Test
	public void flow_isSkewSymmetricAndRespectsCapacities() {
		ArrayList<ArrayList<Integer>> graph = matrix(
				new int[] { 0, 7, 4, 0 },
				new int[] { 0, 0, 3, 5 },
				new int[] { 0, 0, 0, 6 },
				new int[] { 0, 0, 0, 0 });

		FordFulkerson fordFulkerson = new FordFulkerson();
		fordFulkerson.calculate(graph, 0, 3);
		ArrayList<ArrayList<Integer>> flow = fordFulkerson.flow();

		for (int i = 0; i < graph.size(); i++) {
			for (int j = 0; j < graph.size(); j++) {
				assertEquals(-flow.get(j).get(i).intValue(), flow.get(i).get(j).intValue());
				assertTrue(flow.get(i).get(j) <= graph.get(i).get(j));
			}
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void calculate_throwsOnNonRectangularMatrix() {
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		ArrayList<Integer> row1 = new ArrayList<>();
		row1.add(0);
		row1.add(1);
		ArrayList<Integer> row2 = new ArrayList<>();
		row2.add(0);
		graph.add(row1);
		graph.add(row2);

		FordFulkerson fordFulkerson = new FordFulkerson();
		fordFulkerson.calculate(graph, 0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void calculate_throwsOnInvalidSourceOrSink() {
		ArrayList<ArrayList<Integer>> graph = matrix(
				new int[] { 0, 1 },
				new int[] { 0, 0 });

		FordFulkerson fordFulkerson = new FordFulkerson();
		fordFulkerson.calculate(graph, -1, 1);
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

	private ArrayList<ArrayList<Integer>> copyMatrix(ArrayList<ArrayList<Integer>> matrix) {
		ArrayList<ArrayList<Integer>> copy = new ArrayList<>();
		for (ArrayList<Integer> row : matrix) {
			copy.add(new ArrayList<>(row));
		}
		return copy;
	}

}