package fr.cookiedev.jlangton.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.cookiedev.jlangton.core.RectLangtonMapImpl;

class RectLangtonMapImplTest {

	private RectLangtonMapImpl rectLangtonMapImpl;

	@BeforeEach
	public void initMap() {
		rectLangtonMapImpl = new RectLangtonMapImpl(256, 256);
	}

	@Test
	public void givenSetAPosTrue_whenGetThisPos_thenReturnsTrue() {
		// GIVEN
		final long pos = 128L * rectLangtonMapImpl.getXSize() + 128;
		rectLangtonMapImpl.set(pos, true);

		// WHEN
		final boolean value = rectLangtonMapImpl.get(pos);

		// THEN
		assertThat(value).isTrue();
	}

	@Test
	public void whenCreateLongMap_thenThrowIllegalArgument() {
		// WHEN/THEN
		assertThrows(IllegalArgumentException.class, () -> new RectLangtonMapImpl(1000000, 1000000));
	}

	@Test
	public void whenGetOutPos_thenGetModuloPos() {
		// GIVEN
		final long pos = 128L * rectLangtonMapImpl.getXSize() + 128;
		rectLangtonMapImpl.set(pos, true);

		// WHEN
		final boolean value = rectLangtonMapImpl
				.get(pos + rectLangtonMapImpl.getXSize() * rectLangtonMapImpl.getYSize());

		// THEN
		assertThat(value).isTrue();
	}

	@Test
	public void whenSetOutPos_thenSetModuloPos() {
		// GIVEN
		final long pos = 128L * rectLangtonMapImpl.getXSize() + 128
				+ rectLangtonMapImpl.getXSize() * rectLangtonMapImpl.getYSize();
		rectLangtonMapImpl.set(pos, true);

		// WHEN
		final boolean value = rectLangtonMapImpl
				.get(pos % (rectLangtonMapImpl.getXSize() * rectLangtonMapImpl.getYSize()));

		// THEN
		assertThat(value).isTrue();
	}

	@Test
	public void givenNoMove_whenToLeft_thenGetPrevious() {
		// GIVEN
		final long fromPos = 128;

		// WHEN
		final long pos = rectLangtonMapImpl.toLeft(fromPos);

		// THEN
		assertThat(pos).isEqualTo(fromPos - 1);
	}

	@Test
	public void givenNoMove_whenToRight_thenGetNext() {
		// GIVEN
		final long fromPos = 128;

		// WHEN
		final long pos = rectLangtonMapImpl.toRight(fromPos);

		// THEN
		assertThat(pos).isEqualTo(fromPos + 1);
	}

	@Test
	public void givenThreeLeftMove_whenLeft_thenGetToOrigin() {
		// GIVEN
		final long fromPos = 256 * 128 + 128;
		final long toPos = rectLangtonMapImpl.toLeft(rectLangtonMapImpl.toLeft(rectLangtonMapImpl.toLeft(fromPos)));

		// WHEN
		final long pos = rectLangtonMapImpl.toLeft(toPos);

		// THEN
		assertThat(pos).isEqualTo(fromPos);
	}

	@Test
	public void givenThreeRightMove_whenRight_thenGetToOrigin() {
		// GIVEN
		final long fromPos = 256 * 128 + 128;
		final long toPos = rectLangtonMapImpl.toRight(rectLangtonMapImpl.toRight(rectLangtonMapImpl.toRight(fromPos)));

		// WHEN
		final long pos = rectLangtonMapImpl.toRight(toPos);

		// THEN
		assertThat(pos).isEqualTo(fromPos);
	}

	@Test
	public void givenLeftTwoRightMove_whenRight_thenGetToOrigin() {
		// GIVEN
		final long fromPos = 256 * 128 + 128;
		final long toPos = rectLangtonMapImpl.toRight(rectLangtonMapImpl.toRight(rectLangtonMapImpl.toLeft(fromPos)));

		// WHEN
		final long pos = rectLangtonMapImpl.toRight(toPos);

		// THEN
		assertThat(pos).isEqualTo(fromPos);
	}

	@Test
	public void givenLeftTwoLeftMove_whenLeft_thenGetToOrigin() {
		// GIVEN
		final long fromPos = 256 * 128 + 128;
		final long toPos = rectLangtonMapImpl.toLeft(rectLangtonMapImpl.toLeft(rectLangtonMapImpl.toRight(fromPos)));

		// WHEN
		final long pos = rectLangtonMapImpl.toLeft(toPos);

		// THEN
		assertThat(pos).isEqualTo(fromPos);
	}

}
