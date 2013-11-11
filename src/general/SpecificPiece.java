package general;

public class SpecificPiece {
	
	// INSTANCE VARIABLES
	
	//SpecificPiece Associations
	private Square square;		// The square the piece is occupying
	private Player player;
	private Board board;
	
	// CONSTRUCTORS
	public SpecificPiece(Square square){
		this.square = square;
	}
	
	//should methods that move the pieces about the board be in this class?
}
