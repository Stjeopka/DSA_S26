package de.unistuttgart.dsass2026.ex06.p3;

public class Edge implements IEdge {
    private final int source;
    private final int destination;

    /**
     * Initializes a directed edge from node source to node dest.
     *
     * @param source the tail (source) node
     * @param dest   the head (destination) node
     * @throws java.lang.IndexOutOfBoundsException if either v or w is a negative integer
     */
    public Edge(int source, int dest) {
        if (source < 0)
            throw new IndexOutOfBoundsException("source node's name must be a non-negative integer");
        if (dest < 0)
            throw new IndexOutOfBoundsException("destination node's name must be a non-negative integer");
        this.source = source;
        this.destination = dest;
    }

    @Override
    public int getSource() {
        return source;
    }

    @Override
    public int getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return String.format("(%d --> %d)", getSource(), getDestination());
    }
}