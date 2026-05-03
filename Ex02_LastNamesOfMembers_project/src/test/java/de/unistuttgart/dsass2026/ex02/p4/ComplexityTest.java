package de.unistuttgart.dsass2026.ex02.p4;

import static org.junit.Assert.*;
import java.util.function.IntUnaryOperator;
import org.junit.Ignore;
import org.junit.Test;

public class ComplexityTest {

	private long averageRuntimeNanos(IntUnaryOperator algorithm, int n, int warmupRuns, int measuredRuns) {
		int sink = 0;

		for (int i = 0; i < warmupRuns; i++) {
			sink ^= algorithm.applyAsInt(n);
		}

		long start = System.nanoTime();
		for (int i = 0; i < measuredRuns; i++) {
			sink ^= algorithm.applyAsInt(n);
		}
		long total = System.nanoTime() - start;

		// Prevent aggressive optimization that could remove calls in micro-benchmarks.
		if (sink == Integer.MIN_VALUE) {
			System.out.print("");
		}

		return total / measuredRuns;
	}

	private void compareAndPrintBenchmark(
		String label,
		IntUnaryOperator original,
		IntUnaryOperator improved,
		int n,
		int warmupRuns,
		int measuredRuns
	) {
		long oldNanos = averageRuntimeNanos(original, n, warmupRuns, measuredRuns);
		long newNanos = averageRuntimeNanos(improved, n, warmupRuns, measuredRuns);

		assertEquals("Result mismatch for benchmark " + label + " at n=" + n, original.applyAsInt(n), improved.applyAsInt(n));
		assertTrue("Expected improved algorithm to be faster for benchmark " + label, newNanos < oldNanos);

		double ratio = (double) oldNanos / (double) newNanos;
		System.out.println(
			"[Benchmark " + label + "] n=" + n
				+ " | old=" + oldNanos + " ns"
				+ " | new=" + newNanos + " ns"
				+ " | speedup=" + String.format("%.2f", ratio) + "x"
		);
	}

	@Test
	public void better1_matchesOriginal_forSmallRangeIncludingNegative() {
		for (int n = -5; n <= 50; n++) {
			assertEquals(
				"Mismatch at n=" + n,
				Complexity.couldBeBetter1(n),
				Complexity.isDoneBetter1(n)
			);
		}
	}

	@Test
	public void better2_matchesOriginal_forRangeWithoutLargeOverflow() {
		for (int n = -5; n <= 12; n++) {
			assertEquals(
				"Mismatch at n=" + n,
				Complexity.couldBeBetter2(n),
				Complexity.isDoneBetter2(n)
			);
		}
	}

	@Test
	public void better3_matchesOriginal_forTypicalFibonacciInputs() {
		for (int n = 0; n <= 20; n++) {
			assertEquals(
				"Mismatch at n=" + n,
				Complexity.couldBeBetter3(n),
				Complexity.isDoneBetter3(n)
			);
		}
	}

	@Test
	public void better3_throwsOnNegativeLikeOriginal() {
		assertThrows(IllegalArgumentException.class, () -> Complexity.couldBeBetter3(-1));
		assertThrows(IllegalArgumentException.class, () -> Complexity.isDoneBetter3(-1));
	}

	@Test
	public void better2_knownValues() {
		assertEquals(1, Complexity.isDoneBetter2(0));
		assertEquals(1, Complexity.isDoneBetter2(1));
		assertEquals(2, Complexity.isDoneBetter2(2));
		assertEquals(6, Complexity.isDoneBetter2(3));
		assertEquals(24, Complexity.isDoneBetter2(4));
		assertEquals(120, Complexity.isDoneBetter2(5));
	}

	@Test
	public void better3_knownValues() {
		assertEquals(0, Complexity.isDoneBetter3(0));
		assertEquals(1, Complexity.isDoneBetter3(1));
		assertEquals(1, Complexity.isDoneBetter3(2));
		assertEquals(2, Complexity.isDoneBetter3(3));
		assertEquals(3, Complexity.isDoneBetter3(4));
		assertEquals(5, Complexity.isDoneBetter3(5));
		assertEquals(55, Complexity.isDoneBetter3(10));
	}

	//@Ignore("Manual benchmark: run explicitly when you want to compare runtime behavior.")
	@Test
	public void benchmark_oldVsNew_runtimeComparison() {
		compareAndPrintBenchmark(
			"A4.1",
			Complexity::couldBeBetter1,
			Complexity::isDoneBetter1,
			5_000_000,
			2,
			5
		);

		compareAndPrintBenchmark(
			"A4.2",
			Complexity::couldBeBetter2,
			Complexity::isDoneBetter2,
			1500,
			2,
			5
		);

		compareAndPrintBenchmark(
			"A4.3",
			Complexity::couldBeBetter3,
			Complexity::isDoneBetter3,
			35,
			1,
			3
		);
	}

}