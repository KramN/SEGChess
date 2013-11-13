package chess.piece;

import general.*;

public class King extends PieceType {

	//CONSTRUCTOR
	public King(Board board){
		super(board);
	}
	
	// INSTANCE METHODS
	public void initialize(){
		board.pieceInit(0, 4, this);
		board.pieceInit(7, 4, this);
	}
	
	public String toString(){
		return "K";
	}
}
