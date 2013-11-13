package chess.piece;

import general.*;

public class Knight extends PieceType {
	//CONSTRUCTOR
	public Knight(Board board){
		super(board);
	}
	
	public void initialize(){
		board.pieceInit(0, 1, this);
		board.pieceInit(0, 6, this);
		board.pieceInit(7, 1, this);
		board.pieceInit(7, 6, this);
	}
	
	// INSTANCE METHODS
	public String toString(){
		return "N"; // "N" is standard for knight
	}
}
