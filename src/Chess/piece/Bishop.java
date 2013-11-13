package chess.piece;

import general.*;

public class Bishop extends PieceType {
	//CONSTRUCTOR
	public Bishop(Board board){
		super(board);
	}
	// INSTANCE METHODS
	public void initialize(){
		board.pieceInit(0, 2, this);
		board.pieceInit(0, 5, this);
		board.pieceInit(7, 2, this);
		board.pieceInit(7, 5, this);
	}
	
	public String toString(){
		return "B";
	}
	
}
