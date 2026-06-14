package de.unistuttgart.dsass2026.ex07.p4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class ShortestPathTest {

    @Rule
    public Timeout globalTimeout = new Timeout(10, TimeUnit.SECONDS);

    private static final double EPSILON = 1e-6;

    private IWeightedGraph graph;

    @Before
    public void setUp() {
        graph = createGraphWithAlternativeRoutesAndUnreachableNode();
    }

    @Test
    public void testDistancesPreferCheapestRoute() {
        ShortestPath shortestPath = new ShortestPath(graph, 0);

        assertEquals(0.0, shortestPath.distanceTo(0), EPSILON);
        assertEquals(1.0, shortestPath.distanceTo(2), EPSILON);
        assertEquals(2.0, shortestPath.distanceTo(1), EPSILON);
        assertEquals(3.0, shortestPath.distanceTo(3), EPSILON);
        assertEquals(6.0, shortestPath.distanceTo(4), EPSILON);
    }

    @Test
    public void testPathToTargetContainsExpectedHops() {
        ShortestPath shortestPath = new ShortestPath(graph, 0);

        Iterable<IEdge> path = shortestPath.pathTo(4);
        assertNotNull(path);

        StringBuilder hopSequence = new StringBuilder();
        double summedWeight = 0.0;
        int numberOfEdges = 0;
        for (IEdge edge : path) {
            hopSequence.append(edge.getDestination());
            summedWeight += edge.getWeight();
            numberOfEdges++;
        }

        assertEquals(4, numberOfEdges);
        assertEquals("2134", hopSequence.toString());
        assertEquals(shortestPath.distanceTo(4), summedWeight, EPSILON);
    }

    @Test
    public void testPathToStartNodeIsEmpty() {
        ShortestPath shortestPath = new ShortestPath(graph, 0);

        Iterable<IEdge> path = shortestPath.pathTo(0);
        assertNotNull(path);
        Iterator<IEdge> iterator = path.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testUnreachableNodeHasNoPath() {
        ShortestPath shortestPath = new ShortestPath(graph, 0);

        assertFalse(shortestPath.existsPathTo(5));
        assertEquals(Double.POSITIVE_INFINITY, shortestPath.distanceTo(5), 0.0);
        assertNull(shortestPath.pathTo(5));
    }

    @Test
    public void testInvalidStartNodeProducesNoReachableNodes() {
        WeightedGraph tinyGraph = new WeightedGraph();
        tinyGraph.addNode(0, new Node(0, 0, 0));
        tinyGraph.addNode(1, new Node(1, 0, 0));
        tinyGraph.addEdge(new Edge(0, 1, 2.0));

        ShortestPath shortestPath = new ShortestPath(tinyGraph, 99);

        assertFalse(shortestPath.existsPathTo(0));
        assertFalse(shortestPath.existsPathTo(1));
        assertEquals(Double.POSITIVE_INFINITY, shortestPath.distanceTo(0), 0.0);
        assertEquals(Double.POSITIVE_INFINITY, shortestPath.distanceTo(1), 0.0);
        assertNull(shortestPath.pathTo(1));
    }

    private IWeightedGraph createGraphWithAlternativeRoutesAndUnreachableNode() {
        WeightedGraph g = new WeightedGraph();
        for (int i = 0; i <= 5; i++) {
            g.addNode(i, new Node(i, 0, 0));
        }

        g.addEdge(new Edge(0, 1, 4.0));
        g.addEdge(new Edge(0, 2, 1.0));
        g.addEdge(new Edge(2, 1, 1.0));
        g.addEdge(new Edge(1, 3, 1.0));
        g.addEdge(new Edge(2, 3, 5.0));
        g.addEdge(new Edge(3, 4, 3.0));
        g.addEdge(new Edge(1, 4, 10.0));

        return g;
    }

}