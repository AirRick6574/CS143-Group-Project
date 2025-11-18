import java.util.ArrayList;
import java.util.List;

/*
 * 
 */
public class player {
	//Player Deck Array
	List<Integer> playerDeck = new ArrayList<>();
	
	
	//Create deck
	public void createDeckForPlayer() {
		this.playerDeck = cardDeckGenerator.createDeck(5);
	}
	
	//Create method to remove card
	public void removeCard(int card) {
		this.playerDeck.remove(Integer.valueOf(card));
	}
	
	//Create method to update deck 
	public void updateDeck() {
		this.playerDeck.add(cardDeckGenerator.getCard());
	}
	
	//Practice Situational Experiment Code
	public static void main(String[] args) {
		cardDeckGenerator intl = new cardDeckGenerator();
		player player1 = new player(); 
		System.out.println(player1.playerDeck.toString());
		
		System.out.println(cardDeckGenerator.drawPile.toString());
		
		//Create deck
		player1.createDeckForPlayer();
		System.out.println("Player Deck is " + player1.playerDeck.toString());
		System.out.println(cardDeckGenerator.drawPile.toString());
		
		
		//Player got card taken
		System.out.println("Player 1 got card " + 9 + " removed!");
		player1.removeCard(9);
		System.out.println("Deck now is " + player1.playerDeck.toString());
		
		
		player player2 = new player(); 
		System.out.println(cardDeckGenerator.drawPile.toString());
		
		player2.createDeckForPlayer();
		System.out.println("Player Deck2 is " + player2.playerDeck.toString());
		System.out.println(cardDeckGenerator.drawPile.toString());
		
		System.out.println("Player threw a card from deck ");
		player2.updateDeck();
		System.out.println(cardDeckGenerator.drawPile.toString());
		System.out.println("Player Deck2 is " + player2.playerDeck.toString());
		
		
	}
}
