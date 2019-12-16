package fr.cookiedev.jlangton.core;

public class LangtonAntListMapCITest extends AbstractLangtonAntCIT {
	public LangtonAntListMapCITest() {
		super(ListLangtonMapImpl::new, BitSetLangtonPathImpl::new);
	}
}
