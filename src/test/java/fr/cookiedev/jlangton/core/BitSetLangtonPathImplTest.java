package fr.cookiedev.jlangton.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.BitSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BitSetLangtonPathImplTest {

	private final BitSetLangtonPathImpl langtonPath = new BitSetLangtonPathImpl();

	private BitSet testCycle;

	@BeforeEach
	public void initTestCycle() {
		final byte[] bytes = { 97, -122, -33, -120, 119, 48, -77, 84, -120, 121, 46, 92, 115 };
		testCycle = BitSet.valueOf(bytes);
	}

	@Test
	public void whenAddTwoSteps_ThenSizeIsTwo() {
		// WHEN
		langtonPath.add(false);
		langtonPath.add(true);

		// THEN
		assertThat(langtonPath.getSize()).isEqualTo(2);
	}

	@Test
	public void whenAddTwoSteps_ThenGetCorrectSteps() {
		// WHEN
		langtonPath.add(false);
		langtonPath.add(true);

		// THEN
		assertThat(!langtonPath.get(0));
		assertThat(langtonPath.get(1));
	}

	@Test
	public void whenAddTwoSteps_ThenGetCorrectString() {
		// WHEN
		langtonPath.add(false);
		langtonPath.add(true);

		// THEN
		assertThat(langtonPath.toString()).isEqualTo("01");
	}

	@Test
	public void givenA104DoubleCycle_whenGetCycle_thenGetCorrectAnswer() {
		// GIVEN
		for (int i = 0; i < 104; i++) {
			langtonPath.add(testCycle.get(i));
		}
		for (int i = 0; i < 104; i++) {
			langtonPath.add(langtonPath.get(i));
		}

		// WHEN
		final boolean cycle = langtonPath.isCycling();

		// THEN
		assertThat(cycle).isTrue();
	}

	@ParameterizedTest(name = "Given a cycle and a offset of {0} then getCyclingStart is {0}")
	@ValueSource(ints = { 5, 42, 103 })
	public void givenAnOffsetAnd104Cycle_whenGetCycleStart_thenGetOffset(int cyclingStart) {
		// GIVEN
		for (int i = 0; i < cyclingStart; i++) {
			langtonPath.add(true);
		}

		for (int i = 0; i < 104; i++) {
			langtonPath.add(testCycle.get(i));
		}
		for (int i = 0; i < 104; i++) {
			langtonPath.add(langtonPath.get(i + cyclingStart));
		}

		// WHEN
		final long resultStart = langtonPath.getCyclingStart();

		// THEN
		assertThat(resultStart).isEqualTo(cyclingStart);
	}

	@ParameterizedTest(name = "Given {0} cycles then getNbCycles is {0}")
	@ValueSource(ints = { 5, 42, 103 })
	public void givenAnOffsetAnd104Cycle_thenGetNbCyclesIsCorrect(int nbCycles) {
		// GIVEN
		final int cyclingStart = 42;
		for (int i = 0; i < cyclingStart; i++) {
			langtonPath.add(true);
		}
		for (int i = 0; i < 104; i++) {
			langtonPath.add(testCycle.get(i));
		}
		for (int j = 0; j < nbCycles; j++) {
			for (int i = 0; i < 104; i++) {
				langtonPath.add(langtonPath.get(i + cyclingStart));
			}
		}

		// WHEN
		final long resultNbCyles = langtonPath.getNbCycles();

		// THEN
		assertThat(resultNbCyles).isEqualTo(nbCycles);
	}

	@ParameterizedTest(name = "Given a partial cycle of {0} then isCycling is False")
	@ValueSource(ints = { 5, 42, 103 })
	public void givenAnOffsetAndPartialCycle_whenIsCycling_thenFalse(int partialCycle) {
		// GIVEN
		final long cyclingStart = 42;

		for (int i = 0; i < cyclingStart; i++) {
			langtonPath.add(true);
		}

		for (int i = 0; i < 104; i++) {
			langtonPath.add(testCycle.get(i));
		}
		for (int i = 0; i < partialCycle; i++) {
			langtonPath.add(langtonPath.get(i + cyclingStart));
		}

		// WHEN
		langtonPath.add(!langtonPath.get(partialCycle + cyclingStart));

		// THEN
		assertThat(langtonPath.isCycling()).isFalse();
	}

	@ParameterizedTest(name = "Given a partial cycle of {0} then getNbCycles is 0")
	@ValueSource(ints = { 5, 42, 103 })
	public void givenAnOffsetAndPartialCycle_thenGetNbCyclesIs0(int partialCycle) {
		// GIVEN
		final long cyclingStart = 42;

		for (int i = 0; i < cyclingStart; i++) {
			langtonPath.add(true);
		}

		for (int i = 0; i < 104; i++) {
			langtonPath.add(testCycle.get(i));
		}
		for (int i = 0; i < partialCycle; i++) {
			langtonPath.add(langtonPath.get(i + cyclingStart));
		}

		// WHEN
		langtonPath.add(!langtonPath.get(partialCycle + cyclingStart));

		// THEN
		assertThat(langtonPath.getNbCycles()).isEqualTo(0);
	}

}
