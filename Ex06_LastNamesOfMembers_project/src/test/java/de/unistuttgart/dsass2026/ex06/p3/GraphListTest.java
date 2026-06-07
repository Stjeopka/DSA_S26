package de.unistuttgart.dsass2026.ex06.p3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

public class GraphListTest {

	@Test
	public void createFromEdgeListBuildsAdjacencyList() {
		Graph<Integer> graph = new Graph<>();

		graph.createFromEdgeList(new ArrayList<>(Arrays.asList(4, 4, 0, 1, 0, 2, 2, 3, 3, 1)));

		assertEquals(4, graph.numberOfNodes());
		assertEquals(4, graph.numberOfEdges());
		assertEdges(graph.outgoingEdges(0), new int[][] { { 0, 1 }, { 0, 2 } });
		assertEdges(graph.outgoingEdges(1), new int[][] {});
		assertEdges(graph.outgoingEdges(2), new int[][] { { 2, 3 } });
		assertEdges(graph.outgoingEdges(3), new int[][] { { 3, 1 } });
	}

	@Test
	public void toEdgeListIncludesNodesAndEdges() {
		Graph<Integer> graph = new Graph<>();
		for (int i = 0; i < 4; i++)
			graph.addNode();
		graph.addEdge(0, 1);
		graph.addEdge(0, 2);
		graph.addEdge(2, 3);

		assertEquals(new ArrayList<>(Arrays.asList(4, 3, 0, 1, 0, 2, 2, 3)), graph.toEdgeList());
	}

	@Test
	public void createFromEdgeListPreservesIsolatedNodes() {
		Graph<Integer> graph = new Graph<>();

		graph.createFromEdgeList(new ArrayList<>(Arrays.asList(3, 1, 1, 2)));

		assertEquals(3, graph.numberOfNodes());
		assertEquals(1, graph.numberOfEdges());
		assertEdges(graph.outgoingEdges(0), new int[][] {});
		assertEdges(graph.outgoingEdges(1), new int[][] { { 1, 2 } });
		assertEdges(graph.outgoingEdges(2), new int[][] {});
	}

	@Test
	public void createFromNodeListBuildsAdjacencyList() {
		Graph<Integer> graph = new Graph<>();

		graph.createFromNodeList(new ArrayList<>(Arrays.asList(4, 4, 0, 2, 2, 3, 4, 1, 2, 3, 1)));

		assertEquals(4, graph.numberOfNodes());
		assertEquals(4, graph.numberOfEdges());
		assertEdges(graph.outgoingEdges(0), new int[][] { { 0, 1 }, { 0, 2 } });
		assertEdges(graph.outgoingEdges(1), new int[][] {});
		assertEdges(graph.outgoingEdges(2), new int[][] { { 2, 3 } });
		assertEdges(graph.outgoingEdges(3), new int[][] { { 3, 1 } });
	}

	@Test
	public void toNodeListUsesOffsetsAndDestinations() {
		Graph<Integer> graph = new Graph<>();
		for (int i = 0; i < 4; i++)
			graph.addNode();
		graph.addEdge(0, 1);
		graph.addEdge(0, 2);
		graph.addEdge(2, 3);
		graph.addEdge(3, 1);

		assertEquals(new ArrayList<>(Arrays.asList(4, 4, 0, 2, 2, 3, 4, 1, 2, 3, 1)), graph.toNodeList());
	}

	@Test
	public void emptyGraphsCanBeConverted() {
		Graph<Integer> graph = new Graph<>();

		assertEquals(new ArrayList<>(Arrays.asList(0, 0)), graph.toEdgeList());
		assertEquals(new ArrayList<>(Arrays.asList(0, 0, 0)), graph.toNodeList());

		Graph<Integer> edgeListGraph = new Graph<>();
		edgeListGraph.createFromEdgeList(new ArrayList<>(Arrays.asList(0, 0)));
		assertEquals(0, edgeListGraph.numberOfNodes());
		assertEquals(0, edgeListGraph.numberOfEdges());

		Graph<Integer> nodeListGraph = new Graph<>();
		nodeListGraph.createFromNodeList(new ArrayList<>(Arrays.asList(0, 0, 0)));
		assertEquals(0, nodeListGraph.numberOfNodes());
		assertEquals(0, nodeListGraph.numberOfEdges());
	}

	@Test
	public void isolatedNodesArePreservedByNodeList() {
		Graph<Integer> graph = new Graph<>();
		graph.createFromNodeList(new ArrayList<>(Arrays.asList(3, 1, 0, 0, 1, 1, 2)));

		assertEquals(3, graph.numberOfNodes());
		assertEquals(1, graph.numberOfEdges());
		assertEdges(graph.outgoingEdges(0), new int[][] {});
		assertEdges(graph.outgoingEdges(1), new int[][] { { 1, 2 } });
		assertEdges(graph.outgoingEdges(2), new int[][] {});
		assertEquals(new ArrayList<>(Arrays.asList(3, 1, 0, 0, 1, 1, 2)), graph.toNodeList());
	}

	@Test
	public void invalidInputsThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> new Graph<Integer>().createFromEdgeList(null));
		assertThrows(IllegalArgumentException.class, () -> new Graph<Integer>().createFromNodeList(null));
		assertThrows(IllegalArgumentException.class,
				() -> new Graph<Integer>().createFromEdgeList(new ArrayList<>(Arrays.asList(2, 2, 0, 1))));
		assertThrows(IllegalArgumentException.class,
				() -> new Graph<Integer>().createFromEdgeList(new ArrayList<>(Arrays.asList(2, 1, 0, 2))));
		assertThrows(IllegalArgumentException.class,
				() -> new Graph<Integer>().createFromEdgeList(new ArrayList<>(Arrays.asList(2, -1))));
		assertThrows(IllegalArgumentException.class,
				() -> new Graph<Integer>().createFromNodeList(new ArrayList<>(Arrays.asList(2, 1, 1, 0, 1, 1))));
		assertThrows(IllegalArgumentException.class,
				() -> new Graph<Integer>().createFromNodeList(new ArrayList<>(Arrays.asList(2, 1, 0, 1, 1, 2))));
	}

	@Test
	public void createMethodsRequireEmptyGraph() {
		Graph<Integer> graph = new Graph<>();
		graph.addNode();

		assertThrows(UnsupportedOperationException.class,
				() -> graph.createFromEdgeList(new ArrayList<>(Arrays.asList(0, 0))));
		assertThrows(UnsupportedOperationException.class,
				() -> graph.createFromNodeList(new ArrayList<>(Arrays.asList(0, 0, 0))));
	}

	private void assertEdges(Iterator<IEdge> actualEdges, int[][] expectedEdges) {
		for (int[] expectedEdge : expectedEdges) {
			IEdge actualEdge = actualEdges.next();
			assertEquals(expectedEdge[0], actualEdge.getSource());
			assertEquals(expectedEdge[1], actualEdge.getDestination());
		}
		assertFalse(actualEdges.hasNext());
	}

}