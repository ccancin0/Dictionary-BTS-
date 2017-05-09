import java.util.InputMismatchException;
import java.util.Scanner;
// most of the code for this project is the same for both spellcheckers like the closeMatches, 
// file reader, so I didn't comment on those twice

public class SpellCheckerClient {
	
	public static void main(String[] args) {
		SpellCheckerClient client = new SpellCheckerClient();
		client.run();
	}

	// the client 
	public void run() {
		int restart = 0;
		do {
			Scanner input = new Scanner(System.in);
			Scanner version = new Scanner(System.in);
			System.out.println("Welcome to the best Spell Checker!");
			System.out.println("What spell checker would you like to use?");
			System.out.println("------------------------------------------------");
			System.out.println("1. Unbalanced BST " + "\n" + 
							   "2. Balanced BST" + "\n" +
							   "3. Hash Table (WorstHash)" + "\n" +
							   "4. Hash Table (AsciiHash)" + "\n" +
							   "5. Hash Table (dbj2Hash)");
			System.out.println("------------------------------------------------");
			System.out.println("Enter the number of your desired spell checker: ");
			try {
				int spellCheck = version.nextInt();
				while (spellCheck < 1 || spellCheck > 5) {	// enter a valid number 
					System.out.println("Choice is not available! Try again.");
					System.out.println("Enter the number of your desired spell checker: ");
					spellCheck = version.nextInt();
				}
				
				// create a new instance of each class depending on the number they choose
				switch(spellCheck) {	// switch statement seemed the most easiest/efficient
					case 1: BSTSpellChecker spell = new BSTSpellChecker();
							spell.ListReaderUnbalanced();
							break;
					case 2:
							BSTSpellChecker spell2 = new BSTSpellChecker();
							spell2.ListReaderBalanced();
							break;
					case 3:
							HashTableSpellChecker spell3 = new HashTableSpellChecker();
							WorstHash worst = new WorstHash();
							spell3.listReader(worst);
							break;
					case 4: HashTableSpellChecker spell4 = new HashTableSpellChecker();
							AsciiHash ascii = new AsciiHash();
							spell4.listReader(ascii);
							break;
					case 5: HashTableSpellChecker spell5 = new HashTableSpellChecker();
							dbj2Hash dbj2 = new dbj2Hash();
							spell5.listReader(dbj2);
							break;
					default: System.out.println("Uknown Error");
							break;
				}
			}catch (Exception e) {	// in case they try to enter a string 
				System.out.println("Not a valid input!");
			}
			
		try {
			System.out.println("Try the other spell checkers (0 = Yes | Anything else to exit)? ");
			restart = input.nextInt();
		}catch (InputMismatchException e) {
			System.out.println("Goodbye!");
			restart = 1;
		}
		}while(restart == 0);
	}
}
