import java.io.IOException;

import chess.*;
import general.*;


public class Testing {
	
	final public static String newline = System.getProperty("line.separator");
	public static final int DEFAULTPORT = 5555; 
	

	public static void main(String[] args) throws IOException {
		Game theGame = new ChessGame("Test");
		Player player1 = new Player(theGame, null);
		Player player2 = new Player(theGame, null);
		
		theGame.setupBoard();
		
		System.out.println("Testing setting up a board.");
		System.out.println(theGame);
		
		System.out.println(newline);
		
		System.out.println("Testing adding players and starting game.");
		theGame.addPlayer(player1);
		
		if (theGame.start()){
			System.out.println("FAILURE: Chess game should not start with 1 player");
		} else {
			System.out.println("SUCCESS: Chess game failed to start with 1 player");
		}
		
		theGame.addPlayer(player2);
		
		if (theGame.start()){
			System.out.println("SUCCESS: Game started.");
		} else {
			System.out.println("FAILURE: Game start failed.");
		}
		
		
		System.out.println(newline);
		System.out.println("Testing move: NOTE: Move should be invalid.");
		String testMove = "01 76";
		System.out.println("Move: " + testMove);
		theGame.move(testMove, null);
		System.out.println(theGame);
		//Example of the parsing done in ChessGame.move().
//		int startX = Integer.parseInt(testMove.substring(0,1));
//		int startY = Integer.parseInt(testMove.substring(1,2));
//		int endX = Integer.parseInt(testMove.substring(3,4));
//		int endY = Integer.parseInt(testMove.substring(4,5));
		
		System.out.println(newline);
		System.out.println("Test moving pieces: 14 34");
		theGame.movePiece(1, 4, 3, 4);
		System.out.println(theGame);
		
		System.out.println("Test moving pieces: 34 74" + newline +
				"NOTE: In future versions this test should fail. Invalid move.");
		theGame.movePiece(3, 4, 7, 4);
		System.out.println(theGame);
		
		System.out.println(newline);
		System.out.println("Test move non-existent piece: 24 10");
		try{
			theGame.movePiece(2, 4, 1, 0);
		} catch (NoPieceException e){
			System.out.println("SUCCESS: Test throws " + e);
		}
		
		System.out.println(newline);
		System.out.println("Test moving pieces outside board: 11 99");
		try{
			theGame.movePiece(1, 1, 9, 9);
		} catch (OutsideBoardException e){
			System.out.println("SUCCESS: Outside board exception thrown " + e);
		}
		System.out.println(newline);
		System.out.println("Testing negative move values");
		try {
			theGame.movePiece(-4, -1, -5, 0);
		} catch (Exception e){
			System.out.println("SUCCESS: Negative values caused exception thrown" + e);
		}
		
		System.out.println(newline);
		System.out.println("Printing current game state.");
		System.out.println(theGame);
		
		System.out.println(newline);
		System.out.println("Test reset game state");
		if (theGame.restart()){
			System.out.println("SUCCESS: Game restarted. CHECK GAME PRINT BELOW");
		} else {
			System.out.println("FAILURE: Game restart");
		}
		System.out.println(theGame);
		
		
		

		
		// TO BE HANDLED BY PASSING #TEST FROM CLIENT TO SERVER
		//Testing Object Passing. 
		//Thread thread1 = new Thread();
//
//		@SuppressWarnings("unused")
//		GameServer testServer = new GameServer(DEFAULTPORT);
//		System.out.println(newline + newline + "SERVER TESTING");
//		ClientConsole cConsole= new ClientConsole("Kasparov", "localhost", DEFAULTPORT);
//		GameClient testClient = new GameClient("Kasparov", "localhost", DEFAULTPORT, cConsole);
//		
//		
//		testClient.sendToServer("Hello World");
//		

		
		//testServer.listen();
		//testClient.openConnection();
		
		
		
		
		
		
		
		
		
	}
}
