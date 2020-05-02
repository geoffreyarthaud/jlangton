package fr.cookiedev.jlangton.core;

import java.util.Collection;

public interface LangtonMap {

	boolean get(long pos);

	Collection<Long> getAll();

	long prev(long pos);

	void set(long pos, boolean value);

	long toLeft(long fromPos);

	long toRight(long fromPos);

	long backLeft(long fromPos);

	long backRight(long fromPos);

	void resetOrientation();

	LangtonMap cumulateSymetry(long centerPos);

	LangtonMap applySymetry(long centerPos);

}
