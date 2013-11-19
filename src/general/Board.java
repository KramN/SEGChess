package general;

import java.util.*;


public class Board {
	
	//------------------------
	// MEMBER VARIABLES
	//------------------------
	
	private int rows, cols;

	
	//Board Associations
	private Square[][] squares;
	private List<SpecificPiece> specificPieces;
	protected List<PieceType> pieceTypes;
	
		//do captured pieces remain in this list? **Yes**
		//should the index of a given piece match the index of it's square's index in the square list? **No**
	private Game game;
	
	//------------------------
	// CONSTRUCTOR
	//------------------------

	public Board(Game aGame, int rows, int cols)
	{
		squares = new Square[rows][cols];
		specificPieces = new ArrayList<SpecificPiece>();
		pieceTypes = new ArrayList<PieceType>();
		game = aGame;
		this.rows = rows;
		this.cols = cols;
		setupBoard(rows, cols);
	}
	
	private void setupBoard(int rows, int cols){
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				squares[i][j] = new Square();
			}
		}
	}
	
	//GETTERS
	public Square getSquare(int row, int col){
		return squares[row][col];
	}
	
	
	/**
	 * This is the method that does the bulk of setting up the board.
	 * 
	 * @param row
	 * @param col
	 * @param pieceType
	 */
	public void pieceInit(int row, int col, PieceType pieceType, Colour colour){
		Square theSquare = getSquare(row, col);
		SpecificPiece newPiece = new SpecificPiece(theSquare, pieceType, colour);

		theSquare.setPiece(newPiece);
		specificPieces.add(newPiece);
	}
	
	public void init(List<Colour> colourList){
		for (PieceType p : pieceTypes){
			p.initialize(colourList);
		}
	}
	
	public void addPieceType(PieceType pieceType){
		pieceTypes.add(pieceType);
	}
	
	
	//INSTANCE METHODS
	
	public void movePiece(int startX, int startY, int endX, int endY){
		// This method will need a lot of checks.
		// Will need to call either the game and/or the piecetype to see if valid move
		// Must handle check/error for if no piece exists
		// Check that positions are within board range
		
		Square startSquare = getSquare(startX, startY);
		Square endSquare = getSquare(endX, endY);
		
		endSquare.setPiece(startSquare.removePiece());		
	}
	
	
	public String toString(){
		String result = "";
		
		for (int i = rows - 1; i >= 0; i--){
			result += System.getProperty("line.separator");
			for (int j = 0; j < cols; j++){
				result += " " + getSquare(i, j); //Calls the Square at position (i, j)'s toString()
			}
		}
		return result;
	}
	
}

