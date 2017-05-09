
public class AsciiHash implements StringHasher{
	
	public int hash(String s) {	// return the combined ascii value of a word
		int toReturn = 0;
		for (int i = 0; i < s.length(); i++) {
			toReturn += s.charAt(i);
		}
		return toReturn;
	}
}
