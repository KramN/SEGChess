package server;

/**
 * 
 * This class constructs the UI for a user on the server to interact with the clients.  
 * It implements the chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ClientConsole
 * 
 * @author Mark Kasun (3806554)
 * @author Patrick Shortt (6036229)
 * @version October 2013
 */ 

import java.io.*;



/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 */


public class ServerConsole implements ChatIF {

	//Class variables *************************************************

	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;

	//Instance variables **********************************************

	/**
	 * The instance of the server that created this interface or is created by this interface.
	 */
	Server server;


	//Constructors ****************************************************

	/**
	 * Constructs an instance of the ServerConsole UI. 
	 * Used by the server to construct its own interface.
	 * 
	 * @param server The server that created the UI.
	 */
	public ServerConsole(Server server){
		this.server = server;
	}
	
	/**
	 * Constructs an instance of the ServerConsole UI.
	 *
	 * @param port The port to connect on.
	 */
	public ServerConsole(int port) 
	{
		server = new Server(port, this);

		try 
		{
			server.listen(); //Start listening for connections
		} 
		catch (Exception ex) 
		{
			System.out.println("ERROR - Could not listen for clients!");
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
				server.handleMessageFromServerConsole(message);
			}
		} 
		catch (Exception ex) 
		{
			System.out.println
			("Unexpected error while reading from console!");
		}
	}

	/**
	 * This method overrides the method in the ChatIF interface.  It
	 * displays a message onto the screen.
	 *
	 * @param message The string to be displayed.
	 */
	public void display(String message) 
	{
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
		int port = 0;  //The port number

		// **** Changed for E49 - MK, PS
		try{
			port = Integer.parseInt(args[0]);
		}
		catch(ArrayIndexOutOfBoundsException e){
			port = DEFAULT_PORT;
		}

		ServerConsole chat = new ServerConsole(port);

		chat.accept();  //Wait for console data
	}
}
