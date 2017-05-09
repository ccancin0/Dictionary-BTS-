import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class BSTSpellChecker {

	private static BinarySearchTree<String> data = new BinarySearchTree<>();
	BSTSet<String> closeMatches = new BSTSet<>();	// same as the hashtablespellchecker
	
	public void add(String newItem) {
		data.addIterative(newItem);
	}
	
	public boolean contains(String s) {
		return (data.findIterative(s) != null);
	}
	
	// pretty much the same as the hashtablespellchecker
	public void ListReaderBalanced()  {
		try {
			Scanner input = new Scanner(System.in);
			System.out.print("Enter file name (don't forget .txt) or 2 for Project file: ");
			String i = input.nextLine();
			Scanner s;
			if (i.equals("2"))
				s = new Scanner(new File("Project2_wordlist.txt"));
			else
				s = new Scanner(new File(i));
			String[] a = new String[109562];	// could have simply had two while loops, one to loop thru and count the number of words 
			int j = 0;							// in the file and then after that another one to add everything in it
			long startTime = System.currentTimeMillis();
			System.out.println("Reading file...");
			while (s.hasNext()){
			    a[j] = (s.next());
			    j++;
			}
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("It took " + totalTime + " milliseconds!");
			s.close();
			BalanceTree(0, a.length - 1, a); // we call our method that will balance our tree
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
				String[] words = b.split("\\s+");
				for (int k = 0; k < words.length; k++) {
				    words[k] = words[k].replaceAll("[^\\w]", "");
				    if (contains(words[k]) == true) {
				    	System.out.println(words[k] + " is spelled correctly.");
				    }
				    else {
				    	if (closeMatches(words[k]) != null) {
				    		closeMatches = new BSTSet<>();
				    	if (closeMatches(words[k]).toString() == "") {
				    		System.out.println("What do you mean? (By Justin Beiber)");
				    		System.out.println("This means the word cannot be found.");
				    	}
				    	else
				    	{
				    		System.out.println(words[k] + " is not spelled correctly.");
				    		System.out.println("Did you mean: " + closeMatches(words[k]));
				    	}
				    	}
				    }
				}
		}catch (InputMismatchException e) {
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
	
	// similar to above and hashtablespellchecker
	public void ListReaderUnbalanced()  {
		try {
			Scanner input = new Scanner(System.in);
			System.out.println("Enter file name (don't forget .txt) or 2 for Project file: ");
			String i = input.nextLine();
			Scanner s;
			if (i.equals("2"))
				s = new Scanner(new File("Project2_wordlist.txt"));
			else
				s = new Scanner(new File(i));
			int j = 0;
			long startTime = System.currentTimeMillis();
			System.out.println("Reading file...");
			while (j < 100) {//s.hasNext()){ // only did the first 100 words but could be easily changed to do entire list
			    add(s.next());
			    j++;
			}
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("It took " + totalTime + " milliseconds!");
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
				String[] words = b.split("\\s+");
				for (int k = 0; k < words.length; k++) {
				    words[k] = words[k].replaceAll("[^\\w]", "");
				    if (contains(words[k]) == true) {
				    	System.out.println(words[k] + " is spelled correctly.");
				    }
				    else {
				    		if (closeMatches(words[k]) != null) {
				    			closeMatches = new BSTSet<>();
				    		if (closeMatches(words[k]).toString() == "") {
					    		System.out.println("What do you mean? (By Justin Beiber)");
					    		System.out.println("This means the word cannot be found.");
					    	}
					    	else
					    	{
					    		System.out.println(words[k] + " is not spelled correctly.");
					    		System.out.println("Did you mean: " + closeMatches(words[k]));
					    	}
					    	}
					    }
					}
		}catch (InputMismatchException e) {
			System.out.println("You broke me!");
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
	
	// a modified binary seach method - we had the middle element each time do it recursively
	public void BalanceTree(int start, int end, String[] a) {
			if (a.length == 0);
			else if (start > end);
			else if (end - start == 0 && end != a.length) {
				add(a[start]);
			}
			else {
				add(a[(end + start)/2]);
				BalanceTree(start, (end + start)/2 - 1, a); // adding to the left
				BalanceTree((start + end)/2 + 1, end, a); // adding to the right	
			}
	}
	
	// same as before
	public BSTSet<String> closeMatches(String s) { 
		closeMatches = closeMatches.union(swappingChar(s));
		closeMatches = closeMatches.union(replacingAndAddingChar(s));
		closeMatches = closeMatches.union(deleteChar(s));
		closeMatches = closeMatches.union(insertSpace(s));
		return closeMatches;
	}
	
	// same as before
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
	
	// same as before
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
				swappingChar(r);
				swappingChar(c.toString());
				swappingChar(end);
				if (this.contains(r))
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
	
	// same as before
	public BSTSet<String> deleteChar(String s) {
		for (int i = 0; i < s.length(); i++) {
			StringBuilder c = new StringBuilder(s);
			c.deleteCharAt(i);
			swappingChar(c.toString());
			if (this.contains(c.toString())) {
				closeMatches.add(c.toString());
			}
		}
		return closeMatches;
	}
	
	// same as before
	public BSTSet<String> insertSpace(String s) {
	    int j = 1;
	    for (int i = 0; i < s.length(); i++) {
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
	
	// testing purposes
	public static void main(String[] args) {
		BSTSpellChecker r = new BSTSpellChecker();
		r.ListReaderBalanced();	
	}
}
