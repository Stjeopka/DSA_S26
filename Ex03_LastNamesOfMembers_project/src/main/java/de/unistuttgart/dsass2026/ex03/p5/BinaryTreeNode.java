package de.unistuttgart.dsass2026.ex03.p5;

public class BinaryTreeNode<T extends Comparable<T>> implements IBinaryTreeNode<T> {

    private T value;
    private IBinaryTreeNode<T> leftChild;
    private IBinaryTreeNode<T> rightChild;


    public BinaryTreeNode() {
        this.value = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    @Override
    public void setValue(T val) {
        this.value = val;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setLeftChild(IBinaryTreeNode<T> left) {
        this.leftChild = left;
    }

    @Override
    public IBinaryTreeNode<T> getLeftChild() {
        return this.leftChild;
    }

    @Override
    public void setRightChild(IBinaryTreeNode<T> right) {
        this.rightChild = right;
    }

    @Override
    public IBinaryTreeNode<T> getRightChild() {
        return this.rightChild;
    }


}