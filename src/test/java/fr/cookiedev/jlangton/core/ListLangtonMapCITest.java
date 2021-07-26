package fr.cookiedev.jlangton.core;

class ListLangtonMapCITest extends VariousLangtonCIT {
	public ListLangtonMapCITest() {
		super(ListLangtonMapImpl::new, BitSetLangtonPathImpl::new);
	}
}

class LangtonPathToMapListCITest extends LangtonPathToMapCIT {
	public LangtonPathToMapListCITest() {
		super(ListLangtonMapImpl::new);
	}
}
