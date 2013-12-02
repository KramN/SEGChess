package general;

import java.io.Serializable;
import java.util.List;

/**
 * Abstract class representing a category of piece that a game could have many of. 
 *
 */
public abstract class PieceType implements Serializable {

	private static final long serialVersionUID = -2509557331254011663L;
	
	//Piece type associations
	protected Board board;
	
	public PieceType(Board board){
		this.board = board;
	}
	
	//The piece type's algorithm run at the beginning of each game
	public abstract void initialize(List<Colour> colourList);

}
