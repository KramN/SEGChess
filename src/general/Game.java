package general;

import java.util.*;

public abstract class Game {
	
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
	
	public Game(String aName)
	{
		name = aName;
		players = new ArrayList<Player>();
		colours = new ArrayList<Colour>();
		isStarted = false;
	}
	
	// ABSTRACT METHODS
	public abstract void setupBoard();
	public abstract int maximumNumberOfPlayers();
	public abstract int minimumNumberOfPlayers();
	public abstract boolean start();
	public abstract boolean isReadyToStart();
	public abstract boolean move(String move, Player player);
	
	//GETTERS
	public boolean isStarted(){
		return isStarted;
	}
	//SETTERS
	public void setIsStarted(boolean started){
		isStarted = started;
	}
	
	//CLASS METHODS
	public void movePiece(int startX, int startY, int endX, int endY){
		board.movePiece(startX, startY, endX, endY);
	}
	
	public String toString(){
		String result;
		
		result = board.toString();
		
		return result;
	}

	public String getName(){
		return name;
	}
	
	public Board getBoard(){
		return board;
	}
	
	//PLAYER LIST METHODS
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public Player getPlayer(int index)
	{
		Player aPlayer = players.get(index);
		return aPlayer;
	}
	
	public int numberOfPlayers()
	  {
	    int number = players.size();
	    return number;
	  }
	
	public boolean playersContains(Player player){
		return players.contains(player);
	}
	
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
	
	public boolean removePlayer(Player aPlayer){
		boolean wasRemoved = false;
		if (!playersContains(aPlayer)) { return false; }
		
		players.remove(aPlayer);
		wasRemoved = true;
		
		return wasRemoved;
	}
	
	// COLOUR LIST METHODS
	
	public boolean coloursContains(Colour colour){
		return colours.contains(colour);
	}
	
	public List<Colour> getColourList(){
		return colours;
	}
	
	public Colour getColour(int index)
	{
		Colour aColour = colours.get(index);
		return aColour;
	}
	
	public int numberOfColours()
	  {
	    int number = colours.size();
	    return number;
	  }
	
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
	
	public void setBoard(Board board){
		this.board = board;
	}
	
	

	
	

}
