package chess.piece;

import java.util.List;

import general.*;

public class Bishop extends PieceType {
	//CONSTRUCTOR
	public Bishop(Board board){
		super(board);
	}
	// INSTANCE METHODS
	public void initialize(List<Colour> colourList){
		//Check if the list of colours is how we want it
		// size two, contains a white "colour" object and a black "colour" object
		
		board.pieceInit(0, 2, this, colourList.get(0));
		board.pieceInit(0, 5, this, colourList.get(0));
		board.pieceInit(7, 2, this, colourList.get(1));
		board.pieceInit(7, 5, this, colourList.get(1));
	}
	
	public String toString(){
		return "B";
	}
	
}
