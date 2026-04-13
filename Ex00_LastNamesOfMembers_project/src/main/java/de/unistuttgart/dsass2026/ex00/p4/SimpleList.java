package de.unistuttgart.dsass2026.ex00.p4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A simple list implementation backed by an {@link java.util.ArrayList}.
 * Supports sequential and step-based iteration via {@link SimpleListIterator}
 * and {@link SimpleListSkippingIterator}.
 *
 * @param <T> The type of elements stored in this list, must be {@link Comparable}
 */
public class SimpleList<T extends Comparable<T>> implements ISimpleListIterable<T> {

    /** Do not modify the existing methods and signatures! */
    private final List<T> list;

    public SimpleList() {
        list = new ArrayList<T>();
    }

    @Override
    public void append(T element) {
        list.add(element);
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public T getElement(int index) {
        return list.get(index);
    }

    /** {@inheritDoc} */
    @Override
    public Iterator<T> iterator() {
        return new SimpleListIterator();
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException if {@code stepSize} is less than 1
     */
    @Override
    public Iterator<T> skippingIterator(int stepSize) {
        if (stepSize < 1) {
            throw new IllegalArgumentException("stepSize must be at least 1");
        }
        return new SimpleListSkippingIterator(stepSize);
    }

    /**
     * An iterator that enumerates every element in the list in order,
     * starting at index 0.
     */
    private class SimpleListIterator implements Iterator<T> {

        /** The index of the next element to return. */
        private int currentIndex;

        /**
         * Constructs a {@code SimpleListIterator} starting at index 0.
         */
        public SimpleListIterator() {
            this.currentIndex = 0;
        }

        /**
         * Returns {@code true} if there are more elements to iterate over.
         *
         * @return {@code true} if the next call to {@link #next()} will return an element
         */
        @Override
        public boolean hasNext() {
            return currentIndex < getSize();
        }

        /**
         * Returns the next element in the list and advances the cursor.
         *
         * @return the next element
         * @throws NoSuchElementException if there are no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return getElement(currentIndex++);
        }

        /**
         * Not supported. Always throws {@link UnsupportedOperationException}.
         *
         * @throws UnsupportedOperationException always
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * An iterator that enumerates every n-th element in the list,
     * starting at index 0 and advancing by {@code stepSize} on each step.
     */
    private class SimpleListSkippingIterator implements Iterator<T> {

        /** The index of the next element to return. */
        private int currentIndex;

        /** The number of positions to advance after each call to {@link #next()}. */
        private final int stepSize;

        /**
         * Constructs a {@code SimpleListSkippingIterator} starting at index 0.
         *
         * @param stepSize the number of positions to advance per step (must be &ge; 1)
         */
        public SimpleListSkippingIterator(int stepSize) {
            this.currentIndex = 0;
            this.stepSize = stepSize;
        }

        /**
         * Returns {@code true} if there are more elements to iterate over.
         *
         * @return {@code true} if the next call to {@link #next()} will return an element
         */
        @Override
        public boolean hasNext() {
            return currentIndex < getSize();
        }

        /**
         * Returns the element at the current index and advances the cursor by {@code stepSize}.
         *
         * @return the element at the current index
         * @throws NoSuchElementException if there are no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T element = getElement(currentIndex);
            currentIndex += stepSize;
            return element;
        }

        /**
         * Not supported. Always throws {@link UnsupportedOperationException}.
         *
         * @throws UnsupportedOperationException always
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


}