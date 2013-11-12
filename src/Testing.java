import chess.*;
import general.*;


public class Testing {

	public static void main(String[] args){
		Game theGame = new ChessGame("Test");
		theGame.setupBoard();
		
		System.out.println(theGame);
	}
}
