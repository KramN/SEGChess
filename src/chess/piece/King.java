package chess.piece;

import java.util.List;

import general.*;

public class King extends PieceType {

	private static final long serialVersionUID = -5463338697677948876L;

	//CONSTRUCTOR
	public King(Board board){
		super(board);
	}
	
	// INSTANCE METHODS
	public void initialize(List<Colour> colourList){
		board.pieceInit(0, 4, this, colourList.get(0));
		board.pieceInit(7, 4, this, colourList.get(1));
	}
	
	public String toString(){
		return "K";
	}
}
