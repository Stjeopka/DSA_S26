package de.unistuttgart.dsass2026.ex08.p2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class WeightedGraph implements IWeightedGraph {

    private int numEdges;
    private HashMap<Long, ArrayList<IEdge>> adjacencyList;
    private HashMap<Long, Node> nodeMap;

    /**
     * Initializes an empty graph without nodes or edges.
     */
    public WeightedGraph() {
        this.numEdges = 0;
        this.adjacencyList = new HashMap<>();
        this.nodeMap = new HashMap<>();
    }

    @Override
    public int numberOfNodes() {
        return this.adjacencyList.size();
    }

    @Override
    public int numberOfEdges() {
        return this.numEdges;
    }

    @Override
    public void addNode(long nodeID, Node node) {
        if (this.nodeMap.containsKey(nodeID))
            throw new IllegalArgumentException();
        this.adjacencyList.put(nodeID, new ArrayList<>());
        this.nodeMap.put(nodeID, node);
    }

    @Override
    public void setNode(long nodeID, Node node) {
        if (!this.nodeMap.containsKey(nodeID))
            throw new IllegalArgumentException();
        // no change in adjacencyList, because we keep all the edges and just replace
        // the node.
        this.nodeMap.put(nodeID, node);
    }

    @Override
    public Node getNode(long nodeID) {
        return this.nodeMap.get(nodeID);
    }

    @Override
    public void addEdge(IEdge edge) {
        long src = edge.getSource();
        long dst = edge.getDestination();
        if (!this.nodeMap.containsKey(src) || !this.nodeMap.containsKey(dst))
            throw new IllegalArgumentException();
        this.adjacencyList.get(src).add(edge);
        this.numEdges++;
    }

    @Override
    public Iterator<IEdge> edgeIterator() {
        ArrayList<IEdge> edges = new ArrayList<>(numEdges);
        for (ArrayList<IEdge> outgoingEdges : this.adjacencyList.values()) {
            edges.addAll(outgoingEdges);
        }
        return edges.iterator();
    }

    @Override
    public Iterator<IEdge> outgoingEdges(long src) {
        ArrayList<IEdge> edges = this.adjacencyList.get(src);
        if (edges == null) {
            throw new IllegalArgumentException();
        }
        return edges.iterator();
    }

    @Override
    public Iterator<Node> nodeIterator() {
        return nodeMap.values().iterator();
    }

    @Override
    public Iterator<Long> nodeIDIterator() {
        return nodeMap.keySet().iterator();
    }

}