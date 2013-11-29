import java.io.IOException;

import client.ClientConsole;
import client.GameClient;
import server.GameServer;
import chess.*;
import general.*;


public class Testing {
	
	final public static String newline = System.getProperty("line.separator");
	public static final int DEFAULTPORT = 5555; 
	

	public static void main(String[] args) throws IOException {
		Game theGame = new ChessGame("Test");
		theGame.setupBoard();
		
		System.out.println(theGame);
		
		// Test moving pieces.
		theGame.movePiece(1, 4, 3, 4);
		System.out.println(theGame);
		
		// Test moving pieces.
		theGame.movePiece(3, 4, 7, 4);
		System.out.println(theGame);
		
		//Test move non-existent piece
		try{
			theGame.movePiece(2, 4, 1, 0);
		} catch (NoPieceException e){
			System.out.println("Non-existant move piece test successful");
		}
		// Test moving pieces outside board
		try{
			theGame.movePiece(1, 1, 9, 9);
		} catch (OutsideBoardException e){
			System.out.println("Outside board success");
		}
		System.out.println(theGame);
		
		//Testing move parsing
		String testMove = "01 76";
		int startX = Integer.parseInt(testMove.substring(0,1));
		int startY = Integer.parseInt(testMove.substring(1,2));
		int endX = Integer.parseInt(testMove.substring(3,4));
		int endY = Integer.parseInt(testMove.substring(4,5));
		
		System.out.println("Parsed move: " + startX + " " + startY + " " + endX + " " + endY);
		

		//Testing Object Passing.
		//TODO WRITE MORE TEST CASES HERE.
		Thread thread1 = new Thread();

		@SuppressWarnings("unused")
		GameServer testServer = new GameServer(DEFAULTPORT);
		System.out.println(newline + newline + "SERVER TESTING");
		ClientConsole cConsole= new ClientConsole("Kasparov", "localhost", DEFAULTPORT);
		GameClient testClient = new GameClient("Kasparov", "localhost", DEFAULTPORT, cConsole);
		
		
		testClient.sendToServer("Hello World");
		
		

		
		//testServer.listen();
		//testClient.openConnection();
		
		
		
		
		
		
		
		
		
	}
}
