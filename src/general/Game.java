package general;

import java.util.*;

public class Game {
	
	//------------------------
	// MEMBER VARIABLES
	//------------------------

	//Game Attributes
	private String name;

	//Game Associations
	protected Board board;
	private List<Player> players;
	
	public Game(String aName)
	{
		name = aName;
		players = new ArrayList<Player>();
	}
	

}
