package general;

import java.util.List;

public abstract class PieceType {

	protected Board board;
	
	public PieceType(Board board){
		this.board = board;
	}
	
	public abstract void initialize(List<Colour> colourList);

}
