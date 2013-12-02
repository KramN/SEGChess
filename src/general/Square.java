package general;

import java.io.Serializable;
/**
 * 
 * Represents the nodes that make up the board of the game.
 */
public class Square implements Serializable {
	
	private static final long serialVersionUID = 6855502952320772867L;

	//------------------------
	// MEMBER VARIABLES
	//------------------------
	//Square Attributes
	private boolean hasPiece;
	
	//Square Associations
	private SpecificPiece thePiece;
	
	//------------------------
	// CONSTRUCTOR
	//------------------------
	public Square(){
		hasPiece = false;
	}
	
	//GETTERS
	public boolean hasPiece(){
		return hasPiece;
	}
	
	//SETTERS
	public void setPiece(SpecificPiece piece){
		//handle the case when there is already a piece occupying the square
		thePiece = piece;
		hasPiece = true;
	}
	
	//INSTANCE METHODS
	
	/**
	 * Removes the piece from this square.
	 */
	public SpecificPiece removePiece(){
		//TODO: Check for no piece existing. Throw exception if no piece.
		SpecificPiece temp = thePiece;
		thePiece = null;
		hasPiece = false;
		return temp;
	}
	
	public String toString(){
		String result;
		if (hasPiece){
			result = thePiece.toString();
		} else {
			result = "*";
		}
		
		return result;
	}
}
