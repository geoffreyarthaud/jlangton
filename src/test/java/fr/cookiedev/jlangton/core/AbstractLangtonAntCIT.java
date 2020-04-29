package fr.cookiedev.jlangton.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public abstract class AbstractLangtonAntCIT {
	public static final int X_SIZE = 1024;
	public static final int Y_SIZE = 1024;
	public static final int INIT_POS = Y_SIZE / 2 * X_SIZE + X_SIZE / 2;
	private final Supplier<CartesianLangtonMap> langtonMapSupplier;
	private final Supplier<LangtonPath> langtonPathSupplier;
	private CartesianLangtonMap langtonMap;
	private LangtonPath langtonPath;
	private LangtonAnt langtonAnt;

	public AbstractLangtonAntCIT(Supplier<CartesianLangtonMap> langtonMapSupplier,
			Supplier<LangtonPath> langtonPathSupplier) {
		this.langtonMapSupplier = langtonMapSupplier;
		this.langtonPathSupplier = langtonPathSupplier;
	}

	@BeforeEach
	public void initComponent() {
		langtonMap = langtonMapSupplier.get();
		langtonPath = langtonPathSupplier.get();
		langtonAnt = new LangtonAnt(langtonMap, langtonPath);
	}

	@Test
	public void givenBlankMap_whenGo11000Steps_thenGetCorrectCyclingProps() {
		// GIVEN
		final int steps = 11000;
		final int expectedCyclingStart = 9977;
		final int cycleLength = 104;
		final int expectedNbCycles = (steps - expectedCyclingStart - cycleLength) / cycleLength;

		// WHEN
		langtonAnt.move(INIT_POS, steps);

		// THEN
		assertThat(langtonPath.isCycling()).isTrue();
		assertThat(langtonPath.getCyclingStart()).isEqualTo(expectedCyclingStart);
		assertThat(langtonPath.getNbCycles()).isEqualTo(expectedNbCycles);
	}

	// TODO : Refactor this test to obtain a well-structured test.
	@Test
	public void testFootPrint() {
		// GIVEN
		final int expectedCyclingStart = 9977;
		final int cycleLength = 104;

		// WHEN
		Map<Long, Long> cyclePos;
		long pos = langtonAnt.move(Y_SIZE / 2 * X_SIZE + X_SIZE / 2, expectedCyclingStart - 1);
		final List<Long> posRecorder = new ArrayList<>();
		for (int nb = 0; nb < 10; nb++) {
			cyclePos = new HashMap<>();
			for (int i = 0; i < cycleLength; i++) {
				pos = langtonAnt.move(pos);
				final long previous = cyclePos.getOrDefault(pos, 0L);
				cyclePos.put(pos, previous + 1);
				posRecorder.add(pos);
			}

			assertThat(cyclePos.size()).isEqualTo(40);
			assertThat(cyclePos.values().stream().mapToLong(i -> i).sum()).isEqualTo(104);
		}

		for (int i = cycleLength; i < posRecorder.size(); i++) {
			assertThat(langtonMap.toX(posRecorder.get(i)) - langtonMap.toX(posRecorder.get(i - cycleLength)))
					.isEqualTo(2);
			assertThat(langtonMap.toY(posRecorder.get(i)) - langtonMap.toY(posRecorder.get(i - cycleLength)))
					.isEqualTo(-2);
		}

	}

	@Test
	public void testCycleBoxSize() {
		// GIVEN
		final int expectedCyclingStart = 9977;
		final int cycleLength = 104;
		final String expectedCycleMap = "111100\n" +
				"001000\n" +
				"101000\n" +
				"100100\n" +
				"110100\n" +
				"011000\n" +
				"110000\n" +
				"000000\n" +
				"000000\n";

		// WHEN
		long pos = langtonAnt.move(langtonMap.fromXY(X_SIZE / 2, Y_SIZE / 2), expectedCyclingStart - 1);
		int xMin = Integer.MAX_VALUE;
		int yMin = Integer.MAX_VALUE;
		int xMax = Integer.MIN_VALUE;
		int yMax = Integer.MIN_VALUE;
		String cycleMap = "";
		for (int v = 522; v >= 514; v--) {
			for (int u = 526; u <= 531; u++) {
				cycleMap += langtonMap.get(langtonMap.fromXY(u, v)) ? "1" : "0";
			}
			cycleMap += "\n";
		}
		assertThat(cycleMap).isEqualTo(expectedCycleMap);

		for (int i = 0; i < cycleLength; i++) {
			pos = langtonAnt.move(pos);
			final int xPos = langtonMap.toX(pos);
			final int yPos = langtonMap.toY(pos);
			xMin = Math.min(xMin, xPos);
			xMax = Math.max(xMax, xPos);
			yMin = Math.min(yMin, yPos);
			yMax = Math.max(yMax, yPos);
		}
		assertThat(xMax - xMin + 1).isEqualTo(6);
		assertThat(yMax - yMin + 1).isEqualTo(9);
	}

	@ParameterizedTest(name = "Given an initial config {0} then get correct ant cycle after permutation")
	@ValueSource(chars = { 0, 61011, 20854, 30087, 61575, 5691, 20927, 59656, 4206, 4639, 45742, 39478, 39445, 4510,
			23768, 17838, 38257, 54844, 34792, 61309 })
	public void givenAnInitialConfig_whenIsCycling_ThenGet104Cycle(char initConfig) {
		// GIVEN
		long pos = INIT_POS;
		int kbit = 0;
		for (int y = -1; y < 3; y++) {
			for (int x = -1; x < 3; x++) {
				final long setPos = INIT_POS + x + y * X_SIZE;
				final boolean bit = (initConfig >> kbit & 1) != 0;
				langtonMap.set(setPos, bit);
				kbit++;
			}
		}

		// WHEN
		while (langtonPath.getNbCycles() < 10) {
			pos = langtonAnt.move(pos);
		}
		final String doubleCycle = langtonPath.getPath(langtonPath.getCyclingStart(), 104 * 2);

		// THEN
		assertThat(doubleCycle).contains(LangtonPath.CYCLE_PATH);

	}

	@ParameterizedTest(name = "Given a correct path then get no exception")
	@ValueSource(strings = { "0000", "00001", LangtonPath.CYCLE_PATH })
	public void givenACorrectPath_whenApplyPath_ThenNoException(String correctPath) {
		// GIVEN
		LangtonPathToMap pathToMap = new LangtonPathToMap(langtonMapSupplier);
		long fromPos = langtonMap.fromXY(X_SIZE / 2, Y_SIZE / 2);

		// WHEN
		LangtonMap outLangtonMap = pathToMap.applyPath(correctPath, fromPos);

		// THEN
		boolean expectedValue = correctPath.charAt(0) == '1';
		assertThat(outLangtonMap.get(fromPos)).isEqualTo(expectedValue);
	}

	@ParameterizedTest(name = "Given an incorrect path then get exception")
	@ValueSource(strings = { "00000", "11111" })
	public void givenAnIncorrectPath_whenApplyPath_ThenException(String incorrectPath) {
		// GIVEN
		LangtonPathToMap pathToMap = new LangtonPathToMap(langtonMapSupplier);
		long fromPos = langtonMap.fromXY(X_SIZE / 2, Y_SIZE / 2);

		// WHEN-THEN
		assertThrows(IllegalArgumentException.class, () -> pathToMap.applyPath(incorrectPath, fromPos));

	}

	@ParameterizedTest(name = "Given a correct path then get no exception")
	@ValueSource(strings = { "0000", "00001", LangtonPath.CYCLE_PATH })
	public void givenACorrectPath_whenApplyBackPath_ThenNoException(String correctPath) {
		// GIVEN
		LangtonPathToMap pathToMap = new LangtonPathToMap(langtonMapSupplier);
		long fromPos = langtonMap.fromXY(X_SIZE / 2, Y_SIZE / 2);

		// WHEN
		LangtonMap outLangtonMap = pathToMap.applyBackPath(correctPath, fromPos);

		// THEN
		boolean expectedValue = correctPath.charAt(correctPath.length() - 1) == '0';
		assertThat(outLangtonMap.get(outLangtonMap.prev(fromPos))).isEqualTo(expectedValue);
	}

	@ParameterizedTest(name = "Given an incorrect path then get exception")
	@ValueSource(strings = { "00000", "11111" })
	public void givenAnIncorrectPath_whenApplyBackPath_ThenException(String incorrectPath) {
		// GIVEN
		LangtonPathToMap pathToMap = new LangtonPathToMap(langtonMapSupplier);
		long fromPos = langtonMap.fromXY(X_SIZE / 2, Y_SIZE / 2);

		// WHEN-THEN
		assertThrows(IllegalArgumentException.class, () -> pathToMap.applyBackPath(incorrectPath, fromPos));

	}

}
