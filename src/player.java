import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

/*
 * 
 */
public class player {
	//Player Deck Array
	List<Integer> playerDeck = new ArrayList<>();
	
	//Integer counting matching pairs player currently has
	int totalSetsCount = 0;
	
	//Boolean to determine if player can continue playing
	boolean canPlay = true;
	
	//Create deck
	public void createDeckForPlayer() {
		this.playerDeck = cardDeckGenerator.createDeck(5);
	}
	
	//Create method to remove card, return true if
	//card existed and was removed
	public boolean removeCard(int card) {
		return this.playerDeck.remove(Integer.valueOf(card));
	}
	
	//Create method to update deck 
	public int updateDeck() {
		try {
			int card = cardDeckGenerator.getCard();
			this.playerDeck.add(card);
			return card; //return card to check if draw matched player input steal
		} catch (EmptyStackException e) { //Creates exception to prevent card from being added
			//Set empty deck to true
			System.out.println("Draw Pile is empty");
			cardDeckGenerator.emptyDeck = true; 
			return -1;
		}
	}
	
	//Add card from stolen player
	public void addCard(int card) {
		this.playerDeck.add(card);
	}
	
	//Method to remove set and update accordingly
	public void removeSet(int card) {
		//Update Set Count
		totalSetsCount++;
		
		//Clear Set from deck
		this.playerDeck.removeAll(List.of(card)); 
	}
	
	public String displayDeck() {
		return "Cards: " + playerDeck;
	}
	
	public void checkCanPlay() {
		if (canPlay == false) { //condition to ignore print statement
			return;
		}
		else if (cardDeckGenerator.emptyDeck && playerDeck.size() == 0) {
			System.out.println("You can no longer play");
			canPlay = false;
		}
	}
	

	//----------------------------------Practice Situational Experiment Code------------------------------------------
	public static void main(String[] args) {
		//Create card deck object and pile (will be implemented in main)
		//cardDeckGenerator intl = new cardDeckGenerator();
		
		//Player 1 Object
		player player1 = new player(); 
		
		//Print Player Deck (Will be empty since player starts with nothing) 
		//Might change so that every player will have a deck created in constructor
		System.out.println(player1.playerDeck.toString());
		
		//Display Draw Pile 
		System.out.println(cardDeckGenerator.drawPile.toString());
		
		//Create deck for player 1 and display it
		player1.createDeckForPlayer();
		System.out.println("Player Deck is " + player1.playerDeck.toString());
		
		//Display how draw pile looks like currently
		System.out.println(cardDeckGenerator.drawPile.toString());
		
		
		//(Scenario) Player got card taken 
		System.out.println("Player 1 got card " + 9 + " removed!");
		player1.removeCard(9); //removes card
		System.out.println("Deck now is " + player1.playerDeck.toString()); //displays player 1 deck
		
		//Player 2 Object
		player player2 = new player(); 
		System.out.println(cardDeckGenerator.drawPile.toString()); //Displays current draw pile
		
		//Create Player 2 deck by grabbing from draw pile
		player2.createDeckForPlayer();
		System.out.println("Player Deck2 is " + player2.playerDeck.toString()); //Displays deck
		 //Displays draw deck after player created deck (cards that will in last display should be missing and in players 2 deck)
		System.out.println(cardDeckGenerator.drawPile.toString());  
		
		//(Scenario) Player drew a card from draw pile
		System.out.println("Player drew a card from draw pile ");
		player2.updateDeck(); //updates deck by grabbing from draw pile 
		System.out.println(cardDeckGenerator.drawPile.toString()); //displays draw pile after player 2 grabbed a card
		System.out.println("Player Deck2 is " + player2.playerDeck.toString()); //Display player 2 deck
		
		//(Scenario) Player drew a card from draw pile
		System.out.println("Player drew a card from draw pile ");
		player2.playerDeck.add(5); //updates deck by grabbing from draw pile 
		player2.playerDeck.add(5); //updates deck by grabbing from draw pile 
		player2.playerDeck.add(5); //updates deck by grabbing from draw pile 
		System.out.println("Player Deck2 is " + player2.playerDeck.toString()); //Display player 2 deck
		player2.playerDeck.removeAll(List.of(5));
		System.out.println("Player Deck2 is " + player2.playerDeck.toString()); //Display player 2 deck
		
		
		System.out.println(player2.displayDeck());
		
		
		
	}
}
