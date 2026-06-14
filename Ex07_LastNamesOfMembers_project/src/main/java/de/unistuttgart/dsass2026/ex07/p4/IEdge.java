package de.unistuttgart.dsass2026.ex07.p4;

public interface IEdge {

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