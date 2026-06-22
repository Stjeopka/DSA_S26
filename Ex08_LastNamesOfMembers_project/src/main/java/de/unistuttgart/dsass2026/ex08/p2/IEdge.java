package de.unistuttgart.dsass2026.ex08.p2;

public interface IEdge extends Comparable<IEdge> {

  /**
   * @return the tail (source) node of the directed edge
   */
  public long getSource();

  /**
   * @return the head (destination) node of the directed edge
   */
  public long getDestination();

  /**
   * @return the weight of the directed edge
   */
  public double getWeight();

}