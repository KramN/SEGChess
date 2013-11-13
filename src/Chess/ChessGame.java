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
		board.addPieceType(new Pawn(board));
		board.addPieceType(new Rook(board));
		board.addPieceType(new Knight(board));
		board.addPieceType(new Bishop(board));
		board.addPieceType(new Queen(board));
		board.addPieceType(new King(board));
		
		board.init();
		

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

