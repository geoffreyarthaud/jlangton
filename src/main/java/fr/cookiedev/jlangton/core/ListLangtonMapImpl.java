package fr.cookiedev.jlangton.core;

import java.util.HashSet;
import java.util.Set;

public class ListLangtonMapImpl extends AbstractRectLangtonMap {

	final Set<Long> langtonTrueSet;

	public ListLangtonMapImpl() {
		langtonTrueSet = new HashSet<Long>();
	}

	@Override
	public boolean get(long pos) {
		return langtonTrueSet.contains(pos);
	}

	@Override
	public void set(long pos, boolean value) {
		if (value) {
			langtonTrueSet.add(pos);
		} else {
			langtonTrueSet.remove(pos);
		}
	}

	@Override
	public long toLeft(long fromPos) {
		return toLeft(toX(fromPos), toY(fromPos));
	}

	@Override
	public long toRight(long fromPos) {
		return toRight(toX(fromPos), toY(fromPos));
	}

	@Override
	public int toX(long pos) {
		return (int) (pos >> 32);
	}

	@Override
	public int toY(long pos) {
		return (int) pos;
	}

}
