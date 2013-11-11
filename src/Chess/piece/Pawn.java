package chess.piece;

import general.*;

public class Pawn extends SpecificPiece {

	// CONSTRUCTORS
	public Pawn(Square square){
		super(square);
	}
	
	// INSTANCE METHODS
	public String toString(){
		return "P";
	}
}
