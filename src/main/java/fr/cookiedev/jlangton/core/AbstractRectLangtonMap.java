package fr.cookiedev.jlangton.core;

public abstract class AbstractRectLangtonMap implements CartesianLangtonMap {

	private int xOrientation;

	private int yOrientation;

	protected AbstractRectLangtonMap() {
		xOrientation = 0;
		yOrientation = 1;
	}

	protected long toLeft(int x, int y) {
		// Rotate by 90 degrees
		final int tmpX = xOrientation;
		xOrientation = -yOrientation;
		yOrientation = tmpX;

		return encode(x + xOrientation, y + yOrientation);
	}

	protected long toRight(int x, int y) {
		// Rotate by -90 degrees
		final int tmpX = xOrientation;
		xOrientation = yOrientation;
		yOrientation = -tmpX;

		return encode(x + xOrientation, y + yOrientation);
	}

	protected long encode(int x, int y) {
		return (long) x << 32 | y;
	}

}
