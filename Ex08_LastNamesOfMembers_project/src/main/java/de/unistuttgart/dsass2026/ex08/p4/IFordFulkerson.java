package de.unistuttgart.dsass2026.ex08.p4;

import java.util.ArrayList;

public interface IFordFulkerson {

    /**
     * This method calculates the max flow value for a graph given as an adjacency
     * matrix. The graph is not altered during the calculations.
     *
     * @param graph the graph as an adjacency matrix. This array must be rectangular.
     *              The entries of the adjacency matrix must be non-negative and specify
     *              the edge capacities.
     *
     *              (An zero-entry in the adjacency matrix can be interpreted as a
     *              non-existing edge or as an edge with capacity zero. Both of these
     *              interpretations are equivalent for the purpose of calculating the
     *              max flow.)
     *
     * @param s     ID of the source node
     * @param t     ID of the sink node
     * @return the max flow value of the graph
     * @throws IllegalArgumentException if @param graph is not rectangular
     */
    public int calculate(ArrayList<ArrayList<Integer>> graph, int s, int t) throws IllegalArgumentException;

    /**
     * This method returns the max flow as an adjacency matrix, i.e., an adjacency
     * matrix where each entry specifies the flow on the respective edge. The returned
     * flow is correct, i.e., it must adhere to the capacities (f(u,v) <= c(u,v)),
     * consistency (f(u,v) = -f(v,u)), and conservation of flow as defined in the
     * lecture.
     *
     * @return the max flow as an adjacency matrix
     */
    public ArrayList<ArrayList<Integer>> flow();

}