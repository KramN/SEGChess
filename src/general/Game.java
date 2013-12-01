package general;

import java.io.Serializable;
import java.util.*;

/**
 * Abstract class that ties all game elements together. 
 *
 */
public abstract class Game implements Serializable {

	private static final long serialVersionUID = 1208917428440743530L;
	
	//------------------------
	// MEMBER VARIABLES
	//------------------------

	//Game Attributes
	private String name;
	private boolean isStarted;

	//Game Associations
	private Board board;
	private List<Player> players;
	private List<Colour> colours;
	
	//------------------------
	// CONSTRUCTOR
	//------------------------
	public Game(String aName)
	{
		name = aName;
		players = new ArrayList<Player>();
		colours = new ArrayList<Colour>();
		isStarted = false;
	}
	
	//GETTERS
	public boolean isStarted(){
		return isStarted;
	}
	
	public String getName(){
		return name;
	}
	
	public Board getBoard(){
		return board;
	}
	//SETTERS
	public void setIsStarted(boolean started){
		isStarted = started;
	}
	
	public void setBoard(Board board){
		this.board = board;
	}
	
	
	//CLASS METHODS
	/**
	 * Moves a piece from (startX, startY) to (endX, endY)
	 */
	public void movePiece(int startX, int startY, int endX, int endY){
		board.movePiece(startX, startY, endX, endY);
	}
	
	// ABSTRACT METHODS
	/**
	 * Sets up the board during initialization and resetting.
	 */
	public abstract void setupBoard();
	
	/**
	 * @return the maximum amount of player a given game can accommodate
	 */
	public abstract int maximumNumberOfPlayers();
	/**
	 * @return the minimum amount of player a given game can accommodate
	 */
	public abstract int minimumNumberOfPlayers();
	
	/**
	 * Starts the game.
	 * @return whether the game started successfully or not
	 */
	public abstract boolean start();
	/**
	 * restarts the game
	 * @return whether the game restarted successfully or not
	 */
	public abstract boolean restart();
	/**
	 * @return whether the game is ready to start or not
	 */
	public abstract boolean isReadyToStart();
	/**
	 * Interprets a move command from a given player
	 * @return whether the move was successful or not
	 */
	public abstract boolean move(String move, Player player);
	
	//PLAYER LIST METHODS
	public List<Player> getPlayers(){
		return players;
	}
	
	/**
	 * @return the player object at a given index in the list
	 */
	public Player getPlayer(int index)
	{
		Player aPlayer = players.get(index);
		return aPlayer;
	}
	
	/**
	 * @return the size of the player list
	 */
	public int numberOfPlayers()
	  {
	    int number = players.size();
	    return number;
	  }
	
	/**
	 * @return whether a given player object is in the game or not
	 */
	public boolean playersContains(Player player){
		return players.contains(player);
	}
	
	/**
	 * adds a player to the game
	 * @return	whether the player was added successfully or not
	 */
	public boolean addPlayer(Player aPlayer)
	{
		boolean wasAdded = false;
		if (playersContains(aPlayer)) { return false; }
		if (numberOfPlayers() >= maximumNumberOfPlayers())
		{
			return wasAdded;
		}

		players.add(aPlayer);
		wasAdded = true;
		
		return wasAdded;
	}
	
	/**
	 * removes a player from the game
	 * @return whether the player was removed successfully or not
	 */
	public boolean removePlayer(Player aPlayer){
		boolean wasRemoved = false;
		if (!playersContains(aPlayer)) { return false; }
		
		players.remove(aPlayer);
		wasRemoved = true;
		
		return wasRemoved;
	}
	
	// COLOUR LIST METHODS
	public List<Colour> getColourList(){
		return colours;
	}
	
	/**
	 * @return whether a given colour object is in the list or not
	 */
	public boolean coloursContains(Colour colour){
		return colours.contains(colour);
	}
	
	/**
	 * @return the colour object at a given index in the list
	 */
	public Colour getColour(int index)
	{
		Colour aColour = colours.get(index);
		return aColour;
	}
	
	/**
	 * @return the size of the colour list
	 */
	public int numberOfColours()
	  {
	    int number = colours.size();
	    return number;
	  }
	
	/**
	 * adds a colour to the list
	 * @return	whether the object was added successfully or not
	 */
	public boolean addToColours(Colour c){
		
		boolean wasAdded = false;
		if (coloursContains(c)) { return false; }
		if (numberOfPlayers() >= maximumNumberOfPlayers())
		{
			return wasAdded;
		}

		colours.add(c);
		wasAdded = true;
		
		return wasAdded;
	}
	
	
	public String toString(){
		String result;
		
		if (getBoard() != null){
			result = board.toString();
		} else {
			result = "No board to display.";
		}
		
		return result;
	}

	
	

}
