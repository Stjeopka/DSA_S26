package de.unistuttgart.dsass2026.ex01.p5;

import java.util.ArrayList;
import java.util.List;

public class SimpleList<T extends Comparable<T>> implements ISimpleList<T> {

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
    public void swapElements(int i, int j) {
        T tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

}