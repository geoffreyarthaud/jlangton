package fr.cookiedev.jlangton.core;

public class LangtonAnt {

	private final LangtonMap langtonMap;

	public LangtonAnt(LangtonMap langtonMap) {
		this.langtonMap = langtonMap;
	}

	public long move(long fromPos) {
		final boolean valueFrom = langtonMap.get(fromPos);
		final long toPos = valueFrom ? langtonMap.toRight(fromPos) : langtonMap.toLeft(fromPos);
		langtonMap.set(fromPos, !valueFrom);
		return toPos;
	}

	public long move(long fromPos, long nbMove) {
		for (; nbMove > 0; nbMove--) {
			fromPos = move(fromPos);
		}
		return fromPos;
	}

}
