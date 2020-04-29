package fr.cookiedev.jlangton.core;

import java.util.BitSet;

public class BitSetLangtonPathImpl implements LangtonPath {

	public static final int CYCLE_LENGTH = 104;

	private BitSet path = new BitSet();

	private int size = 0;

	private int cyclingStart = 0;

	private int backCyclingStart = 0;

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public boolean get(long i) {
		return path.get((int) i);
	}

	@Override
	public void add(boolean value) {
		if (size >= CYCLE_LENGTH && value != path.get(size - CYCLE_LENGTH)) {
			cyclingStart = size - CYCLE_LENGTH + 1;
		}

		path.set(size++, value);

	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer(size);
		for (int index = 0; index < size; index++) {
			buffer.append(path.get(index) ? '1' : '0');
		}
		return buffer.toString();
	}

	@Override
	public boolean isCycling() {
		return size - cyclingStart - CYCLE_LENGTH >= CYCLE_LENGTH;
	}

	@Override
	public long getCyclingStart() {
		return cyclingStart;
	}

	@Override
	public long getNbCycles() {
		return isCycling() ? (size - cyclingStart - CYCLE_LENGTH) / CYCLE_LENGTH : 0;
	}

	@Override
	public String getPath(long from, long length) {
		final StringBuffer buffer = new StringBuffer((int) length);
		final BitSet subPath = path.get((int) from, (int) from + (int) length);
		for (int i = 0; i < length; i++) {
			buffer.append(subPath.get(i) ? '1' : '0');
		}
		return buffer.toString();
	}

	@Override
	public String getPath() {
		final StringBuffer buffer = new StringBuffer(size);
		for (int i = 0; i < size; i++) {
			buffer.append(path.get(i) ? '1' : '0');
		}
		return buffer.toString();
	}

	@Override
	public void backAdd(boolean value) {
		cyclingStart++;
		if (size >= CYCLE_LENGTH && value != path.get(CYCLE_LENGTH - 1)) {
			backCyclingStart = 0;
		} else {
			backCyclingStart++;
		}
		BitSet newPath = new BitSet(++size);
		path.stream().forEach(i -> newPath.set(i + 1));
		newPath.set(0, value);
		path = newPath;
	}

	@Override
	public boolean isBackCycling() {
		return backCyclingStart - CYCLE_LENGTH >= CYCLE_LENGTH;
	}

	@Override
	public long getBackCyclingStart() {
		return backCyclingStart;
	}

	@Override
	public long getNbBackCycles() {
		return isBackCycling() ? (backCyclingStart - CYCLE_LENGTH) / CYCLE_LENGTH : 0;
	}

}
