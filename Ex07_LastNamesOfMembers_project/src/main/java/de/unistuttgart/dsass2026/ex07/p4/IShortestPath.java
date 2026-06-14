package de.unistuttgart.dsass2026.ex07.p4;

public interface IShortestPath {

  /**
   * Computes the Dijkstra algorithm on the given weighted graph from the given start
   * node. This method is started after initializing a ShortPath object.
   *
   * @param graph     the weighted graph
   * @param startnode the start node ID
   */
  public void dijkstra(IWeightedGraph graph, long startnode);

  /**
   * Returns the distance of the shortest path from the start node to the given
   * destination node (as the sum of the weights of the edges on the shortest path).
   *
   * @param destination the destination node ID
   * @return the length (weight) of a shortest path from the start node to the
   *         destination node, or Double.POSITIVE_INFINITY if no such path exists.
   */
  public double distanceTo(long destination);

  /**
   * Returns the edges on the shortest path from the start node to the given destination
   * node.
   * Example: A shortest path s--->u--->x--->y exists, then the iterable's next() method
   * will return the edge s--->u, then the edge u--->x, and then the edge x--->y.
   *
   * @param destination the destination node ID
   * @return a shortest path from the start node the the destination node as an iterable
   *         of the edges, or null if no such path exists.
   */
  public Iterable<IEdge> pathTo(long destination);

  /**
   * Returns a boolean whether there is a path from the start node to the given
   * destination node.
   *
   * @param destination the destination node ID
   * @return true if there is a path from the start node to th destination node, or
   *         false otherwise.
   */
  public boolean existsPathTo(long destination);

}