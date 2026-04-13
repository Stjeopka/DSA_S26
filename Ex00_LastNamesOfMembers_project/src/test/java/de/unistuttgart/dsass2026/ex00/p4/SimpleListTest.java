package de.unistuttgart.dsass2026.ex00.p4;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.Timeout;

public class SimpleListTest {

    @Rule
    public Timeout globalTimeout = new Timeout(5, TimeUnit.SECONDS);

    @Rule
    public TestName testName = new TestName();

    private static final String ANSI_GREEN  = "\033[32m";
    private static final String ANSI_CYAN   = "\033[36m";
    private static final String ANSI_DIM    = "\033[2m";
    private static final String ANSI_BOLD   = "\033[1m";
    private static final String ANSI_RESET  = "\033[0m";

    private SimpleList<Integer> createList(int... values) {
        SimpleList<Integer> list = new SimpleList<>();
        for (int v : values) {
            list.append(v);
        }
        return list;
    }

    private void log(String msg) {
        System.out.println(
            ANSI_GREEN + "[PASS]" + ANSI_RESET + "  "
            + ANSI_BOLD + testName.getMethodName() + ANSI_RESET
            + ANSI_DIM + " -- " + ANSI_RESET
            + ANSI_CYAN + msg + ANSI_RESET
        );
    }

    // =========================================================================
    //  Standard Iterator
    // =========================================================================

    @Test
    public void iterator_emptyList_hasNextReturnsFalse() {
        SimpleList<Integer> list = new SimpleList<>();
        Iterator<Integer> it = list.iterator();

        assertFalse("Empty list iterator should have no elements", it.hasNext());
        log("hasNext() correctly returns false for empty list");
    }

    @Test(expected = NoSuchElementException.class)
    public void iterator_emptyList_nextThrowsNoSuchElement() {
        log("Calling next() on empty list iterator, expecting NoSuchElementException");
        SimpleList<Integer> list = new SimpleList<>();
        list.iterator().next();
    }

    @Test
    public void iterator_multipleElements_returnsAllInOrder() {
        SimpleList<Integer> list = createList(10, 20, 30, 40, 50);
        Iterator<Integer> it = list.iterator();

        for (int i = 0; i < 5; i++) {
            assertTrue("Expected hasNext() == true at index " + i, it.hasNext());
            assertEquals("Wrong element at index " + i,
                    Integer.valueOf((i + 1) * 10), it.next());
        }
        assertFalse("Iterator should be exhausted after all elements", it.hasNext());
        log("Iterated all 5 elements [10, 20, 30, 40, 50] in correct order");
    }

    @Test
    public void iterator_singleElement_returnsOneElement() {
        SimpleList<Integer> list = createList(42);
        Iterator<Integer> it = list.iterator();

        assertTrue("Single-element list should have next", it.hasNext());
        assertEquals("Wrong element", Integer.valueOf(42), it.next());
        assertFalse("Iterator should be exhausted", it.hasNext());
        log("Single element [42] returned correctly, iterator exhausted");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void iterator_remove_throwsUnsupported() {
        log("Calling remove() on iterator, expecting UnsupportedOperationException");
        SimpleList<Integer> list = createList(1, 2, 3);
        Iterator<Integer> it = list.iterator();
        it.next();
        it.remove();
    }

    // =========================================================================
    //  Skipping Iterator – input validation
    // =========================================================================

    @Test(expected = IllegalArgumentException.class)
    public void skippingIterator_stepSizeZero_throwsIllegalArgument() {
        log("Creating skippingIterator(0), expecting IllegalArgumentException");
        createList(1, 2, 3).skippingIterator(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void skippingIterator_negativeStepSize_throwsIllegalArgument() {
        log("Creating skippingIterator(-1), expecting IllegalArgumentException");
        createList(1, 2, 3).skippingIterator(-1);
    }

    // =========================================================================
    //  Skipping Iterator – traversal
    // =========================================================================

    @Test
    public void skippingIterator_stepOne_behavesLikeNormalIterator() {
        SimpleList<Integer> list = createList(1, 2, 3, 4, 5);
        Iterator<Integer> it = list.skippingIterator(1);

        for (int i = 1; i <= 5; i++) {
            assertTrue("Expected hasNext() at element " + i, it.hasNext());
            assertEquals("Wrong element", Integer.valueOf(i), it.next());
        }
        assertFalse("Iterator should be exhausted", it.hasNext());
        log("stepSize=1: returned [1, 2, 3, 4, 5] -- same as normal iterator");
    }

    @Test
    public void skippingIterator_stepTwo_returnsEverySecondElement() {
        // [1, 2, 3, 4, 5] with step 2 -> 1, 3, 5
        SimpleList<Integer> list = createList(1, 2, 3, 4, 5);
        Iterator<Integer> it = list.skippingIterator(2);

        assertEquals("First element", Integer.valueOf(1), it.next());
        assertEquals("Second returned element (index 2)", Integer.valueOf(3), it.next());
        assertEquals("Third returned element (index 4)", Integer.valueOf(5), it.next());
        assertFalse("Iterator should be exhausted", it.hasNext());
        log("stepSize=2 on [1,2,3,4,5]: returned [1, 3, 5]");
    }

    @Test
    public void skippingIterator_stepThree_returnsEveryThirdElement() {
        // [10, 20, 30, 40, 50, 60, 70] with step 3 -> 10, 40, 70
        SimpleList<Integer> list = createList(10, 20, 30, 40, 50, 60, 70);
        Iterator<Integer> it = list.skippingIterator(3);

        assertEquals("Element at index 0", Integer.valueOf(10), it.next());
        assertEquals("Element at index 3", Integer.valueOf(40), it.next());
        assertEquals("Element at index 6", Integer.valueOf(70), it.next());
        assertFalse("Iterator should be exhausted", it.hasNext());
        log("stepSize=3 on [10,20,30,40,50,60,70]: returned [10, 40, 70]");
    }

    @Test
    public void skippingIterator_stepLargerThanSize_returnsOnlyFirstElement() {
        SimpleList<Integer> list = createList(1, 2, 3);
        Iterator<Integer> it = list.skippingIterator(10);

        assertTrue("Should have at least first element", it.hasNext());
        assertEquals("First element", Integer.valueOf(1), it.next());
        assertFalse("Should have no more elements", it.hasNext());
        log("stepSize=10 on [1,2,3]: returned only [1]");
    }

    @Test
    public void skippingIterator_emptyList_hasNextReturnsFalse() {
        SimpleList<Integer> list = new SimpleList<>();
        Iterator<Integer> it = list.skippingIterator(2);

        assertFalse("Empty list skipping iterator should have no elements", it.hasNext());
        log("hasNext() correctly returns false for empty list with stepSize=2");
    }

    @Test(expected = NoSuchElementException.class)
    public void skippingIterator_exhausted_nextThrowsNoSuchElement() {
        log("Calling next() on exhausted skipping iterator, expecting NoSuchElementException");
        SimpleList<Integer> list = createList(1);
        Iterator<Integer> it = list.skippingIterator(1);
        it.next();
        it.next(); // should throw
    }

    @Test(expected = UnsupportedOperationException.class)
    public void skippingIterator_remove_throwsUnsupported() {
        log("Calling remove() on skipping iterator, expecting UnsupportedOperationException");
        SimpleList<Integer> list = createList(1, 2, 3);
        Iterator<Integer> it = list.skippingIterator(2);
        it.next();
        it.remove();
    }
}