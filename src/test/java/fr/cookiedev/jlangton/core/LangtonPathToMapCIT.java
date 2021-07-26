package fr.cookiedev.jlangton.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public abstract class LangtonPathToMapCIT {
    public static final int X_SIZE = 1024;
	public static final int Y_SIZE = 1024;
	public static final int INIT_POS = Y_SIZE / 2 * X_SIZE + X_SIZE / 2;
	private final Supplier<CartesianLangtonMap> langtonMapSupplier;
	private CartesianLangtonMap langtonMap;

	public LangtonPathToMapCIT(Supplier<CartesianLangtonMap> langtonMapSupplier) {
		this.langtonMapSupplier = langtonMapSupplier;
	}

	@BeforeEach
	public void initComponent() {
		langtonMap = langtonMapSupplier.get();
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
