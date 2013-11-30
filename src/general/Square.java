package general;

import java.io.Serializable;

public class Square implements Serializable {
	
	private static final long serialVersionUID = 6855502952320772867L;

	//------------------------
	// MEMBER VARIABLES
	//------------------------
	private boolean hasPiece;
	
	//Square Associations
	private SpecificPiece thePiece;

	public Square(){
		hasPiece = false;
	}
	
	public void setPiece(SpecificPiece piece){
		//handle the case when there is already a piece occupying the square
		thePiece = piece;
		hasPiece = true;
	}
	
	public SpecificPiece removePiece(){
		//Check for no piece existing. Throw exception if no piece.
		SpecificPiece temp = thePiece;
		thePiece = null;
		hasPiece = false;
		return temp;
	}
	
	public boolean hasPiece(){
		return hasPiece;
	}
	
	public String toString(){
		String result;
		if (hasPiece){
			result = thePiece.toString();
		} else {
			result = "*";
		}
		
		return result;
	}
}
