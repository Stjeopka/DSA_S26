package de.unistuttgart.dsass2026.ex02.p5;

/**
 * This interface defines the necessary methods a class must have to work as a
 * node for a linked list. I.e., a class implementing this interface stores its
 * corresponding element of type T, as well as the next node in the list and
 * allows access via the getter and setter methods.
 *
 * @param <T> The type of list element
 */
public interface ISimpleListNode<T extends Comparable<T>> {

    /**
     * @return the element stored in this node
     */
    public T getElement();


    /**
     * @param element the element to store in this node
     */
    public void setElement(T element);


    /**
     * @return the next node in the list, or null if "this" is the last node
     */
    public ISimpleListNode<T> getNext();


    /**
     * Sets the "next node" which this node is pointing to
     * @param node the "next node" to point to
     */
    public void setNext(ISimpleListNode<T> node);
}