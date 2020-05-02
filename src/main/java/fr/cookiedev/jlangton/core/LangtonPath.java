package fr.cookiedev.jlangton.core;

public interface LangtonPath {
	
	public static final String CYCLE_PATH = "10101100001101100001100001101000010111100001001000011110100001000011110100001011001111001100001100101101";

	public static String backRevert(String path) {
		char[] pathArray = path.toCharArray();
		int len = pathArray.length;
		for(int left = 0; left < len / 2 + len % 2; left++) {
			char temp = revert(pathArray[left]);
			int right = len - left - 1;
			pathArray[left] = revert(pathArray[right]);
			pathArray[right] = temp;
		}
		return new String(pathArray);
	}

	private static char revert(char in) {
		return in == '0' ? '1' : '0';
	}

	long getSize();

	boolean get(long i);

	void add(boolean b);

	void backAdd(boolean b);

	public boolean isCycling();

	public long getCyclingStart();

	public long getNbCycles();

	public boolean isBackCycling();

	public long getBackCyclingStart();

	public long getNbBackCycles();

	public String getPath(long from, long length);

	public String getPath();

}
