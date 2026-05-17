package de.unistuttgart.dsass2026.ex04.p2;



public class AVLTree<K extends Comparable<K>> implements IAVLTree<K> {
    private AVLNode<K> root = null;

    @Override
    public AVLNode<K> getRootNode() {
        return root;
    }

    @Override
    public void setRootNode(AVLNode<K> node) {
        root = node;
    }

    @Override
    public void insert(K k) {
        if (root == null)
            root = new AVLNode<>(k);
        else
            root = insertRecursive(root, k);
    }

    private AVLNode<K> insertRecursive(AVLNode<K> n, K k) {
        int cmp = n.getKey().compareTo(k);
        if (cmp == 0) {
            // Schlüssel bereits vorhanden. Es ist nichts zu tun.
        } else if (cmp < 0) {
            // Rechts einfügen
            if (n.getRight() != null) {
                // Im rechten Teilbaum einfügen (rekursiv)
                n.setRight(insertRecursive(n.getRight(), k));
            } else {
                // Neuen Blattknoten rechts erzeugen
                n.setRight(new AVLNode<>(k));
            }
            // Höhe des rechten Teilbaums könnte sich verändert haben
            n.updateHeight();
            // Ggf. rebalancieren
            n = rebalance(n);
        } else {
            // Links einfügen (symmetrisch)
            if (n.getLeft() != null) {
                // Im linken Teilbaum einfügen (rekursiv)
                n.setLeft(insertRecursive(n.getLeft(), k));
            } else {
                // Neuen Blattknoten links erzeugen
                n.setLeft(new AVLNode<>(k));
            }
            // Höhe des linken Teilbaums könnte sich verändert haben
            n.updateHeight();
            // Ggf. rebalancieren
            n = rebalance(n);
        }

        return n;
    }

    @Override
    public void remove(K k) {
        root = removeRecursive(root, k);
    }

    private AVLNode<K> removeRecursive(AVLNode<K> n, K k) {
        if (n == null)
            return null;

        int cmp = n.getKey().compareTo(k);
        if (cmp < 0) {
            n.setRight(removeRecursive(n.getRight(), k));
        } else if (cmp > 0) {
            n.setLeft(removeRecursive(n.getLeft(), k));
        } else {
            if (n.getLeft() == null)
                return n.getRight();
            if (n.getRight() == null)
                return n.getLeft();

            AVLNode<K> successor = leftmostNode(n.getRight());
            n.setKey(successor.getKey());
            n.setRight(removeRecursive(n.getRight(), successor.getKey()));
        }

        n.updateHeight();
        return rebalance(n);
    }

    private AVLNode<K> leftmostNode(AVLNode<K> n) {
        AVLNode<K> current = n;
        while (current.getLeft() != null)
            current = current.getLeft();
        return current;
    }

    @Override
    public AVLNode<K> rebalance(AVLNode<K> n) {
        if (n == null)
            return null;

        int balance = n.getBalance();

        if (balance == 2) {
            if (n.getLeft().getBalance() >= 0) {
                return rotateRight(n);
            }
            n.setLeft(rotateLeft(n.getLeft()));
            return rotateRight(n);
        }

        if (balance == -2) {
            if (n.getRight().getBalance() <= 0) {
                return rotateLeft(n);
            }
            n.setRight(rotateRight(n.getRight()));
            return rotateLeft(n);
        }

        return n;
    }



    private AVLNode<K> rotateLeft(AVLNode<K> n) {
        AVLNode<K> tmp = n.getRight();
        n.setRight(tmp.getLeft());
        n.updateHeight();
        tmp.setLeft(n);
        tmp.updateHeight();
        return tmp;
    }

    private AVLNode<K> rotateRight(AVLNode<K> n) {
        AVLNode<K> tmp = n.getLeft();
        n.setLeft(tmp.getRight());
        n.updateHeight();
        tmp.setRight(n);
        tmp.updateHeight();
        return tmp;
    }
}