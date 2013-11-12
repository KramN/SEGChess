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
		//do captured pieces remain in this list? **Yes**
		//should the index of a given piece match the index of it's square's index in the square list? **No**
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
	
	//GETTERS
	public Square getSquare(int row, int col){
		return squares.get(row*rows + col);
	}
	
	//could be useless and bad
	//restricts the board to be applicable only to rectangular grid games **Should it be (row*cols + col)?**
	public int getListIndex(int row, int col){
		return row*rows + col;
	}
	
	//SETTERS
	public void setPieceAt(int row, int col, SpecificPiece aPiece){
		getSquare(row, col).setPiece(aPiece);
		specificPieces.add(aPiece); 
	}
	
	
	//INSTANCE METHODS
	
	public String toString(){
		String result = "";
		
		for (int i = 0; i < rows; i++){
			result += System.getProperty("line.separator");
			for (int j = 0; j < cols; j++){
				result += " " + squares.get(i*cols + j); //Calls the Square at position (i, j)'s toString()
			}
		}
		return result;
	}
	
}

