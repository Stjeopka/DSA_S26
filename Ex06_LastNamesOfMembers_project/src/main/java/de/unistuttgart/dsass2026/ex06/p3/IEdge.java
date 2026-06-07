package de.unistuttgart.dsass2026.ex06.p3;

public interface IEdge {

  /**
   * @return the tail (source) node of the directed edge
   */
  public int getSource();

  /**
   * @return the head (destination) node of the directed edge
   */
  public int getDestination();

}