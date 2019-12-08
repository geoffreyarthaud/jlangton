package fr.cookiedev.jlangton;

public class LangtonAnt {

	private final LangtonMap langtonMap;

	public LangtonAnt(LangtonMap langtonMap) {
		this.langtonMap = langtonMap;
	}

	public long move(long fromPos) {
		final boolean valueFrom = this.langtonMap.get(fromPos);
		final long toPos = valueFrom ? this.langtonMap.toRight(fromPos) : this.langtonMap.toLeft(fromPos);
		this.langtonMap.set(fromPos, !valueFrom);
		return toPos;
	}

}
