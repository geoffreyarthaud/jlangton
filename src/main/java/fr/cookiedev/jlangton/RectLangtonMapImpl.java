package fr.cookiedev.jlangton;

import java.text.MessageFormat;
import java.util.BitSet;

public class RectLangtonMapImpl implements LangtonMap {

	private final int xSize;

	private final int ySize;

	private final BitSet grid;

	private int xOrientation;

	private int yOrientation;

	public RectLangtonMapImpl(int xSize, int ySize) {
		checkRangePositiveInteger(xSize);
		checkRangePositiveInteger(ySize);
		checkRangePositiveInteger((long) xSize * ySize);
		this.xSize = xSize;
		this.ySize = ySize;
		grid = new BitSet(xSize * ySize);
		xOrientation = 0;
		yOrientation = 1;
	}

	@Override
	public boolean get(long pos) {
		return grid.get(rangeGrid(pos));
	}

	@Override
	public void set(long pos, boolean value) {
		grid.set(rangeGrid(pos), value);
	}

	@Override
	public long toLeft(long fromPos) {
		final int intPos = rangeGrid(fromPos);
		final int xPos = intPos % xSize;
		final int yPos = intPos / xSize;

		// Rotate by 90 degrees
		final int tmpX = xOrientation;
		xOrientation = -yOrientation;
		yOrientation = tmpX;

		return xPos + xOrientation + (yPos + yOrientation) * xSize;
	}

	@Override
	public long toRight(long fromPos) {
		final int intPos = rangeGrid(fromPos);
		final int xPos = intPos % xSize;
		final int yPos = intPos / xSize;

		// Rotate by -90 degrees
		final int tmpX = xOrientation;
		xOrientation = yOrientation;
		yOrientation = -tmpX;

		return xPos + xOrientation + (yPos + yOrientation) * xSize;
	}

	public int getYSize() {
		return ySize;
	}

	public int getXSize() {
		return xSize;
	}

	private int checkRangePositiveInteger(long value) {
		if (value < 0 || value > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(
					MessageFormat.format("Value {0} is not a be positive integer less or equal than {1}",
							value, Integer.MAX_VALUE));
		}
		return (int) value;
	}

	private int rangeGrid(long value) {
		return checkRangePositiveInteger(value) % (xSize * ySize);
	}

}
