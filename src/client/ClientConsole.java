package client;
import general.*;

public class ClientConsole implements GameIF {

	@Override
	public void displayBoard(Board board) {
		System.out.println(board.toString());
		// TODO Auto-generated method stub
	}

}
