package fr.cookiedev.jlangton.core;

public interface CartesianLangtonMap extends LangtonMap {

	public long fromXY(int x, int y);

	public int toX(long pos);

	public int toY(long pos);
}
