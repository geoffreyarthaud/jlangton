package fr.cookiedev.jlangton.core;

import java.text.MessageFormat;
import java.util.BitSet;

public class RectLangtonMapImpl extends AbstractRectLangtonMap {

	private final int xSize;

	private final int ySize;

	private final BitSet grid;

	public RectLangtonMapImpl(int xSize, int ySize) {
		checkRangePositiveInteger(xSize);
		checkRangePositiveInteger(ySize);
		checkRangePositiveInteger((long) xSize * ySize);
		this.xSize = xSize;
		this.ySize = ySize;
		grid = new BitSet(xSize * ySize);
	}

	@Override
	public boolean get(long pos) {
		return grid.get(rangeGrid(pos));
	}

	@Override
	public long prev(long fromPos) {
		final int intPos = rangeGrid(fromPos);
		final int xPos = intPos % xSize;
		final int yPos = intPos / xSize;
		final long prevPos = prev(xPos, yPos);
		return fromXY(prevPos >> 32, prevPos);
	}

	@Override
	public long backLeft(long fromPos) {
		final int intPos = rangeGrid(fromPos);
		final int xPos = intPos % xSize;
		final int yPos = intPos / xSize;
		final long resultPos = backLeft(xPos, yPos);
		return fromXY(resultPos >> 32, resultPos);
	}

	@Override
	public long backRight(long fromPos) {
		final int intPos = rangeGrid(fromPos);
		final int xPos = intPos % xSize;
		final int yPos = intPos / xSize;
		final long resultPos = backRight(xPos, yPos);
		return fromXY(resultPos >> 32, resultPos);
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
		final long resultPos = toLeft(xPos, yPos);
		final int resultPosX = (int) (resultPos >> 32);
		final int resultPosY = (int) resultPos;
		return resultPosX + resultPosY * xSize;
	}

	@Override
	public long toRight(long fromPos) {
		final int intPos = rangeGrid(fromPos);
		final int xPos = intPos % xSize;
		final int yPos = intPos / xSize;
		final long resultPos = toRight(xPos, yPos);
		final int resultPosX = (int) (resultPos >> 32);
		final int resultPosY = (int) resultPos;
		return resultPosX + resultPosY * xSize;
	}

	@Override
	public int toX(long pos) {
		final int intPos = rangeGrid(pos);
		return intPos % xSize;
	}

	@Override
	public int toY(long pos) {
		final int intPos = rangeGrid(pos);
		return intPos / xSize;
	}

	public int getYSize() {
		return ySize;
	}

	public int getXSize() {
		return xSize;
	}

	public BitSet getGrid() {
		return grid;
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

	@Override
	public long fromXY(int x, int y) {
		return (long) y * xSize + x;
	}

	private long fromXY(long x, long y) {
		return fromXY((int) x, (int) y);
	}

}
