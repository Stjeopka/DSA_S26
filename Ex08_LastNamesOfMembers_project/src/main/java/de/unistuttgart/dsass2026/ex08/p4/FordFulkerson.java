package de.unistuttgart.dsass2026.ex08.p4;

import java.util.ArrayList;

public class FordFulkerson implements IFordFulkerson{

    private ArrayList<ArrayList<Integer>> maxFlowMatrix;

    @Override
    public int calculate(ArrayList<ArrayList<Integer>> graph, int s, int t) {
        validateAdjacencyMatrix(graph);

        int n = graph.size();
        if (s < 0 || s >= n || t < 0 || t >= n) {
            throw new IllegalArgumentException();
        }

        ArrayList<ArrayList<Integer>> residual = copyMatrix(graph);
        this.maxFlowMatrix = createZeroMatrix(n);

        if (s == t) {
            return 0;
        }

        int maxFlow = 0;

        while (true) {
            int[] predecessor = TreeTraversal.dfs(residual, s);
            if (predecessor[t] == -1) {
                break;
            }

            int pathFlow = Integer.MAX_VALUE;
            int node = t;

            while (node != s) {
                int previous = predecessor[node];
                int residualCapacity = residual.get(previous).get(node);
                pathFlow = Math.min(pathFlow, residualCapacity);
                node = previous;
            }

            node = t;
            while (node != s) {
                int previous = predecessor[node];

                residual.get(previous).set(node, residual.get(previous).get(node) - pathFlow);
                residual.get(node).set(previous, residual.get(node).get(previous) + pathFlow);

                maxFlowMatrix.get(previous).set(node, maxFlowMatrix.get(previous).get(node) + pathFlow);
                maxFlowMatrix.get(node).set(previous, maxFlowMatrix.get(node).get(previous) - pathFlow);

                node = previous;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;

    }

    @Override
    public ArrayList<ArrayList<Integer>> flow() {
        if (this.maxFlowMatrix == null) {
            return new ArrayList<>();
        }

        return copyMatrix(this.maxFlowMatrix);

    }

    private static void validateAdjacencyMatrix(ArrayList<ArrayList<Integer>> graph) {
        if (graph == null) {
            throw new IllegalArgumentException();
        }

        int n = graph.size();
        for (ArrayList<Integer> row : graph) {
            if (row == null || row.size() != n) {
                throw new IllegalArgumentException();
            }
        }
    }

    private static ArrayList<ArrayList<Integer>> copyMatrix(ArrayList<ArrayList<Integer>> matrix) {
        ArrayList<ArrayList<Integer>> copy = new ArrayList<>();
        for (ArrayList<Integer> row : matrix) {
            copy.add(new ArrayList<>(row));
        }
        return copy;
    }

    private static ArrayList<ArrayList<Integer>> createZeroMatrix(int size) {
        ArrayList<ArrayList<Integer>> zeroMatrix = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(0);
            }
            zeroMatrix.add(row);
        }

        return zeroMatrix;

    }

}