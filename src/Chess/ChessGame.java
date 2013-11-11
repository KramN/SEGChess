package chess;


import general.Board;
import general.Game;
import chess.piece.*;

public class ChessGame extends Game {
	
	public ChessGame(String name){
		super(name);
	}
	
	public void setupBoard(){
		
		//Set up blank chess board
		board = new Board(this, 8, 8);
		
		//populate board with chess pieces
		//will need to have set the players (white and black) at this point to properly construct piece objects
		initializeChessBoard();
	}
	
	private void initializeChessBoard(){
		initializePawns();
		initializeRooks();
		initializeKnights();
		initializeBishops();
		initializeQueens();
		initializeKings();
	}
	
	//there's probably a better way of doing this...
	//constructor calls will have to updated with board and player
	private void initializePawns(){
		for(int i = 0; i < 8; i++){
			//white
			board.setPieceAt(1, i, new Pawn(board.getSquare(1, i))); // breaking law of demeter?
			//black
			board.setPieceAt(6, i, new Pawn(board.getSquare(6, i)));
		}
	}
	private void initializeRooks(){
		//white
		board.setPieceAt(0, 0, new Rook(board.getSquare(0, 0)));
		board.setPieceAt(0, 7, new Rook(board.getSquare(0, 7)));
		//black
		board.setPieceAt(7, 0, new Rook(board.getSquare(7, 0)));
		board.setPieceAt(7, 7, new Rook(board.getSquare(7, 7)));
	}
	private void initializeKnights(){
		//white
		board.setPieceAt(0, 1, new Knight(board.getSquare(0, 1)));
		board.setPieceAt(0, 6, new Knight(board.getSquare(0, 6)));
		//black
		board.setPieceAt(7, 1, new Knight(board.getSquare(7, 1)));
		board.setPieceAt(7, 6, new Knight(board.getSquare(7, 6)));
	}
	private void initializeBishops(){
		//white
		board.setPieceAt(0, 2, new Bishop(board.getSquare(0, 2)));
		board.setPieceAt(0, 5, new Bishop(board.getSquare(0, 5)));
		//black
		board.setPieceAt(7, 2, new Bishop(board.getSquare(7, 2)));
		board.setPieceAt(7, 5, new Bishop(board.getSquare(7, 5)));
	}
	private void initializeQueens(){
		//white
		board.setPieceAt(0, 3, new Queen(board.getSquare(0, 3)));
		//black
		board.setPieceAt(7, 3, new Queen(board.getSquare(7, 3)));
	}
	private void initializeKings(){
		//white
		board.setPieceAt(0, 4, new King(board.getSquare(0, 4)));
		//black
		board.setPieceAt(7, 4, new King(board.getSquare(7, 4)));
	}
		
}

/*
 *    0 1 2 3 4 5 6 7
 *    
 * 7  R H B Q K B H R
 * 6  P P P P P P P P
 * 5  * * * * * * * *
 * 4  * * * * * * * *
 * 3  * * * * * * * *
 * 2  * * * * * * * *
 * 1  P P P P P P P P
 * 0  R H B Q K B H R
 * 
 */

