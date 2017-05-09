
public class dbj2Hash implements StringHasher{

	public int hash(String s) {	// use the dbj2 formula to get this hash
		int hash = 5381;
		for (int i = 0; i < s.length(); i++) {
			hash = hash * 33 + s.charAt(i);
		}
		return hash;
	}

}
