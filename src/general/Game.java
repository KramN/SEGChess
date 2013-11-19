package general;

import java.util.*;

public abstract class Game {
	
	//------------------------
	// MEMBER VARIABLES
	//------------------------

	//Game Attributes
	private String name;

	//Game Associations
	protected Board board;
	protected List<Player> players;
	
	public Game(String aName)
	{
		name = aName;
		players = new ArrayList<Player>();
	}
	
	// ABSTRACT METHODS
	public abstract void setupBoard();
	public abstract boolean addPlayer(Player aPlayer);
	
	
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
	

}
