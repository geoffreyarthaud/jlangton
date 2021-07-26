package fr.cookiedev.jlangton.core;

class RectLangtonMapCITest extends VariousLangtonCIT {
	public RectLangtonMapCITest() {
		super(() -> new RectLangtonMapImpl(X_SIZE, Y_SIZE), BitSetLangtonPathImpl::new);
	}
}

class LangtonPathToMapRectCITest extends LangtonPathToMapCIT {
	public LangtonPathToMapRectCITest() {
		super(() -> new RectLangtonMapImpl(X_SIZE, Y_SIZE));
	}
}