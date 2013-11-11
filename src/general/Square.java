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
