package fr.cookiedev.jlangton.core;

public interface LangtonMap {

	boolean get(long pos);

	long prev(long pos);

	void set(long pos, boolean value);

	long toLeft(long fromPos);

	long toRight(long fromPos);

	long backLeft(long fromPos);

	long backRight(long fromPos);

}
