package general;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import chess.piece.Pawn;

public class TestObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String test;
	
	List<Pawn> pawnList2;
	
	public TestObject(){
		test = "TESTING";
		pawnList2 = new ArrayList<Pawn>();
		pawnList2.add(new Pawn(null));
	}
	
	public String toString(){
		return test;
	}

}
