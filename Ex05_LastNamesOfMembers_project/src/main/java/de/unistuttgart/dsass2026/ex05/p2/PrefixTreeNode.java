package de.unistuttgart.dsass2026.ex05.p2;

import java.util.HashMap;
import java.util.Set;

public class PrefixTreeNode implements IPrefixTreeNode {

    private String prefix;
    private final HashMap<Character, IPrefixTreeNode> children;

    public PrefixTreeNode() {
        this.prefix = "";
        this.children = new HashMap<>();
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public Set<Character> getLabels() {
        return this.children.keySet();
    }

    @Override
    public void setChild(char label, IPrefixTreeNode node) {
        this.children.put(label, node);
    }

    @Override
    public IPrefixTreeNode getChild(char label) {
        return this.children.get(label);
    }

    @Override
    public void removeChildren() {
        this.children.clear();
    }

}