package general;

import java.util.ArrayList;
import java.util.List;

import server.GameServer;

import chess.ChessGame;
import chess.piece.Pawn;

public class TestObject2 extends TestObject {

	private static final long serialVersionUID = -2127121783370730856L;
	private String name2;
	
	private List<Pawn> pawnList;
	Game game;
	Board board;
	
	public TestObject2(String name){
		super();
		name2 = name;
		pawnList = new ArrayList<Pawn>();
		pawnList.add(new Pawn(null));
		game = new ChessGame("Test");
		board = new Board(null, 8, 8);
		game.setBoard(board);
	}
	
	public String toString(){
		String result = "";
		result += name2.toString() + " " + super.toString();
		result += GameServer.newline + pawnList.get(0) + " Super: " + super.pawnList2.get(0);
		result += GameServer.newline + board;
		result += GameServer.newline + game;
		return result;
	}
	
	public static void main(String[] args){
		TestObject test = new TestObject2("Grr");
		System.out.println(test);
	}

}
