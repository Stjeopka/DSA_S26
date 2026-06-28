package de.unistuttgart.dsass2026.ex09.p4;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BoyerMooreTest {

	@Test
	public void findsPatternAtBeginning() {
		BoyerMoore matcher = new BoyerMoore("abc");

		assertEquals(0, matcher.search("abcdef"));
	}

	@Test
	public void findsPatternInMiddle() {
		BoyerMoore matcher = new BoyerMoore("needle");

		assertEquals(4, matcher.search("hay-needle-stack"));
	}

	@Test
	public void findsPatternAtEnd() {
		BoyerMoore matcher = new BoyerMoore("end");

		assertEquals(6, matcher.search("prefixend"));
	}

	@Test
	public void returnsMinusOneWhenPatternIsMissing() {
		BoyerMoore matcher = new BoyerMoore("xyz");

		assertEquals(-1, matcher.search("abcabcabc"));
	}

	@Test
	public void returnsMinusOneWhenPatternIsLongerThanText() {
		BoyerMoore matcher = new BoyerMoore("longpattern");

		assertEquals(-1, matcher.search("short"));
	}

	@Test
	public void emptyPatternMatchesAtZero() {
		BoyerMoore matcher = new BoyerMoore("");

		assertEquals(0, matcher.search("anything"));
		assertEquals(0, matcher.search(""));
	}

	@Test
	public void worksWithSingleCharacterPattern() {
		BoyerMoore matcher = new BoyerMoore("a");

		assertEquals(3, matcher.search("bbba"));
		assertEquals(-1, matcher.search("bbbb"));
	}

	@Test
	public void returnsFirstOverlappingMatch() {
		BoyerMoore matcher = new BoyerMoore("ana");

		assertEquals(1, matcher.search("bananana"));
	}

	@Test
	public void findsPatternWithRepeatedPrefixAndSuffix() {
		BoyerMoore matcher = new BoyerMoore("ababaca");

		assertEquals(2, matcher.search("abababacaba"));
	}

	@Test
	public void findsClassicBoyerMooreExample() {
		BoyerMoore matcher = new BoyerMoore("ABCDABD");

		assertEquals(15, matcher.search("ABC ABCDAB ABCDABCDABDE"));
	}
}