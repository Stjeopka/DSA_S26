package de.unistuttgart.dsass2026.ex08.p2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

public class MinimalSpanningTree {
    /**
     * This method calculates the minimal spanning tree (MST) using the Kruskal
     * algorithm.
     * 
     * @param graph the graph object to calculate the MST for
     * @return a set of edges, which belong to the MST of the given graph
     */
    public static Set<IEdge> kruskal(IWeightedGraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException();
        }

        Set<IEdge> mstEdges = new HashSet<>();
        if (graph.numberOfNodes() == 0) {
            return mstEdges;
        }

        HashMap<Long, Long> parent = new HashMap<>();
        HashMap<Long, Integer> rank = new HashMap<>();

        Iterator<Long> nodeIDIterator = graph.nodeIDIterator();
        while (nodeIDIterator.hasNext()) {
            long nodeID = nodeIDIterator.next();
            parent.put(nodeID, nodeID);
            rank.put(nodeID, 0);
        }

        PriorityQueue<IEdge> edgesByWeight = new PriorityQueue<>();
        Iterator<IEdge> edgeIterator = graph.edgeIterator();
        while (edgeIterator.hasNext()) {
            edgesByWeight.add(edgeIterator.next());
        }

        int neededEdges = Math.max(0, graph.numberOfNodes() - 1);
        while (!edgesByWeight.isEmpty() && mstEdges.size() < neededEdges) {
            IEdge edge = edgesByWeight.poll();
            long src = edge.getSource();
            long dst = edge.getDestination();

            if (!parent.containsKey(src) || !parent.containsKey(dst)) {
                continue;
            }

            long rootSrc = find(parent, src);
            long rootDst = find(parent, dst);

            if (rootSrc != rootDst) {
                mstEdges.add(edge);
                union(parent, rank, rootSrc, rootDst);
            }
        }

        return mstEdges;

    }

    private static long find(HashMap<Long, Long> parent, long nodeID) {
        long currentParent = parent.get(nodeID);
        if (currentParent != nodeID) {
            long representative = find(parent, currentParent);
            parent.put(nodeID, representative);
            return representative;
        }

        return currentParent;
    }

    private static void union(HashMap<Long, Long> parent, HashMap<Long, Integer> rank, long rootA, long rootB) {
        int rankA = rank.get(rootA);
        int rankB = rank.get(rootB);

        if (rankA < rankB) {
            parent.put(rootA, rootB);
            return;
        }

        if (rankA > rankB) {
            parent.put(rootB, rootA);
            return;
        }

        parent.put(rootB, rootA);
        rank.put(rootA, rankA + 1);

    }

}