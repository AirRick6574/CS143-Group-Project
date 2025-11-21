import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class main {
	
	public static final int setCount = 4;
	
	/*	Create Map to determine if duplicates exist. 
	 *	Each array element will be transferred to map and its value will be its 
	 *	duplicate count
	 *	If Duplicate count is bypassed, we know if there is a pair.
	 *
	 *	LIMIT AT THE MOMENT: CAN ONLY REMOVE ONE PAIR AT A TIME 
	 *	UPDATE(FIXED) Can check for multiple
	 */
	public static void checkMatch(player player) {
		Map<Integer, Integer> duplicateCount = new HashMap<>();
		
		for(int n : player.playerDeck) {
			//getOrDefault will attempt to find an existing key, will return value if found
			//otherwise returns 0 if no key found (set default)
			int count = duplicateCount.getOrDefault(n, 0) + 1; 
			if(count >= setCount) {
				System.out.println("Match Found!");
				player.removeSet(n);
				checkMatch(player); //Repeat loop to check for more sets
				return;
			}
			duplicateCount.put(n, count);
		}
		System.out.println("NO SETS IN DECK ATM");
	}
	
	public static boolean gameOverTF() {
		return false;
	}
	
	//Test code
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
        System.out.println("WELCOME TO GO FISH");
        System.out.println("How many players would you like");

       try {
    	   int playerCount = in.nextInt();
       } catch (InputMismatchException e) {
    	   System.out.print("Please enter a player count :(");
       }
//		//Create deck by creating object 
//		cardDeckGenerator intl = new cardDeckGenerator();
//		
//		System.out.println(cardDeckGenerator.drawPile.toString());
//		
//		System.out.println();
//		//Create players 
//		player player1 = new player(); 
//		player1.createDeckForPlayer();
//		System.out.println("Player Deck2 is " + player1.playerDeck.toString());
//		checkMatch(player1);
//		for(int i = 0; i < 42; i++) {
//			player1.updateDeck();
//		}
//		
//		
//		System.out.println("Player Deck2 is " + player1.playerDeck.toString());
//		System.out.println(cardDeckGenerator.drawPile.toString());
//		checkMatch(player1);
//		System.out.println("Player Deck2 is " + player1.playerDeck.toString());
//		System.out.println(cardDeckGenerator.drawPile.toString());
//		System.out.println(player1.totalSetsCount);
//		
		
		
//		player player2 = new player(); 
//		player player3 = new player(); 
//		
//		player1.createDeckForPlayer();
//		player2.createDeckForPlayer();
//		player3.createDeckForPlayer();
//		
//		System.out.println(player1.playerDeck);
//		System.out.println(player2.playerDeck);
//		System.out.println(player3.playerDeck);
//		
//		System.out.println();
//		
//		System.out.println(cardDeckGenerator.drawPile.toString());
//		
//		//(Scenario) Player drew a card from draw pile
//		System.out.println("Player drew a card from draw pile ");
//		System.out.println("Player Deck2 is " + player2.playerDeck.toString()); //Display player 2 deck
//		player2.playerDeck.add(5); //updates deck by grabbing from draw pile 
//		player2.playerDeck.add(5); //updates deck by grabbing from draw pile 
//		checkMatch(player2);
//		player2.playerDeck.add(5); //updates deck by grabbing from draw pile 
//		player2.playerDeck.add(5); //updates deck by grabbing from draw pile 
//		System.out.println("Player Deck2 is " + player2.playerDeck.toString()); //Display player 2 deck
//		checkMatch(player2);
//		System.out.println("Player Deck2 is " + player2.playerDeck.toString()); //Display player 2 deck
		
		
		
	}
}
