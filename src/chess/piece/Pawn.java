package chess.piece;

import java.util.List;

import general.*;

public class Pawn extends PieceType {
	//CONSTRUCTOR
	public Pawn(Board board){
		super(board);
	}
	
	public void initialize(List<Colour> colourList){
		
		for(int i = 0; i < 8; i++){
			//white
			board.pieceInit(1, i, this, colourList.get(0));
			//black
			board.pieceInit(6, i, this, colourList.get(1));
		}
		
	}
	
	// INSTANCE METHODS
	public String toString(){
		return "P";
	}
}
