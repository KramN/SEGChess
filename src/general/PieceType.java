package general;

public abstract class PieceType {

	protected Board board;
	
	public PieceType(Board board){
		this.board = board;
	}
	
	public abstract void initialize();

}
