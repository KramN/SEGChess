package chess.piece;

import java.util.List;

import general.*;

public class Knight extends PieceType {
	//CONSTRUCTOR
	public Knight(Board board){
		super(board);
	}
	
	public void initialize(List<Colour> colourList){
		board.pieceInit(0, 1, this, colourList.get(0));
		board.pieceInit(0, 6, this, colourList.get(0));
		board.pieceInit(7, 1, this, colourList.get(1));
		board.pieceInit(7, 6, this, colourList.get(1));
	}
	
	// INSTANCE METHODS
	public String toString(){
		return "N"; // "N" is standard for knight
	}
}
