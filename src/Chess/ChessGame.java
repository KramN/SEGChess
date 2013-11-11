package Chess;

import general.Board;
import general.Game;

public class ChessGame extends Game {
	
	public ChessGame(String name){
		super(name);
	}
	
	public void setupBoard(){
		board = new Board(this, 8, 8);
	}
	

}
