package general;

import java.util.*;

public abstract class Game {
	
	//------------------------
	// MEMBER VARIABLES
	//------------------------

	//Game Attributes
	private String name;

	//Game Associations
	private Board board;
	private List<Player> players;
	private List<Colour> colourList;
	
	public Game(String aName)
	{
		name = aName;
		players = new ArrayList<Player>();
		colourList = new ArrayList<Colour>();
	}
	
	// ABSTRACT METHODS
	public abstract void setupBoard();
	public abstract boolean addPlayer(Player aPlayer);
	public abstract int maximumNumberOfPlayers();
	
	
	//CLASS METHODS
	public int numberOfPlayers()
	  {
	    int number = players.size();
	    return number;
	  }
	
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
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public List<Colour> getColourList(){
		return colourList;
	}
	
	
	public void setBoard(Board board){
		this.board = board;
	}
	
	public boolean addToColourList(Colour c){
		
		boolean wasAdded = false;
		if (colourListContains(c)) { return false; }
		if (numberOfPlayers() >= maximumNumberOfPlayers())
		{
			return wasAdded;
		}

		colourList.add(c);
		wasAdded = true;
		
		return wasAdded;
	}
	
	
	public boolean playersContains(Player player){
		return players.contains(player);
	}
	
	public boolean colourListContains(Colour colour){
		return colourList.contains(colour);
	}
	
	

}
