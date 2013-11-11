package general;

import java.util.*;


public class Board {
	
	//------------------------
	// MEMBER VARIABLES
	//------------------------
	
	private int rows, cols;

	
	//Board Associations
	private List<Square> squares;
	private List<SpecificPiece> specificPieces;
	private Game game;
	
	//------------------------
	// CONSTRUCTOR
	//------------------------

	public Board(Game aGame, int rows, int cols)
	{
		squares = new ArrayList<Square>();
		specificPieces = new ArrayList<SpecificPiece>();
		game = aGame;
		this.rows = rows;
		this.cols = cols;
		setupBoard(rows, cols);
	}
	
	private void setupBoard(int rows, int cols){
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				squares.add(new Square(i, j));
			}
		}
	}
	
	public String toString(){
		String result = "";
		
		for (int i = 0; i < rows; i++){
			result += System.getProperty("line.separator");
			for (int j = 0; j < cols; j++){
				result += " " + squares.get(i*cols + j);
			}
		}
		return result;
	}
	
}
