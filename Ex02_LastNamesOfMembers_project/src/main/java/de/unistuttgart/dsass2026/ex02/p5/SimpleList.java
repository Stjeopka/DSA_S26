package de.unistuttgart.dsass2026.ex02.p5;

public class SimpleList<T extends Comparable<T>> implements ISimpleList<T> {

    private ISimpleListNode<T> head;
    private int size;

    public SimpleList() {
        this.head = null;
        this.size = 0;
    }


    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void prepend(T element) {
        ISimpleListNode<T> node = new SimpleListNode<>(element);
        node.setNext(head);
        head = node;
        size++;
    }

    @Override
    public T getElement(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        ISimpleListNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getElement();
    }

    @Override
    public void sort() {
        if (size < 2) {
            return;
        }

        boolean swapped;
        ISimpleListNode<T> sortedBoundary = null;

        do {
            swapped = false;
            ISimpleListNode<T> current = head;

            while (current.getNext() != sortedBoundary) {
                ISimpleListNode<T> next = current.getNext();
                if (current.getElement().compareTo(next.getElement()) > 0) {
                    T temp = current.getElement();
                    current.setElement(next.getElement());
                    next.setElement(temp);
                    swapped = true;
                }
                current = next;
            }

            sortedBoundary = current;
        } while (swapped);
    }

}