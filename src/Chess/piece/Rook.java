package chess.piece;

import general.*;

public class Rook extends PieceType { 
	//CONSTRUCTOR
	public Rook(Board board){
		super(board);
	}

	// INSTANCE METHODS
	public void initialize(){
		
		board.pieceInit(0, 0, this);
		board.pieceInit(0, 7, this);
		board.pieceInit(7, 0, this);
		board.pieceInit(7, 7, this);
	}
	
	public String toString(){
		return "R";
	}
}
