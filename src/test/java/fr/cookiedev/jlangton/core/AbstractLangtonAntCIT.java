package fr.cookiedev.jlangton.core;

import static org.assertj.core.api.Assertions.assertThat;

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
	public static final String CYCLE_PATH = "10110000110110000110000110100001011110000100100001111010000100001111010000101100111100110000110010110110";
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
		assertThat(doubleCycle).contains(CYCLE_PATH);

	}
}
