import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HashTableSpellChecker {
	
	HashTable table = new HashTable();
	BSTSet<String> closeMatches = new BSTSet<>();	// keeps all the close matches in here when going 
													// into the different methods
	
	public void add(String newItem) {
		table.add(newItem);
	}
	
	public boolean contains(String s) {
		return (table.get(s) != null);
	}
	
	
	public void listReader(StringHasher o)  {
		try {
			table = new HashTable(o);	// create a table with the specific string hasher
			Scanner input = new Scanner(System.in);
			System.out.println("Enter file name (don't forget .txt) or 2 for Project file: ");
			String i = input.nextLine();
			Scanner s;
			System.out.println(i);
			if (i.equals("2"))
				s = new Scanner(new File("Project2_wordlist.txt"));
			else
				s = new Scanner(new File(i));
			long startTime = System.currentTimeMillis();
			System.out.println("Reading file...");
			while (s.hasNext()){
			   table.add(s.next());
			}
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("It took: " + totalTime + " milliseconds!");
			table.collisions();
			System.out.println("Percentage of indices occupied: " + (table.indicesFilled() * 100) + "%");
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(0);
		}
		
		int restart = 0;
		Scanner inp = new Scanner(System.in);
		do
		{
			try {
				System.out.println("Enter to see if word is spelled correctly: ");
				String b = inp.nextLine();
				String[] words = b.split("\\s+");	// makes sure we cut the spaces out and store each word individually in an array
				for (int k = 0; k < words.length; k++) {
				    words[k] = words[k].replaceAll("[^\\w]", ""); 
				    System.out.println("Looking...");
				    if (contains(words[k]) == true) {	// word is found in the file or list
				    	System.out.println(words[k] + " is spelled correctly.");
				    }
				    else {
				    	if (closeMatches(words[k]) != null) {	// ensures that whenever we have more than two words, 
			    			closeMatches = new BSTSet<>();		//the close matches of those words wont appear as close matches of the other words
			    		if (closeMatches(words[k]).toString() == "") {	// if we found no valid word for the input after applying the transformations
				    		System.out.println("What do you mean? (By Justin Beiber)");	// like numbers or jdsahghjsagh
				    		System.out.println("This means the word cannot be found.");
				    	}
				    	else
				    	{
				    		System.out.println(words[k] + " is not spelled correctly.");
				    		System.out.println("Looking...");
				    		System.out.println("Did you mean: " + closeMatches(words[k]));
				    	}
				    	}
				    }
				}

			
		}catch (InputMismatchException e) {	// for input thats not of type int for the do-while loop
			System.out.println("Not a valid input!");
			System.exit(0);
		}
		
		try {
			System.out.println("Continue looking for more words or head back to Main menu (0 = Yes | Anything else = No)? ");
			restart = inp.nextInt();
		}catch (InputMismatchException e) {
			restart = 1;
		}
		}while(restart == 0);
	}
	
	// attach all the close matches to our global variable closeMatches after calling each method
	public BSTSet<String> closeMatches(String s) { 
		closeMatches = closeMatches.union(swappingChar(s));
		closeMatches = closeMatches.union(replacingAndAddingChar(s));
		closeMatches = closeMatches.union(deleteChar(s));
		closeMatches = closeMatches.union(insertSpace(s));
		return closeMatches;
	}
	
	//  we swap adjacent characters to see if we find a valid word in the list/file
	public BSTSet<String> swappingChar(String s) {
		for (int i = 0; i < s.length() - 1; i++){
			String r = s.substring(0, i);
			r = r + s.charAt(i + 1);
			r = r + s.charAt(i);
			r = r.concat(s.substring(i + 2));
			if (this.contains(r))
				closeMatches.add(r);	
		}
		return closeMatches;
	}
	
	// we replace each character and also add a character 
	public BSTSet<String> replacingAndAddingChar(String s) {
		char[] b = {'a', 'b', 'c', 'd', 'e', 'f', 'g',
				'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z'};
		
		for (int i = 0; i < s.length(); i++) {
			for (int j = 0; j < b.length; j++) {
				StringBuilder c = new StringBuilder(s);
				c.setCharAt(i, b[j]);
				String r = s.substring(0, i) + b[j] + s.substring(i, s.length());
				String end = s.substring(0, s.length()) + b[j];
				swappingChar(r);	// we call swap on this new word to see if that will give us a valid word
				swappingChar(c.toString());	// same here
				swappingChar(end);	// and here
				if (this.contains(r))	// just add it if it does exist in the list/file
					closeMatches.add(r);
				else if (this.contains(end))
					closeMatches.add(end);
				else if (this.contains(c.toString())) {
					closeMatches.add(c.toString());
				}
			}
		}
		return closeMatches;
	}
	
	// we delete a character at any position in the word 
	public BSTSet<String> deleteChar(String s) {		
		for (int i = 0; i < s.length(); i++) {
			StringBuilder c = new StringBuilder(s);  // this makes it easier to manipulate strings
			c.deleteCharAt(i);
			swappingChar(c.toString());	// swap to see if new word is valid 
			if (this.contains(c.toString())) { 
				closeMatches.add(c.toString());
			}
		}
		return closeMatches;
	}
	
	// we insert a space to see if we can form any smaller words
	public BSTSet<String> insertSpace(String s) {
	    int j = 1;
	    for (int i = 0; i < s.length(); i++) { // insert a space in between each character 
	    	String r = s.substring(0, j);	
	    	String l = s.substring(j, s.length());
	    	j++;
	        if (this.contains(r) && this.contains(l)) {
	        	closeMatches.add(r);
	        	closeMatches.add(l);
	        }
	    }
	    return closeMatches;
	}
	
	public String toString() {
		return table.toString();
	}
	
	// testing purposes
	public static void main(String[] args) {
		HashTableSpellChecker a = new HashTableSpellChecker();
		//a.listReader();
	}
}
