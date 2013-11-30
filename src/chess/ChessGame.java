package chess;


import general.*;
import chess.piece.*;

public class ChessGame extends Game {

	private static final long serialVersionUID = 3158567182638032568L;

	public ChessGame(String name){
		super(name);
		addToColours(new Colour("White"));
		addToColours(new Colour("Black"));
	}
	
	public void setupBoard(){
		
		//Set up blank chess board
		setBoard(new Board(this, 8, 8));
		
		//populate board with chess pieces
		//will need to have set the players (white and black) at this point to properly construct piece objects
		initializeChessBoard();
		
		setIsStarted(true);
	}
	
	  public int maximumNumberOfPlayers()
	  {
	    return 2;
	  }
	  
	  //This is the minimum number of players to start a game.
	  //This is not the minimum number of associations.
	  public int minimumNumberOfPlayers(){
		  return 2; 
	  }
	
	private void initializeChessBoard(){
		Board board = getBoard();
		
		board.addPieceType(new Pawn(board));
		board.addPieceType(new Rook(board));
		board.addPieceType(new Knight(board));
		board.addPieceType(new Bishop(board));
		board.addPieceType(new Queen(board));
		board.addPieceType(new King(board));
		
		board.init(getColourList());
		

	}
	
	public boolean isReadyToStart(){
		if (numberOfPlayers() < minimumNumberOfPlayers()){
			return false;
		}
		
		return true;
	}
	
	public boolean start(){
		if (numberOfPlayers() < minimumNumberOfPlayers()){
			return false;
		}
		setupBoard();
		getPlayer(0).setColour(getColour(0));
		getPlayer(1).setColour(getColour(1));

		return true;
	}
	
	
	public boolean restart(){
		return start();
	}
	
	//TODO This method needs major overhaul
	public boolean move(String move, Player player) 
		throws OutsideBoardException, NoPieceException{
		boolean wasMoved = false;
		int startX, startY, endX, endY;
		Board board = getBoard();
		
		try{
			startX = Integer.parseInt(move.substring(0,1));
			startY = Integer.parseInt(move.substring(1,2));
			endX = Integer.parseInt(move.substring(3,4));
			endY = Integer.parseInt(move.substring(4,5));
		} catch (Exception e){
			return wasMoved;
		}
		
		wasMoved = board.movePiece(startX, startY, endX, endY);
		
		return wasMoved;
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

