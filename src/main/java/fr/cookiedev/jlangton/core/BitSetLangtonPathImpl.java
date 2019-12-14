package fr.cookiedev.jlangton.core;

import java.util.BitSet;

public class BitSetLangtonPathImpl implements LangtonPath {

	public static final int CYCLE_LENGTH = 104;

	private final BitSet path = new BitSet();

	private int size = 0;

	private int cyclingStart = 0;

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

	public boolean isCycling() {
		return size - cyclingStart - CYCLE_LENGTH >= CYCLE_LENGTH;
	}

	public long getCyclingStart() {
		return cyclingStart;
	}

	public long getNbCycles() {
		return isCycling() ? (size - cyclingStart - CYCLE_LENGTH) / CYCLE_LENGTH : 0;
	}

}
