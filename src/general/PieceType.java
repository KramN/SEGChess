package general;

import java.io.Serializable;
import java.util.List;

public abstract class PieceType implements Serializable {

	private static final long serialVersionUID = -2509557331254011663L;
	
	protected Board board;
	
	public PieceType(Board board){
		this.board = board;
	}
	
	public abstract void initialize(List<Colour> colourList);

}
