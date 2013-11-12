package general;

public class SpecificPiece {
	
	// INSTANCE VARIABLES
	
	//SpecificPiece Associations
	private Square square;		// The square the piece is occupying
	private Player player;
	private Board board;
	private PieceType pieceType;
	
	// CONSTRUCTORS
	public SpecificPiece(Square square, PieceType pieceType){
		this.square = square;
		this.pieceType = pieceType;
	}
	
	public String toString(){
		String result;
		
		result = pieceType.toString();
		
		return result;
	}
	
	//should methods that move the pieces about the board be in this class?
}
