import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * MainGame serves as the core controller for the Go Fish program. 
 * 
 * <p>
 * This class manages:
 * </p>
 * <ul>
 * <li> Player Creation and Initialization</li>
 * <li> Turn rotation and overall game flow. </li>
 * <li> Input validation and user prompts</li>
 * <li> Card Drawing logic and stealing mechanics </li>
 * <li> Detections and removal of sets</li>
 * <li> Game ending conditions </li>
 * </ul>
 * 
 * <p>
 * This class contains the main loop of the game, which will continue until the draw 
 * pile is empty hand all players have no remaining cards. It will coordinate all gameplay 
 * actions by calling supporting methods in player class and the cardDeckGeneration class. 
 * 
 * 
 * (Fun Fact: I have never played Go Fish -Erick)
 * </p>
 * 
 * @author Erick Ruiz, Justin Truong, Marcus Underwood
 */
public class MainGame {
	
	//Set Count
	public static final int SETCOUNT = 4;
	
	//Debug counter
	public static int debugCount = 0;
	
	//Variable that represents current turn, start at 0.
	public static int currentTurn = 0;
	
	//public list of players
	public static player[] players;
	
	//Initial initialization of Command Int
	private static Scanner in = new Scanner(System.in);
	
	
	/**
	 * Determines whether the game has ended
	 * <p>
	 * Games end when two conditions are met:
	 * 		-The draw pile is completely empty
	 * 		-Every player has an empty hand. 
	 * </p>
	 * @return returns true if both conditions were met. 
	 */
	public static boolean gameOverCheck() {
	    return cardDeckGenerator.drawPile.isEmpty() && allPlayersEmpty();
	}
	
	/**
	 * Checks whether all players have an empty hand
	 * <p>
	 * This helper method iterates through every player in the game and 
	 * examines deck to determine if cards still exist in deck. 
	 * </p>
	 * @return Returns true if all players have empty decks; false otherwise. 
	 */
	public static boolean allPlayersEmpty() {
	    for (player p : players) {
	        if (!p.playerDeck.isEmpty()) {
	            return false;
	        }
	    }
	    return true;
	}	
	
	/**
	 * Handles all prompts and actions for the current player's turn  
	 * 
	 * <p>
	 * This method will perform the following:
	 * <ul>
	 * <li>Display current player's hand </li>
	 * <li>Ask which player to steal from</li>
	 * <li>Ask which card they want to request </li>
	 * <li>Attempt action through {@code attemptTurn(int, int)}. </li>
	 * </ul>
	 * Method will check base case to determine if turn is permitted and return false
	 * immediately (Base Case). 
	 * </p>
	 * 
	 * @return true if player earned another turn by making match; false otherwise
	 */
	public static boolean prompts() {
		//Base Condition, check if player can play, otherwise ignore.
		if (!players[currentTurn].canPlay) {
			return false;
		}
		
		System.out.println("Player " + (currentTurn + 1));
		System.out.println(players[currentTurn].displayDeck());
		
		//Ask player of steal choice
		int playerInput = (checkMethod(("Which player to steal from?" + " 1 - " 
				+ players.length), 1, players.length, true)) - 1;
		
		//Get Card Input (Makes sure within Conditions)
		int cardInput = checkMethod("What card would you like to steal? 1 - 13", 1, 13, false);
		
		//Attempt steal turn 
		return attemptTurn(cardInput, playerInput);
	}
	
