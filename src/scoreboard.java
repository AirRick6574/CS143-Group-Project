/**
 * 
 */

/**
 * Class is meant to track pairs each player has, rank the players, return the players
 * in ranked order, and return the winner who has the most pairs.
 */
public class scoreboard {
	
	// use totalSetsCount from the player class as the variable
	
	// create method that takes totalSetsCount from each player and creates a queue.
	
	// what is the most efficient way to rearrange the queue/stack/or array list?
	
	// end result is to print rankings highest to lowest and return the highest as winner
	
	
	public static void scoreboardOutput(player[] players) {
		int[] setTotalOfEachPlayers = new int{6}; 
		for (int i = 0; i < players.length; i++) {
			setTotalOfEachPlayers[i] = players[i].getSize();
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
