package de.unistuttgart.dsass2026.ex07.p4;

public interface INode {

    /**
     * @return ID of the node
     */
    public long getID();


    /**
     * @return latitude of the node
     */
    public double getLatitude();


    /**
     * @return the longitude of the node
     */
    public double getLongitude();


    /**
     * Sets the distance of the node (in respect to the start node)
     *
     * @param dist the distance to the node
     */
    public void setDistance(double dist);


    /**
     * @return distance of the node
     */
    public double getDistance();

}