	/**
	 * Attempts to steal card from another player and determines results of turn
	 * 
	 * <p>
	 * This method will handle the main logic of the player's turn:
	 * <ul> 
	 * <li> If the targeted player has the requested card, the card is yoinked and added 
	 * to the current player's hand. In this case, the player does not get another turn. </li>
	 * <li> If the targeted player does not have the card, the current player has to go 
	 * "go fish" by drawing a new card from the deck. </li>
	 * <li> If the drawn card matches the requested card, the system returns true and 
	 * player gets to play again </li>
	 * </ul>
	 * </p>
	 * 
	 * @param card the card value the current player is requesting
	 * @param player the index of the player being asked for the card
	 * @return true if the player draws the exact card they asked for (extra turn);
	 *         false otherwise
	 */
	public static boolean attemptTurn(int card, int player) {
		//Attempt yoink, return true if valid
		boolean attemptResults = players[player].removeCard(card);
		
		//If target player does not have card
		if (attemptResults == false) {
			System.out.println("Go Fish");
			int deckCard = players[currentTurn].updateDeck();
			
			//Special Case: Player draws requested card
			if (deckCard == card) {
				System.out.println("Card Matched! Play Again!");
				return true; //Another Turn is allowed
			}
			return false; //Turn ends
		}
		//Case: Player has card
		players[currentTurn].addCard(card);
		return false;  //Return false since player cannot play again
	}
	
	
	/**
	 * Prompts user for an integer input and validates the user input according to provide rules. 
	 * <p>
	 * This method repeatedly asks for input until the user provides a valid integer within the 
	 * specified range. 
	 * 
	 * When ({@code specialCondition} is enabled, the method ensures the user does not select the 
	 * current player. (Used in case for choosing player to ask in Go Fish).
	 * </p>
	 * 
	 * @param consoleOutput The prompt message displayed to the user.
	 * @param lowerLimit The minimum valid integer (inclusive).
	 * @param upperLimit  The maximum valid integer (inclusive).
	 * @param specialCondition  If true, prevents selecting {@code currentTurn}
 *                           	(i.e., selecting oneself).
	 * @return The validated integer input provided by the user.
	 * @throws InputMismatchException This exception never leaves the method 
	 * 								  and will catch non-integer input. 	
	 */
	public static int checkMethod(String consoleOutput, int lowerLimit, int upperLimit, boolean specialCondition) {
		while(true) {
			try {
				System.out.println(consoleOutput);
				int input = in.nextInt();
				
				 // Check Parameter Range
				if(input < lowerLimit || input > upperLimit ) {
					System.out.println("Not within range.");
					continue; //Reset if out of range
				}
				
				//Unique Case:
				//Cannot Select Yourself
				if(input - 1 == currentTurn && specialCondition) {
					System.out.println("Players can't steal from themselves");
					continue;
				}
				return input; //end point 
				
			} catch (InputMismatchException e) {
				//Check for non-numeric input
				System.out.println("Not valid response");
				in.nextLine(); //Clear invalid input
			}
		}
	}
	
	/**
	 * Method will check to see if current player's deck has a set.
	 * If an available is set, is in deck, set will be removed from 
	 * player deck and player totalSetCount will increase by 1. 
	 * 
	 * Included recursion feature to account for multiple sets
	 * (Might not be realistic, might be removed. Not sure how often multiple sets appear) 
	 * @return Returns true if set was detected, returns false otherwise. 
	 */
	public static boolean checkMatch() {
		Map<Integer, Integer> duplicateCount = new HashMap<>();
		
		//Check each card for match count, check if card exists in map, if card exists 
		//increase value of card in map by 1. Otherwise, add card to map with value being 1.
		for(int n : players[currentTurn].playerDeck) {
			int count = duplicateCount.getOrDefault(n, 0) + 1; 
			if(count >= SETCOUNT) {
				System.out.println("Set Found! GO FISH");
				players[currentTurn].removeSet(n);
				checkMatch(); //Repeat loop to check for more sets
				return true;
			}
			//Update Count 
			duplicateCount.put(n, count);
		}
		return false;
	}
	
	/**
	 * Advances the {@code currentTurn} to the next player
	 * 
	 * <p>
	 * Method that increments currentTurn variable
	 * Will also reset if larger than player count 
	 * </p>
	 */
	public static void rotateTurn() {
		currentTurn++;
		if (currentTurn >= players.length) {
			currentTurn = 0;
		}
	}
	
