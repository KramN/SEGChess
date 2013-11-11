package general;

public class Square {
	
	
	//------------------------
	// MEMBER VARIABLES
	//------------------------
	int xCoord, yCoord;
	boolean hasPiece;
	
	//Square Associations
	SpecificPiece thePiece;

	public Square(int xCoord, int yCoord){
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		hasPiece = false;
	}
	
	public void setPiece(SpecificPiece piece){
		//handle the case when there is already a piece occupying the square
		thePiece = piece;
		hasPiece = true;
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
