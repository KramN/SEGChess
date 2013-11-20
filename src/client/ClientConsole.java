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
	 * @param loginID The loginID the user wishes the use.
	 * @param host The host to connect to.
	 * @param port The port to connect on.
	 */
	public ClientConsole(String loginID, String host, int port) 
	{
		client = new GameClient(loginID, host, port, this);
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
	public void displayMessage(Object message){
		System.out.println(message);
	}

	//Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the Client UI.
	 *
	 * @param args[0] The login id to use
	 * @param args[1] The host to connect to.
	 */
	public static void main(String[] args) 
	{
		String loginID = "";
		String host = "";
		int port = 0;  //The port number

		try {
			loginID = args[0];
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Must provide a user name!");
			System.exit(0);
		}

		try
		{
			host = args[1];
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			host = "localhost";
		}

		try{
			port = Integer.parseInt(args[2]);
		}
		catch(ArrayIndexOutOfBoundsException e){
			port = DEFAULT_PORT;
		}

		ClientConsole chat = new ClientConsole(loginID, host, port);
		chat.accept();  //Wait for console data
	}

}
