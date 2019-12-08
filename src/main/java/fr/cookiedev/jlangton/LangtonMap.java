package fr.cookiedev.jlangton;

public interface LangtonMap {

	boolean get(long pos);

	void set(long pos, boolean value);

	long toLeft(long fromPos);

	long toRight(long fromPos);

}
