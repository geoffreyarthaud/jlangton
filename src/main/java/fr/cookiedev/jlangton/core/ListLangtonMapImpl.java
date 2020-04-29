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

	@Override
	public long fromXY(int x, int y) {
		return encode(x, y);
	}

	@Override
	public long prev(long pos) {
		return prev(toX(pos), toY(pos));
	}

	@Override
	public long backLeft(long fromPos) {
		return backLeft(toX(fromPos), toY(fromPos));
	}

	@Override
	public long backRight(long fromPos) {
		return backRight(toX(fromPos), toY(fromPos));
	}

	@Override
	public LangtonMap cumulateSymetry(long centerPos) {
		int centerX = toX(centerPos);
		int centerY = toY(centerPos);
		for (long curPos: langtonTrueSet) {
			int curPosX = toX(curPos);
			int curPosY = toY(curPos);
			langtonTrueSet.add(fromXY(2*centerX - curPosX, 2*centerY - curPosY));
		}
		return this;
	}

	@Override
	public LangtonMap applySymetry(long centerPos) {
		int centerX = toX(centerPos);
		int centerY = toY(centerPos);
		for (long curPos: (Long[])langtonTrueSet.toArray()) {
			int curPosX = toX(curPos);
			int curPosY = toY(curPos);
			langtonTrueSet.add(fromXY(2*centerX - curPosX, 2*centerY - curPosY));
			langtonTrueSet.remove(curPos);
		}
		return this;
	}

}
