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
	public static void checkMatch() {
		Map<Integer, Integer> duplicateCount = new HashMap<>();
		
		for(int n : players[currentTurn].playerDeck) {
			//getOrDefault will attempt to find an existing key, will return value if found
			//otherwise returns 0 if no key found (set default)
			int count = duplicateCount.getOrDefault(n, 0) + 1; 
			if(count >= setCount) {
				System.out.println("Set Found! GO FISH");
				players[currentTurn].removeSet(n);
				checkMatch(); //Repeat loop to check for more sets
				return;
			}
			duplicateCount.put(n, count);
		}
		
	}
	
	public static void rotateTurn() {
		currentTurn++;
		//Reset if larger than limit
		if(currentTurn + 1 > players.length) {
			currentTurn = 0;
		}
	}
	
	//Will check if game is over and return true if game is over
	//Game is over if deck is empty and every player runs out of cards
	public static boolean gameOverCheck() {
	    return cardDeckGenerator.drawPile.isEmpty() && allPlayersEmpty();
	}
	
	//Helper method to check if every player has an empty deck
	public static boolean allPlayersEmpty() {
	    for (player p : players) {
	        if (!p.playerDeck.isEmpty()) {
	            return false;
	        }
	    }
	    return true;
	}
		
	
	/*
	 * Cycle Turn Prompts
	 */
	public static void prompts() {
		//Base Condition, check if player can play, otherwise ignore.
		if (!players[currentTurn].canPlay) {
			return;
		}
		
		System.out.println("Player " + (currentTurn + 1));
		System.out.println(players[currentTurn].displayDeck());
		
		//playerInput
		int playerInput = (checkMethod(("Which player to steal from?" + " 1 - " + players.length), 1, players.length, true)) - 1;
		
		//Get Card Input (Makes sure within Conditions)
		int cardInput = checkMethod("What card would you like to steal? 1 - 13", 1, 13, false);
		
		//Attempt Turn
		attemptTurn(cardInput, playerInput);
	}
	
	
	public static void attemptTurn(int card, int player) {
		//Attempt yoink, return true if valid
		boolean attemptResults = players[player].removeCard(card);
		if (attemptResults == false) {
			System.out.println("Go Fish");
			players[currentTurn].updateDeck();
			return;
		}
		players[currentTurn].addCard(card);
	}
	
	//Holds unique outputs that will trigger if specific method called it
	public static int checkMethod(String consoleOutput, int lowerLimit, int upperLimit, boolean specialCondition) {
		//Intial Prompts
		while(true) {
			try {
				System.out.println(consoleOutput);
				int input = in.nextInt();
				if(input < lowerLimit || input > upperLimit ) {
					System.out.println("Not within range.");
					continue; //Reset if out of range
				}
				//Make Sure player is trying to steal from itself
				if(input - 1 == currentTurn && specialCondition) {
					System.out.println("Players can't steal from themselves");
					continue;
				}
				return input; //end point 
			} catch (InputMismatchException e) {
				System.out.println("Not valid response");
				in.nextLine();
			}
		}
	}

	//Test code
	public static void main(String[] args) throws InterruptedException {
		//cardDeckGenerator intl = new cardDeckGenerator(); //Create deck object

		System.out.println("WELCOME TO GO FISH");
		
		
		int playerCount = checkMethod("How many players would you like", 1, 6, false);
		
		//Create players based on player count input 
		//Also create decks for them 
		players = new player[playerCount];
		for (int i = 0; i < playerCount; i++) {
			players[i] = new player();
			players[i].createDeckForPlayer();
		}


		//game Loop will loop while gameOverCheck returns false
		while (gameOverCheck() == false) {
			//Check to see if player can continue playing
			players[currentTurn].checkCanPlay();
			
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
					+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); 
			//Create illusion of console being cleared
			
			for (int i = 0; i < players.length; i++) {
				System.out.println(players[i].displayDeck());
			}
			
			System.out.println(cardDeckGenerator.drawPile);
			
			//prompt player of choices
			//and make choice 
			prompts();
			
			//Check for match
			checkMatch();
			
			//rotate turn
			rotateTurn();
			
			//Include delay so player can read input before reset
			Thread.sleep(1000); 
		}
		
		//Post game Loop
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
				+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); 
		System.out.println("GAME OVER");
	}
}
