package de.unistuttgart.dsass2026.ex05.p2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PrefixTreeTest {

    @Test
    public void emptyTreeHasSizeZero() {
        PrefixTree tree = new PrefixTree();
        assertEquals(0, tree.size());
    }

    @Test
    public void sizeIncreasesOnInsert() {
        PrefixTree tree = new PrefixTree();
        tree.insert("hello");
        tree.insert("world");
        tree.insert("help");
        assertEquals(3, tree.size());
    }

    @Test
    public void nodePrefixesAreBuiltCorrectly() {
        PrefixTreeNode root = new PrefixTreeNode();
        root.setPrefix("");

        PrefixTreeNode child = new PrefixTreeNode();
        child.setPrefix("a");
        root.setChild('a', child);

        assertEquals("", root.getPrefix());
        assertEquals("a", child.getPrefix());
        assertNotNull(root.getChild('a'));
        assertNull(root.getChild('b'));
    }

    @Test
    public void labelsContainOutgoingEdges() {
        PrefixTreeNode node = new PrefixTreeNode();
        node.setChild('x', new PrefixTreeNode());
        node.setChild('y', new PrefixTreeNode());

        assertEquals(2, node.getLabels().size());
        assertTrue(node.getLabels().contains('x'));
        assertTrue(node.getLabels().contains('y'));
    }

    @Test
    public void removeChildrenClearsEdges() {
        PrefixTreeNode node = new PrefixTreeNode();
        node.setChild('a', new PrefixTreeNode());
        node.setChild('b', new PrefixTreeNode());
        node.removeChildren();
        assertTrue(node.getLabels().isEmpty());
    }

    @Test
    public void insertingSharedPrefixCountsBothWords() {
        PrefixTree tree = new PrefixTree();
        tree.insert("car");
        tree.insert("cat");
        assertEquals(2, tree.size());
    }

}