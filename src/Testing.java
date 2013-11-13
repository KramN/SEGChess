import chess.*;
import general.*;


public class Testing {

	public static void main(String[] args){
		Game theGame = new ChessGame("Test");
		theGame.setupBoard();
		
		System.out.println(theGame);
		
		// Test moving pieces.
		theGame.movePiece(1, 4, 3, 4);
		System.out.println(theGame);
		
		// Test moving pieces.
		theGame.movePiece(3, 4, 7, 4);
		System.out.println(theGame);
	}
}
