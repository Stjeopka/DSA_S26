package de.unistuttgart.dsass2026.ex07.p4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


public class ShortestPath implements IShortestPath {

    private final IWeightedGraph graph;
    private final long startNode;
    private final Map<Long, IEdge> edgeTo;

    /**
     * Initializes the shortest path for the given weighted graph using the given start
     * node. Immediately calls the dijkstra(graph, startNode) method to execute the
     * Dijkstra algorithm.
     *
     * @param graph     the weighted graph
     * @param startNode the starting node
     */
    public ShortestPath(IWeightedGraph graph, long startNode) {
        this.graph = graph;
        this.startNode = startNode;
        this.edgeTo = new HashMap<>();
        dijkstra(this.graph, this.startNode);
    }

    @Override
    public void dijkstra(IWeightedGraph graph, long startNode) {
        edgeTo.clear();

        Iterator<Node> nodeIterator = graph.nodeIterator();
        while (nodeIterator.hasNext()) {
            nodeIterator.next().setDistance(Double.POSITIVE_INFINITY);
        }

        Node start = graph.getNode(startNode);
        if (start == null) {
            return;
        }

        start.setDistance(0.0);
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Set<Long> visited = new HashSet<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (!visited.add(current.getID())) {
                continue;
            }

            Iterator<IEdge> outgoing = graph.outgoingEdges(current.getID());
            while (outgoing.hasNext()) {
                IEdge edge = outgoing.next();
                Node destination = graph.getNode(edge.getDestination());
                if (destination == null || visited.contains(destination.getID())) {
                    continue;
                }

                double newDistance = current.getDistance() + edge.getWeight();
                if (newDistance < destination.getDistance()) {
                    destination.setDistance(newDistance);
                    edgeTo.put(destination.getID(), edge);

                    // PriorityQueue has no decrease-key operation.
                    queue.remove(destination);
                    queue.add(destination);
                }
            }
        }

    }

    @Override
    public double distanceTo(long node) {
        Node destination = graph.getNode(node);
        if (destination == null) {
            return Double.POSITIVE_INFINITY;
        }
        return destination.getDistance();

    }

    @Override
    public Iterable<IEdge> pathTo(long node) {
        if (!existsPathTo(node)) {
            return null;
        }

        LinkedList<IEdge> path = new LinkedList<>();
        long current = node;

        while (current != startNode) {
            IEdge edge = edgeTo.get(current);
            if (edge == null) {
                return null;
            }
            path.addFirst(edge);
            current = edge.getSource();
        }

        return path;

    }

    @Override
    public boolean existsPathTo(long node) {
        return this.distanceTo(node) < Double.POSITIVE_INFINITY;
    }

}