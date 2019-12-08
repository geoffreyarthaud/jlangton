package fr.cookiedev.jlangton;

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

}
