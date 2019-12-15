package fr.cookiedev.jlangton.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LangtonAntCITest {
	private LangtonAnt langtonAnt;
	private LangtonPath langtonPath;
	private LangtonMap langtonMap;

	@BeforeEach
	public void initComponent() {
		langtonMap = new RectLangtonMapImpl(500, 500);
		langtonPath = new BitSetLangtonPathImpl();
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
		langtonAnt.move(500 * 250 + 250, steps);

		// THEN
		assertThat(langtonPath.isCycling()).isTrue();
		assertThat(langtonPath.getCyclingStart()).isEqualTo(expectedCyclingStart);
		assertThat(langtonPath.getNbCycles()).isEqualTo(expectedNbCycles);
	}
}
