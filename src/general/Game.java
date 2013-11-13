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
	
	public abstract void setupBoard();
	
	public void movePiece(int startX, int startY, int endX, int endY){
		board.movePiece(startX, startY, endX, endY);
	}
	
	public String toString(){
		String result;
		
		result = board.toString();
		
		return result;
	}

}
