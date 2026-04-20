package de.unistuttgart.dsass2026.ex01.p5;

/**
 * A simple list interface
 *
 * @param <T> The type of list element
 */
public interface ISimpleList<T extends Comparable<T>> {

    /**
     * Returns the size of the list.
     *
     * @return Size of the list
     */
    public int getSize();

    /**
     * Appends an element to the end of the list.
     *
     * @param element Element to be appended
     */
    public void append(T element);

    /**
     * Returns the element at the specified index.
     *
     * @param index Index of the element (starting from 0)
     * @throws IndexOutOfBoundsException if the given index is out of range
     */
    public T getElement(int index);

    /**
     * Swaps the specified elements at index i and j.
     *
     * @param i The index of the first element
     * @param j The index of the second element
     * @throws IndexOutOfBoundsException if the given indices are out of range
     */
    public void swapElements(int i, int j);
}