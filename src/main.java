import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class main {
	
	public static final int setCount = 4;
	
	//Variable that represents current turn, start at 0.
	public static int currentTurn = 0;
	
	//public list of players
	public static player[] players;
	
	//Initial initialization of Command Int
	private static Scanner in = new Scanner(System.in);
	
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
	
	public static void rotateTurn() {
		currentTurn++;
		
		//Reset if larger than limit
		if(currentTurn + 1 > players.length) {
			currentTurn = 0;
		}
	}
	
	
	public static boolean gameOverTF() {
		return false;
	}
	
	/*
	 * Cycle Turn Prompts
	 * TODO: EXTRACT CHECK IF VALID SYSTEM INTO METHOD
	 */
	public static void prompts(player[] players) {
		System.out.println("Player " + (currentTurn + 1));
		System.out.println(players[currentTurn].displayDeck());
		
		int playerInput;
		int cardInput;
		
		//Check prompt to steal from which player
		while(true) {
			System.out.println("Which player to steal from?" + " 1 - " + players.length);
			try {
				playerInput = in.nextInt() - 1; //Account for 0 index
				if(playerInput < 0 || playerInput >= players.length ) {
					System.out.println("Not within range.");
					continue;
				}
				break; //break if condition is correct
			} catch (InputMismatchException e) {
				System.out.println("Not valid response");
				in.nextLine();
			}			
		}

		//Check prompt to steal from which player
		while(true) {
			System.out.println("What card would you like to steal? 1 - 13");
			try {
				cardInput = in.nextInt(); 
				if(cardInput < 1 || cardInput > 13 ) {
					System.out.println("Not within range.");
					continue; //Reset if out of range
				}
				break; //break if condition is correct
			} catch (InputMismatchException e) { //reset if not int
				System.out.println("Not valid response");
				in.nextLine();
			}			
		}
		attemptTurn(cardInput, playerInput, players);
	}
	
	public static void attemptTurn(int card, int player, player[] playerlist) {
		//Attempt yoink, return true if valid
		boolean attemptResults = playerlist[player].removeCard(card);
		if (attemptResults == false) {
			System.out.println("Go Fish");
			playerlist[currentTurn].updateDeck();
		}
	}
	
	
	//Test code
	public static void main(String[] args) {
		cardDeckGenerator intl = new cardDeckGenerator(); //Create deck object
		
		System.out.println("WELCOME TO GO FISH");
		
		int playerCount = 0;
		
		while(true) {
			try {
				System.out.println("How many players would you like");
				playerCount = in.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Not valid response");
			}
			in.nextLine();
		}
		
		//System.out.println(cardDeckGenerator.drawPile.toString());

		//Create players based on player count input 
		//Also create decks for them 
		players = new player[playerCount];
		for (int i = 0; i < playerCount; i++) {
			players[i] = new player();
			players[i].createDeckForPlayer();
		}
		
		
	//game Loop will loop while gameOverCheck returns false
	while (gameOverTF() == false) {
		System.out.println();
		
		//prompt player of choices
		//and make choice 
		prompts(players);
		
		//rotate turn
		rotateTurn();
		
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
