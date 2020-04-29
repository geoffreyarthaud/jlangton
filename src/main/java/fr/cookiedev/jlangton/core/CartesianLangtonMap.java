package fr.cookiedev.jlangton.core;

public interface CartesianLangtonMap extends LangtonMap {

	public long fromXY(int x, int y);

	public int toX(long pos);

	public int toY(long pos);

	public int getMinX();

	public int getMinY();
	
	public int getMaxX();
	
	public int getMaxY();
}
