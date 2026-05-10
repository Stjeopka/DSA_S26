package de.unistuttgart.dsass2026.ex03.p5;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<T>> implements IBinarySearchTreeIterable<T> {

    private IBinaryTreeNode<T> root;

    public BinarySearchTree() {
        this.root = null;
    }

    @Override
    public void insert(T t) {
        this.root = this.insert(this.root, t);
    }

    private IBinaryTreeNode<T> insert(IBinaryTreeNode<T> node, T t) {
        if (node == null) {
            IBinaryTreeNode<T> newNode = new BinaryTreeNode<>();
            newNode.setValue(t);
            return newNode;
        }
        if (t.compareTo(node.getValue()) < 0) {
            node.setLeftChild(this.insert(node.getLeftChild(), t));
        } else if (t.compareTo(node.getValue()) > 0) {
            node.setRightChild(this.insert(node.getRightChild(), t));
        }
        return node;
    }

    @Override
    public IBinaryTreeNode<T> getRootNode() {
        return this.root;
    }

    @Override
    public boolean isFull() {
        return this.isFull(this.root);
    }

    private boolean isFull(IBinaryTreeNode<T> node) {
        if (node == null) {
            return true;
        }

        IBinaryTreeNode<T> left = node.getLeftChild();
        IBinaryTreeNode<T> right = node.getRightChild();

        if ((left == null) != (right == null)) {
            return false;
        }

        return this.isFull(left) && this.isFull(right);
    }

    @Override
    public Iterator<T> iterator(TreeTraversalType traversalType) {
        if (traversalType == null) {
            throw new IllegalArgumentException("traversalType must not be null");
        }

        switch (traversalType) {
            case PREORDER:
                return new PreOrderIterator();
            case INORDER:
                return new InOrderIterator();
            case POSTORDER:
                return new PostOrderIterator();
            case LEVELORDER:
                return new LevelOrderIterator();
            default:
                throw new IllegalArgumentException("Unknown traversal type: " + traversalType);
        }
    }

    private class PreOrderIterator implements Iterator<T> {

        private final Stack<IBinaryTreeNode<T>> stack;

        public PreOrderIterator() {
            this.stack = new Stack<>();
            if (root != null) {
                this.stack.push(root);
            }
        }

        @Override
        public boolean hasNext() {
            return !this.stack.isEmpty();
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }

            IBinaryTreeNode<T> currentNode = this.stack.pop();
            if (currentNode.getRightChild() != null) {
                this.stack.push(currentNode.getRightChild());
            }
            if (currentNode.getLeftChild() != null) {
                this.stack.push(currentNode.getLeftChild());
            }
            return currentNode.getValue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class InOrderIterator implements Iterator<T> {

        private final Stack<IBinaryTreeNode<T>> stack;
        private IBinaryTreeNode<T> currentNode;

        public InOrderIterator() {
            this.stack = new Stack<>();
            this.currentNode = root;
        }

        @Override
        public boolean hasNext() {
            return this.currentNode != null || !this.stack.isEmpty();
        }

        @Override
        public T next() {
            while (this.currentNode != null) {
                this.stack.push(this.currentNode);
                this.currentNode = this.currentNode.getLeftChild();
            }

            if (this.stack.isEmpty()) {
                throw new NoSuchElementException();
            }

            IBinaryTreeNode<T> nextNode = this.stack.pop();
            this.currentNode = nextNode.getRightChild();
            return nextNode.getValue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class PostOrderIterator implements Iterator<T> {

        private final Stack<IBinaryTreeNode<T>> stack;

        public PostOrderIterator() {
            this.stack = new Stack<>();
            Stack<IBinaryTreeNode<T>> tempStack = new Stack<>();

            if (root != null) {
                tempStack.push(root);
            }

            while (!tempStack.isEmpty()) {
                IBinaryTreeNode<T> node = tempStack.pop();
                this.stack.push(node);

                if (node.getLeftChild() != null) {
                    tempStack.push(node.getLeftChild());
                }
                if (node.getRightChild() != null) {
                    tempStack.push(node.getRightChild());
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !this.stack.isEmpty();
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.stack.pop().getValue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class LevelOrderIterator implements Iterator<T> {

        private final Queue<IBinaryTreeNode<T>> queue;

        public LevelOrderIterator() {
            this.queue = new LinkedList<>();
            if (root != null) {
                this.queue.offer(root);
            }
        }

        @Override
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }

            IBinaryTreeNode<T> currentNode = this.queue.poll();

            if (currentNode.getLeftChild() != null) {
                this.queue.offer(currentNode.getLeftChild());
            }
            if (currentNode.getRightChild() != null) {
                this.queue.offer(currentNode.getRightChild());
            }

            return currentNode.getValue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


}