import chess.*;
import general.*;
import lib.*;


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
		
		// TODO theGame.movePiece(2, 4, 1, 0);
		// Test moving pieces outside board
		try{
			theGame.movePiece(1, 1, 9, 9);
		} catch (OutsideBoardException e){
			System.out.println("Outside board success");
		}
		System.out.println(theGame);
		
		String testMove = "01 76";
		int startX = Integer.parseInt(testMove.substring(0,1));
		int startY = Integer.parseInt(testMove.substring(1,2));
		int endX = Integer.parseInt(testMove.substring(3,4));
		int endY = Integer.parseInt(testMove.substring(4,5));
		System.out.println(startX + " " + startY + " " + endX + " " + endY);

        // Testing ANSI support detection and colour output.
        String message = "Testing colour output.";
        String colorMessage = lib.Ansi.format(message, Ansi.Color.GREEN, Ansi.Color.WHITE);
        System.out.println(colorMessage);
	}
}
