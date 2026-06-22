package de.unistuttgart.dsass2026.ex08.p4;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Arrays;

public class TreeTraversal {

    /**
     * This method traverses the tree using depth first search (DFS). To eliminate any
     * ambiguity choosing the next node, the node with the smallest ID is visited next.
     *
     * @param weights adjacency matrix defining the graph. Since only you are
     *                using this method (when performing the ford-fulkerson algorithm),
     *                you can expect weights to be a rectangular array.
     * @param s the id of the node to start the DFS on
     * @return array with predecessors. I.e. pi[5] = 2 means, that the node with ID 2 is
     *         predecessor of the node with ID 5. For nodes x that don't have a
     *         predecessor (i.e., the start node and nodes which aren't reachable), the
     *         returned array contains the value -1 (i.e., pi[x] = -1).
     */
    public static int[] dfs(ArrayList<ArrayList<Integer>> weights, int s) throws IllegalArgumentException {
        validateAdjacencyMatrix(weights);

        int n = weights.size();
        if (s < 0 || s >= n) {
            throw new IllegalArgumentException();
        }

        int[] predecessor = new int[n];
        Arrays.fill(predecessor, -1);
        boolean[] visited = new boolean[n];

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(s);
        visited[s] = true;

        while (!stack.isEmpty()) {
            int current = stack.pop();
            ArrayList<Integer> row = weights.get(current);

            // Push in reverse order so that smaller node IDs are explored first.
            for (int next = n - 1; next >= 0; next--) {
                if (!visited[next] && row.get(next) > 0) {
                    visited[next] = true;
                    predecessor[next] = current;
                    stack.push(next);
                }
            }
        }

        return predecessor;
    }

    private static void validateAdjacencyMatrix(ArrayList<ArrayList<Integer>> weights) {
        if (weights == null) {
            throw new IllegalArgumentException();
        }

        int n = weights.size();
        for (ArrayList<Integer> row : weights) {
            if (row == null || row.size() != n) {
                throw new IllegalArgumentException();
            }
        }
    }

}