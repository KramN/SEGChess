package general;

import java.io.Serializable;

public class Move implements Serializable {

	private static final long serialVersionUID = -4921105678876999666L;
	
	protected String move;
	protected Game game;

	public Move(String move) {
		this.move = move;
	}
	
	public Move(String move, Game game){
		this.move = move;
		this.game = game;
	}
	
	public String getMove(){
			return move;
	}
	
	public void setMove(String move){
		this.move = move;
	}
	
	
	
	
	
	

}
