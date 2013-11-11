package chess.piece;

import general.*;

public class Knight extends SpecificPiece {
	// CONSTRUCTORS
	public Knight(Square square){
		super(square);
	}

	// INSTANCE METHODS
	public String toString(){
		return "H"; // "H" for horse due to conflict with king
	}
}
