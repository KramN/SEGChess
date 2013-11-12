package chess;


import general.*;
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
	//constructor calls will have to be updated with board and player
	private void initializePawns(){
		PieceType pawn = new Pawn();
		for(int i = 0; i < 8; i++){
			//white
			board.setPieceAt(1, i, new SpecificPiece(board.getSquare(1, i), pawn)); // breaking law of demeter? **I don't think so**
			//black
			board.setPieceAt(6, i, new SpecificPiece(board.getSquare(6, i), pawn));
		}
	}
	
	private void initializeRooks(){
		PieceType rook = new Rook();
		//white
		board.setPieceAt(0, 0, new SpecificPiece(board.getSquare(0, 0), rook));
		board.setPieceAt(0, 7, new SpecificPiece(board.getSquare(0, 7), rook));
		//black
		board.setPieceAt(7, 0, new SpecificPiece(board.getSquare(7, 0), rook));
		board.setPieceAt(7, 7, new SpecificPiece(board.getSquare(7, 7), rook));
	}
	
	private void initializeKnights(){
		PieceType knight = new Knight();
		//white
		board.setPieceAt(0, 1, new SpecificPiece(board.getSquare(0, 1), knight));
		board.setPieceAt(0, 6, new SpecificPiece(board.getSquare(0, 6), knight));
		//black
		board.setPieceAt(7, 1, new SpecificPiece(board.getSquare(7, 1), knight));
		board.setPieceAt(7, 6, new SpecificPiece(board.getSquare(7, 6), knight));
	}
	
	private void initializeBishops(){
		PieceType bishop = new Bishop();
		//white
		board.setPieceAt(0, 2, new SpecificPiece(board.getSquare(0, 2), bishop));
		board.setPieceAt(0, 5, new SpecificPiece(board.getSquare(0, 5), bishop));
		//black
		board.setPieceAt(7, 2, new SpecificPiece(board.getSquare(7, 2), bishop));
		board.setPieceAt(7, 5, new SpecificPiece(board.getSquare(7, 5), bishop));
	}
	
	private void initializeQueens(){
		PieceType queen = new Queen();
		//white
		board.setPieceAt(0, 3, new SpecificPiece(board.getSquare(0, 3), queen));
		//black
		board.setPieceAt(7, 3, new SpecificPiece(board.getSquare(7, 3), queen));
	}
	
	private void initializeKings(){
		PieceType king = new King();
		//white
		board.setPieceAt(0, 4, new SpecificPiece(board.getSquare(0, 4), king));
		//black
		board.setPieceAt(7, 4, new SpecificPiece(board.getSquare(7, 4), king));
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

