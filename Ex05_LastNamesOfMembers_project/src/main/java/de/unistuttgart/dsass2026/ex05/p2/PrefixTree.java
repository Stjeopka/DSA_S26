package de.unistuttgart.dsass2026.ex05.p2;

public class PrefixTree implements IPrefixTree {

    private IPrefixTreeNode root;
    private int size;

    public PrefixTree() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public void insert(String word) {
        if (word == null) {
            return;
        }
        // special case: empty tree -> create root with empty prefix
        if (this.root == null) {
            this.root = new PrefixTreeNode();
            this.root.setPrefix("");
        }
        insertRec(this.root, word);
        this.size++;
    }

    /**
     * Walks down the tree along the characters of the remaining word
     * and creates new nodes whenever an edge is missing.
     */
    private void insertRec(IPrefixTreeNode node, String remaining) {
        if (remaining.isEmpty()) {
            return;
        }
        char label = remaining.charAt(0);
        IPrefixTreeNode child = node.getChild(label);
        if (child == null) {
            child = new PrefixTreeNode();
            child.setPrefix(node.getPrefix() + label);
            node.setChild(label, child);
        }
        insertRec(child, remaining.substring(1));
    }

    @Override
    public int size() {
        return this.size;
    }

}