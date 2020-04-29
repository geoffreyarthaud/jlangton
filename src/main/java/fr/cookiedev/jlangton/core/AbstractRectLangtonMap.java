package fr.cookiedev.jlangton.core;

public abstract class AbstractRectLangtonMap implements CartesianLangtonMap {

	private int xOrientation;

	private int yOrientation;

	private int minX = Integer.MAX_VALUE;

	private int minY = Integer.MAX_VALUE;
	
	private int maxX = Integer.MIN_VALUE;
	
	private int maxY = Integer.MIN_VALUE;

	protected AbstractRectLangtonMap() {
		resetOrientation();
	}

	protected long prev(int x, int y) {
		return encode(x - xOrientation, y - yOrientation);
	}

	protected long toLeft(int x, int y) {
		// Rotate by 90 degrees
		registerPos(x, y);
		final int tmpX = xOrientation;
		xOrientation = -yOrientation;
		yOrientation = tmpX;
		return encode(x + xOrientation, y + yOrientation);
	}

	protected long toRight(int x, int y) {
		// Rotate by -90 degrees
		registerPos(x, y);
		final int tmpX = xOrientation;
		xOrientation = yOrientation;
		yOrientation = -tmpX;

		return encode(x + xOrientation, y + yOrientation);
	}

	protected long backLeft(int x, int y) {
		registerPos(x, y);
		final long prevPos = prev(x,y);
		final int tmpX = xOrientation;
		xOrientation = yOrientation;
		yOrientation = -tmpX;
		return prevPos;
	}

	protected long backRight(int x, int y) {
		registerPos(x, y);
		final long prevPos = prev(x,y);
		final int tmpX = xOrientation;
		xOrientation = -yOrientation;
		yOrientation = tmpX;
		return prevPos;
	}

	public static long encode(int x, int y) {
		return (long) x << 32 | y;
	}

	public void resetOrientation() {
		xOrientation = 0;
		yOrientation = 1;
	}

	@Override
	public int getMinX() {
		return minX;
	}

	@Override
	public int getMinY() {
		return minY;
	}

	@Override
	public int getMaxX() {
		return maxX;
	}

	@Override
	public int getMaxY() {
		return maxY;
	}

	private void registerPos(int x, int y) {
		minX = Math.min(minX, x);
		maxX = Math.max(maxX, x);
		minY = Math.min(minY, y);
		maxY = Math.max(maxY, y);
	}

}
