import java.util.LinkedList;

public class HashTable {
	
	private LinkedList<Entry<Integer, String>>[] data = new LinkedList[5];	// an array of LinkedLists; each list contains Entry objects
	private static int size = 0;
	private boolean customHash = false;
	private StringHasher whatHash;
	
		//	if the constructor with the StringHasher parameter is called then it will set the hash to 
		//  which ever the user wants among the three otherwise it just uses the regular hash
		private int hash(String s) {
			if (customHash == true)		
				return whatHash.hash(s);
			else
				return Math.abs(s.hashCode() % data.length);
		}
		
		// constructor - that takes in a StringHasher object and also creates a new 
		// empty LinkedList at each index of the data array
		public HashTable(StringHasher o) {
			whatHash = o;
			customHash = true;
			for (int i = 0; i < data.length; i++)
				data[i] = new LinkedList<>();
		}
		
		// constructor - creates a new empty LinkedList at each index of the data array
		public HashTable() {
			for (int i = 0; i < data.length; i++)
				data[i] = new LinkedList<>();
		}
		
		//	adds the newItem at the index after being hashed and increases size
		public void add(String newItem) {
			int index = hash(newItem);
			index = Math.abs(index % data.length);
			if (get(newItem) == null) {	
				data[index].add(new Entry<Integer, String>(index, newItem));
				size++;
			}
			if ((double)size/data.length >= 1.00)  // rehash as soon as load factor reaches 1.00
				rehash();
		}
		
		public double indicesFilled() {
			double indicesFilled = (double)size/data.length;
			return indicesFilled;  // returns the percentage of indices filled
		}
		
		public void collisions() {
			int[] wordsAtIndex = new int[data.length];	// make an array the length of our data
			for (int i = 0; i < data.length; i++) {	
				int count = 0;
				for (Entry<Integer, String> e : data[i]) { // we count the number of words at that index 
					wordsAtIndex[i] = count + 1; 	// store the number of words at that index in the array
					count++;
				}
			}
			int deduct = 0, all = 0;
			for (int i = 0; i < wordsAtIndex.length; i++) {	
				if (wordsAtIndex[i] >= 2) {	// if the number of words at that index are greater than or equal to 2 then we have a collision
					deduct += 1;	// the first word does not count as a collision so that is why we had 1 to deduct
					all += wordsAtIndex[i];	// we add all of the number of words at that index 
				}
			}

			int totalColl = all - deduct; // the number of collisions = the number of items in the hashtable minus 1 from each entry that has 2 or more number of words
			System.out.println("Number of collisions: " + totalColl);
		}
		//  checks to if there are no duplicates by going to that index
		public String get(String someItem) {
			int index = hash(someItem);
			index = Math.abs(index % data.length);
			for (Entry<Integer, String> e : data[index]) {	// go through each entry at that linked list
				if (someItem.equals(e.getValue()))
					return e.getValue();
				}	
			return null;
		}
		
		private void rehash() {
			LinkedList<Entry<Integer, String>>[] oldData = data;
			data = new LinkedList[2*data.length + 1];		// ensures an odd number of spaces
			size = 0;
			
			for (int i = 0; i < data.length; i++) {
				data[i] = new LinkedList<>();
			}
			for (int i = 0; i < oldData.length; i++) {
				for (Entry<Integer, String> item : oldData[i]) {
					int j;
					if (customHash == true) {	// this ensures that we use the custom hash and not the regualr 
						j = whatHash.hash(item.getValue());
						j = Math.abs(j % data.length);
					}
					else	//  if custom hash is false then use regular hash
						j = hash(item.getValue());
					data[j].add(new Entry<Integer, String>(i, item.getValue()));
					size++;
				}
			}
		}
		
		public String toString() {
			String r = "";
			for (int i = 0; i < data.length; i++)
				r += i + " - " + data[i] + "\n";
			return r;
		}
		
		// testing purposes
		public static void main(String[] args) {
			//HashTable a = new HashTable();
			AsciiHash w = new AsciiHash();
			//WorstHash w = new WorstHash();
			//dbj2Hash w = new dbj2Hash();
			HashTable h = new HashTable(w);
			h.add("hello");
			h.add("dog");
			h.add("plays");
			h.add("z");
			h.add("fdc");
			h.add("mean");
			h.add("name");
			h.add("hell");
			h.add("what");
			h.add("df");
			h.add("i");
			//System.out.println(a.get("d"));
			System.out.println(h.toString());
			//System.out.println(collisions());
			
		}
}
