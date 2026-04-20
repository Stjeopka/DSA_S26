package de.unistuttgart.dsass2026.ex01.p5;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractSortCheckerList<T extends Comparable<T>> implements ISimpleList<T> {

    protected ArrayList<T> data = new ArrayList<>();

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public void append(T element) {
        data.add(element);
    }

    @Override
    public T getElement(int index) {
        return data.get(index);
    }

    @Override
    public void swapElements(int i, int j) {
        T tmp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, tmp);
    }
}