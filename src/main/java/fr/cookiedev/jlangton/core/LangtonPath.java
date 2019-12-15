package fr.cookiedev.jlangton.core;

public interface LangtonPath {

	long getSize();

	boolean get(long i);

	void add(boolean b);

	public boolean isCycling();

	public long getCyclingStart();

	public long getNbCycles();

	public String getPath(long from, long length);

	public String getPath();

}
