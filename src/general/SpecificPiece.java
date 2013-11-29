package general;

import java.io.Serializable;

public class SpecificPiece implements Serializable {
	
	// INSTANCE VARIABLES
	
	private static final long serialVersionUID = 1613590067524472168L;
	
	//SpecificPiece Associations
	private Square square;		// The square the piece is occupying
	private Player player;
	private Board board;
	private PieceType pieceType;
	private Colour colour;
	
	// CONSTRUCTORS
	public SpecificPiece(Square square, PieceType pieceType, Colour colour){
		this.square = square;
		this.pieceType = pieceType;
		this.colour = colour;
	}
	
	public String toString(){
		String result;
		
		result = pieceType.toString();
		
		//TODO Hack method. Works only for chess or black/white games.
		if (colour.getColourName().equals("Black")){
			result = result.toLowerCase();
		}
		
		
		return result;
	}
	
	//should methods that move the pieces about the board be in this class?
}
