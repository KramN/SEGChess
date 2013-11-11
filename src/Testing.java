import chess.*;
import general.*;


public class Testing {

	public static void main(String[] args){
		Game theGame = new ChessGame("Test");
		theGame.setupBoard();
		
		Square testSquare = new Square(5, 5);
		
		Board testBoard = new Board(theGame, 12, 12);
		
		System.out.println(theGame);
		System.out.println("\n" + testSquare);
	}
}