	/**
	 * Prints a formatted scoreboard showing each player's total set count, ordered
	 * from highest to lowest. 
	 * 
	 * <p>
	 * This method iterates through every single possible score values ranging from 13 to 0
	 * while checking to see if a player if their {@code totalSetCount} matches the current score. 
	 * Matching players are then printed one by one by highest to lowest.
	 * </p>
	 * 
	 * (This method uses a nested loop, I try to stray away from nested loops for the most part
	 * but felt fine using here since the scoreboard only needs to print out once per game on a 
	 * relatively small list)
	 */
	public static void scoreboard() {
		System.out.println("Scoreboard: ");
		//Loop Through Score Limits (Highest Score possible is 13) 
		for (int score = 13; score > -1; score--) { 
			for (int j = 0; j < players.length; j++) { //Loop through players and match set
				if (players[j].totalSetsCount == score) {
					System.out.println("\tPlayer " + (j + 1) + ": " + players[j].totalSetsCount);
				}
			}
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		//------------------Intial Prompts----------------------------
		System.out.println("WELCOME TO GO FISH\n");
		System.out.println("Current version assumes all players are different users\n");
		System.out.println("Current Version will also label the following:");
		System.out.println("		Jack = 11,");
		System.out.println("		Queen == 12,");
		System.out.println("		King == 13\n");
		System.out.println("Bots on the way in 2029 (*_*) \n");
		int playerCount = checkMethod("How many players would you like? (2-6)", 2, 6, false);
		
		//Create players based on player count input 
		//Also create decks for them 
		players = new player[playerCount];
		for (int i = 0; i < playerCount; i++) {
			players[i] = new player();
			players[i].createDeckForPlayer();
		}
		
		//		-------------Game Loop------------------------
		
		
		//game Loop will loop while gameOverCheck returns false
		while (gameOverCheck() == false) {
			//Check to see if player can continue playing
			players[currentTurn].checkCanPlay();
			
			//Create illusion of console being cleared (Account for full screen console case)
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
					+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
					+ "\n\n\n\n\n\n\n\n\n\n\n\n"); 
			
			
			//XXX //Debug Code was kept in code for quick testing
			//-------------------DEBUG CODE DISPLAY TEXT------------------------------
			
//			for (int i = 0; i < players.length; i++) {
//				System.out.println("PLayer " + (i + 1) + 
//						": "+ players[i].displayDeck());
//			}
//			System.out.println("Current Draw Pile: " + cardDeckGenerator.drawPile + '\n');
			
			//Debug NOTE (If testing with debug text displayed and one of the players
			//is out of cards, it wont let the player play as expected but will still
			//print out debug code on their turn. This is a quirk of the debug display code and the
			//game loop.)
			
			//XXX //Debug Code was kept in code for quick testing
			//-----DEBUG CODE GIVE EVERYONE MORE CARDS (Speeds up game to test)-------
//			if (debugCount <= 19) {
//				debugCount++;
//				players[currentTurn].updateDeck();
//				rotateTurn();
//				continue;
//			}
			
			///XXX //Debug Code was kept in code for quick testing
			//-----DEBUG CODE TEST SCOREBOARD-----------------------------------------
//			players[0].totalSetsCount = 5; //2
//			players[1].totalSetsCount = 2; //5
//			players[2].totalSetsCount = 1; //6
//			players[3].totalSetsCount = 8; //1
//			players[4].totalSetsCount = 4; //3
//			players[5].totalSetsCount = 2; //4
//
//			scoreboard();
			
			//-------------------------------------------------------------------------
			//prompt player of choices
			//and make choice 
			boolean drawOutcome = prompts();
			
			//Check for match
			boolean checkOutcome = checkMatch();
			
			//Include delay so player can read input before reset
			Thread.sleep(1000); 
			
			//if match is detected, prevent turn rotate to allow player to play again
			if (drawOutcome || checkOutcome) { 
				continue; 
			}
			//rotate turn
			rotateTurn();	
		}
		
		//Post game Loop
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
				+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
				+ "\n\n\n\n\n\n\n\n\n\n\n\n"); 
		System.out.println("GAME OVER\n");
		scoreboard();
	}
}
