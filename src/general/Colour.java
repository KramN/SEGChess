package general;
import java.util.*;
/**
 * 
 * This class links a player to all of his or her pieces, and assigns those pieces a colour.
 * 
 */
public class Colour  {
	
	// INSTANCE VARIABLES
	
	private Player              player;
	
	private String              colourName;
	
	private List<SpecificPiece> pieceList;
	
	// CONSTRUCTORS
	public Colour(Player player, String colourName){
		
		this.player = player;
		this.colourName = colourName;
		pieceList = new ArrayList<SpecificPiece>();
	}
	
	// GETTERS
	// SETTERS
	public void setPiece(SpecificPiece piece){
		pieceList.add(piece);
	}
	// INSTANCE METHODS

}
