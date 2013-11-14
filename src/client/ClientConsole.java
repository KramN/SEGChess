package client;
import general.*;
import java.io.*;

public class ClientConsole implements GameIF {
	
	//Class variables *************************************************

	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;
	
	//Instance variables **********************************************

	/**
	 * The instance of the client that created this ConsoleChat.
	 */
	GameClient client;
	
	//Constructors ****************************************************

	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host The host to connect to.
	 * @param port The port to connect on.
	 */
	public ClientConsole(String host, int port) 
	{
		try 
		{
			client = new GameClient(host, port, this);
		} 
		catch(IOException exception) 
		{
			System.out.println("Error: Can't setup connection!"
					+ " Terminating client.");
			System.exit(1);
		}
	}
	
	  //Instance methods ************************************************
	  
	/**
	 * This method waits for input from the console.  Once it is 
	 * received, it sends it to the client's message handler.
	 */
	public void accept() 
	{
		try
		{
			BufferedReader fromConsole = 
				new BufferedReader(new InputStreamReader(System.in));
			String message;

			while (true) 
			{
				message = fromConsole.readLine();
				client.handleMessageFromClientUI(message);
			}
		} 
		catch (Exception ex) 
		{
			System.out.println
			("Unexpected error while reading from console!");
		}
	}

	@Override
	public void displayBoard(Board board) {
		System.out.println(board.toString());
		// TODO Auto-generated method stub
	}
	
	@Override
	public void displayMessage(String message){
		System.out.println(message);
	}

	//Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the Client UI.
	 *
	 * @param args[0] The host to connect to.
	 */
	public static void main(String[] args) 
	{
		String host = "";
		int port = 0;  //The port number

		try
		{
			host = args[0];
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			host = "localhost";
		}
		ClientConsole chat= new ClientConsole(host, DEFAULT_PORT);
		chat.accept();  //Wait for console data
	}

}
