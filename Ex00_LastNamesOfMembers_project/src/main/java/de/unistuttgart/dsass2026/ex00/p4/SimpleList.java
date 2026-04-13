package de.unistuttgart.dsass2026.ex00.p4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Override
    public Iterator<T> iterator() {
        return new SimpleListIterator();
    }

    @Override
    public Iterator<T> skippingIterator(int stepSize) {
        if (stepSize < 1) {
            throw new IllegalArgumentException("stepSize must be at least 1");
        }
        return new SimpleListSkippingIterator(stepSize);
    }

    private class SimpleListIterator implements Iterator<T> {

        private int currentIndex;

        public SimpleListIterator() {
            this.currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < getSize();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return getElement(currentIndex++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class SimpleListSkippingIterator implements Iterator<T> {

        private int currentIndex;
        private final int stepSize;

        public SimpleListSkippingIterator(int stepSize) {
            this.currentIndex = 0;
            this.stepSize = stepSize;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < getSize();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T element = getElement(currentIndex);
            currentIndex += stepSize;
            return element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


}