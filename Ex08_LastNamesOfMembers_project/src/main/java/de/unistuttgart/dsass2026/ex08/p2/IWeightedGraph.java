package de.unistuttgart.dsass2026.ex08.p2;

import java.util.Iterator;

public interface IWeightedGraph extends Iterable<IEdge> {

    /**
     * @return the number of nodes in the weighted graph
     */
    public int numberOfNodes();

    /**
     * @return the number of edges in the weighted graph
     */
    public int numberOfEdges();

    /**
     * Adds a new node object with the specified ID
     * @param nodeID
     * @param node the node to add
     */
    public void addNode(long nodeID, Node node);

    /**
     * Replaces the node object with the specified ID
     * @param nodeID ID of the node
     * @param node    the new node object
     */
    public void setNode(long nodeID, Node node);

    /**
     * Returns the node object with the specified ID, or null if the node doesn't exist.
     * @param nodeID id of the node
     * @return node object
     */
    public Node getNode(long nodeID);

    /**
     * Adds an Edge to the graph
     * @param edge the edge object to add
     */
    public void addEdge(IEdge edge);

    /**
     * Returns an iterator for all edges in the weighted graph.
     *
     * @return an iterator that iterates through all edges in the weighted graph
     */
    public Iterator<IEdge> edgeIterator();

    /**
     * Returns an iterator for all outgoing edges of the given node.
     *
     * @param src the node id, for which to get outgoing edges
     * @return an iterator that iterates through all outgoing edges of the given node
     */
    public Iterator<IEdge> outgoingEdges(long src);

    /**
     * Returns an iterator for all the nodes in the weighted graph.
     *
     * @return an iterator that iterates through all nodes in the weighted graph
     */
    public Iterator<Node> nodeIterator();

    /**
     * Returns an iterator for all the node IDs in the weighted graph.
     *
     * @return an iterator that iterates through all node IDs in the weighted graph
     */
    public Iterator<Long> nodeIDIterator();


    @Override
    default Iterator<IEdge> iterator() {
        return edgeIterator();
    }
}