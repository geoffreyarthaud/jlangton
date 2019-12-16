package fr.cookiedev.jlangton.core;

public class LangtonAntRectMapCITest extends AbstractLangtonAntCIT {
	public LangtonAntRectMapCITest() {
		super(() -> new RectLangtonMapImpl(X_SIZE, Y_SIZE), BitSetLangtonPathImpl::new);
	}
}
