package chess.piece;

import general.*;

public class Pawn extends PieceType {
	//CONSTRUCTOR
	public Pawn(Board board){
		super(board);
	}
	
	public void initialize(){
		
		for(int i = 0; i < 8; i++){
			//white
			board.pieceInit(1, i, this);
			//black
			board.pieceInit(6, i, this);
		}
		
	}
	
	// INSTANCE METHODS
	public String toString(){
		return "P";
	}
}
