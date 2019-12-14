package fr.cookiedev.jlangton.core;

public class LangtonAnt {

	private final LangtonMap langtonMap;
	private final LangtonPath langtonPath;

	public LangtonAnt(LangtonMap langtonMap, LangtonPath langtonPath) {
		this.langtonMap = langtonMap;
		this.langtonPath = langtonPath;
	}

	public long move(long fromPos) {
		final boolean valueFrom = langtonMap.get(fromPos);
		final long toPos = valueFrom ? langtonMap.toRight(fromPos) : langtonMap.toLeft(fromPos);
		langtonPath.add(valueFrom);
		langtonMap.set(fromPos, !valueFrom);
		return toPos;
	}

	public long move(long fromPos, long nbMove) {
		for (; nbMove > 0; nbMove--) {
			fromPos = move(fromPos);
		}
		return fromPos;
	}

	public LangtonPath getPath() {
		return langtonPath;
	}

}
