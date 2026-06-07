package de.unistuttgart.dsass2026.ex06.p3;

import java.util.ArrayList;
import java.util.Iterator;

public class Graph<N> implements IGraph<N> {

    private int numEdges;
    private ArrayList<ArrayList<IEdge>> adjacencyList;

    /**
     * Initializes an empty graph without nodes or edges.
     */
    public Graph() {
        this.numEdges = 0;
        this.adjacencyList = new ArrayList<>();
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
    public Iterator<IEdge> edgeIterator() {
        ArrayList<IEdge> edges = new ArrayList<>(numEdges);
        for (ArrayList<IEdge> outgoingEdges : this.adjacencyList) {
            edges.addAll(outgoingEdges);
        }
        return edges.iterator();
    }

    @Override
    public Iterator<IEdge> outgoingEdges(int src) {
        return this.adjacencyList.get(src).iterator();
    }

    public int addNode() {
        this.adjacencyList.add(new ArrayList<>());
        return this.adjacencyList.size() - 1;
    }

    public void addEdge(int src, int dest) {
        if (src < 0 || src >= numberOfNodes() || dest < 0 || dest >= numberOfNodes())
            throw new IllegalArgumentException();
        this.adjacencyList.get(src).add(new Edge(src, dest));
        this.numEdges++;
    }

    @Override
    public void createFromEdgeList(ArrayList<Integer> list) throws UnsupportedOperationException {
        if (numberOfNodes() != 0 || numberOfEdges() != 0)
            throw new UnsupportedOperationException();
        if (list == null)
            throw new IllegalArgumentException();

        if (list.isEmpty())
            return;

        // The first two values are the number of nodes and edges.
        int numberOfNodes = getNumberOfNodesFromEdgeList(list);

        // The actual edges start at index 2 and are saved as source/destination pairs.
        for (int i = 2; i < list.size(); i += 2)
            validateNodeIndex(list.get(i), numberOfNodes);
        for (int i = 3; i < list.size(); i += 2)
            validateNodeIndex(list.get(i), numberOfNodes);

        // Add all nodes first, because addEdge only works for existing node indices.
        for (int i = 0; i < numberOfNodes; i++)
            addNode();
        for (int i = 2; i < list.size(); i += 2)
            addEdge(list.get(i), list.get(i + 1));
    }

    @Override
    public ArrayList<Integer> toEdgeList() {
        // Edge list format: number of nodes, number of edges, then all edge pairs.
        ArrayList<Integer> list = new ArrayList<>(2 + 2 * numberOfEdges());
        list.add(numberOfNodes());
        list.add(numberOfEdges());

        Iterator<IEdge> iterator = edgeIterator();
        while (iterator.hasNext()) {
            IEdge edge = iterator.next();
            list.add(edge.getSource());
            list.add(edge.getDestination());
        }
        return list;
    }

    @Override
    public void createFromNodeList(ArrayList<Integer> list) throws UnsupportedOperationException {
        if (numberOfNodes() != 0 || numberOfEdges() != 0)
            throw new UnsupportedOperationException();
        if (list == null)
            throw new IllegalArgumentException();

        NodeListData nodeList = parseNodeList(list);

        // The node list only gives offsets and destinations, so the source is the current node.
        for (int i = 0; i < nodeList.numberOfNodes; i++)
            addNode();
        for (int src = 0; src < nodeList.numberOfNodes; src++) {
            for (int i = nodeList.offsets[src]; i < nodeList.offsets[src + 1]; i++)
                addEdge(src, nodeList.destinations.get(i));
        }
    }

    @Override
    public ArrayList<Integer> toNodeList() {
        // Node list format: header, offsets for each node, then all destinations.
        ArrayList<Integer> list = new ArrayList<>(2 + numberOfNodes() + 1 + numberOfEdges());
        list.add(numberOfNodes());
        list.add(numberOfEdges());

        // Offsets are prefix sums over the outgoing edge counts.
        int nextEdgeIndex = 0;
        list.add(nextEdgeIndex);
        for (ArrayList<IEdge> outgoingEdges : this.adjacencyList) {
            nextEdgeIndex += outgoingEdges.size();
            list.add(nextEdgeIndex);
        }

        // The sources are already encoded by the offsets, so only destinations are stored here.
        Iterator<IEdge> iterator = edgeIterator();
        while (iterator.hasNext())
            list.add(iterator.next().getDestination());
        return list;
    }

    private int getNumberOfNodesFromEdgeList(ArrayList<Integer> list) {
        // A valid edge list needs the two header values before any edge data.
        if (list.size() < 2 || list.get(0) == null || list.get(1) == null)
            throw new IllegalArgumentException();

        int numberOfNodes = list.get(0);
        int numberOfEdges = list.get(1);
        if (numberOfNodes < 0 || numberOfEdges < 0)
            throw new IllegalArgumentException();
        if (list.size() != 2 + 2 * numberOfEdges)
            throw new IllegalArgumentException();
        return numberOfNodes;
    }

    private NodeListData parseNodeList(ArrayList<Integer> list) {
        // Empty input is accepted as an empty graph.
        if (list.isEmpty())
            return new NodeListData(0, new int[] { 0 }, new ArrayList<>());
        if (list.size() < 3 || list.get(0) == null || list.get(1) == null)
            throw new IllegalArgumentException();

        int numberOfNodes = list.get(0);
        int numberOfEdges = list.get(1);
        if (numberOfNodes < 0 || numberOfEdges < 0)
            throw new IllegalArgumentException();
        if (list.size() != 2 + numberOfNodes + 1 + numberOfEdges)
            throw new IllegalArgumentException();

        // After the header come n + 1 offsets, followed by exactly m destinations.
        int[] offsets = readAndValidateOffsets(list, 2, numberOfNodes, numberOfEdges);
        ArrayList<Integer> destinations = new ArrayList<>(numberOfEdges);
        int destinationStartIndex = 2 + numberOfNodes + 1;
        for (int i = 0; i < numberOfEdges; i++) {
            Integer destination = list.get(destinationStartIndex + i);
            validateNodeIndex(destination, numberOfNodes);
            destinations.add(destination);
        }
        return new NodeListData(numberOfNodes, offsets, destinations);
    }

    private int[] readAndValidateOffsets(ArrayList<Integer> list, int startIndex, int numberOfNodes, int numberOfEdges) {
        int[] offsets = new int[numberOfNodes + 1];
        for (int i = 0; i <= numberOfNodes; i++) {
            Integer offset = list.get(startIndex + i);
            // Offsets must stay inside the destination array and must not go backwards.
            if (offset == null || offset < 0 || offset > numberOfEdges)
                throw new IllegalArgumentException();
            if (i > 0 && offset < offsets[i - 1])
                throw new IllegalArgumentException();
            offsets[i] = offset;
        }
        if (offsets[0] != 0 || offsets[numberOfNodes] != numberOfEdges)
            throw new IllegalArgumentException();
        return offsets;
    }

    private void validateNodeIndex(Integer nodeIndex, int numberOfNodes) {
        if (nodeIndex == null || nodeIndex < 0 || nodeIndex >= numberOfNodes)
            throw new IllegalArgumentException();
    }

    private class NodeListData {
        private final int numberOfNodes;
        private final int[] offsets;
        private final ArrayList<Integer> destinations;

        private NodeListData(int numberOfNodes, int[] offsets, ArrayList<Integer> destinations) {
            this.numberOfNodes = numberOfNodes;
            this.offsets = offsets;
            this.destinations = destinations;
        }
    }

}