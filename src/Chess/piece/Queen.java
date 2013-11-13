package chess.piece;

import general.*;

public class Queen extends PieceType {
	//CONSTRUCTOR
	public Queen(Board board){
		super(board);
	}

	// INSTANCE METHODS
	public void initialize(){
		
		board.pieceInit(0, 3, this);
		board.pieceInit(7, 3, this);

	}
	
	public String toString(){
		return "Q";
		}
	
	
}
