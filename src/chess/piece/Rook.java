package chess.piece;

import java.util.List;

import general.*;

public class Rook extends PieceType { 

	private static final long serialVersionUID = -7521860270193363924L;

	//CONSTRUCTOR
	public Rook(Board board){
		super(board);
	}

	// INSTANCE METHODS
	public void initialize(List<Colour> colourList){
		
		board.pieceInit(0, 0, this, colourList.get(0));
		board.pieceInit(0, 7, this, colourList.get(0));
		board.pieceInit(7, 0, this, colourList.get(1));
		board.pieceInit(7, 7, this, colourList.get(1));
	}
	
	public String toString(){
		return "R";
	}
}
