package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class Server extends AbstractServer {

	protected ServerConsole console; // UI for console interaction.

	final public static int DEFAULT_PORT = 5555;
	final public static String newline = System.getProperty("line.separator");

	//CONSTRUCTORS
	public Server(int port){
		super(port);
		console = new ServerConsole(this);
		
		init(); //hook

		try 
		{
			listen(); //Start listening for connections
		} 
		catch (Exception ex) 
		{
			System.out.println("ERROR - Could not listen for clients!");
		}

		console.accept();


	}

	/**
	 * Constructs an instance of the echo server. 
	 * The second constructor is needed for the ServerConsole to call.
	 *
	 * @param port The port number to connect on.
	 * @param console The UI for the user interactions
	 */
	public Server(int port, ServerConsole console) 
	{
		super(port);
		// Associates the EchoServer with a console UI.
		this.console = console;
	}
	
	//Hook method.
	protected void init(){}

	//////////////// HOOK METHODS ////////////////////

	/**
	 * Hook method called each time a new client connection is
	 * accepted.
	 * @param client the connection connected to the client.
	 */
	protected void clientConnected(ConnectionToClient client) {
		console.display(client + " has connected.");
	}

	/**
	 * Hook method called each time a client disconnects.
	 *
	 * @param client the connection with the client.
	 */
	synchronized protected void clientDisconnected(
			ConnectionToClient client) {
		console.display(client.getInfo("loginID") + " has disconnected.");
		sendToAllClients(client.getInfo("loginID").toString() + " has left the chat.");
	}

	/**
	 * Hook method called each time an exception is thrown in a
	 * ConnectionToClient thread.
	 * 
	 * Attempts to disconnect the client which threw the exception.
	 *
	 * @param client the client that raised the exception.
	 * @param Throwable the exception thrown.
	 */
	synchronized protected void clientException(
			ConnectionToClient client, Throwable exception) {
		try {
			client.close();
		} // Ignore all exceptions when closing clients. 
		catch (IOException e) {}
	}

	/**
	 * Hook method called when the server stops accepting
	 * connections because an exception has been raised.
	 * The default implementation does nothing.
	 * This method may be overridden by subclasses.
	 *
	 * @param exception the exception raised.
	 */
	protected void listeningException(Throwable exception) {}

	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server starts listening for connections.
	 */
	protected void serverStarted()
	{
		console.display
		("Server listening for connections on port " + getPort() + ".");
	}

	/**
	 * This method overrides the one in the superclass.  Called
	 * when the server stops listening for connections.
	 */
	protected void serverStopped()
	{
		console.display
		("Server has stopped listening for connections.");
	}

	/**
	 * Hook method called when the server is closed.
	 * The default implementation does nothing. This method may be
	 * overridden by subclasses. When the server is closed while still
	 * listening, serverStopped() will also be called.
	 */
	protected void serverClosed() {}


	///////////////// END HOOK METHODS ///////////////////////

	/**
	 * Handles a command sent from one client to the server.
	 * This method is called by a synchronized method so it is also
	 * implicitly synchronized.
	 *
	 * @param msg   the message sent.
	 * @param client the connection connected to the client that
	 *  sent the message.
	 */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println(client + " : " + msg);


		String message = "";
		try {
			message = (String)msg;
		} catch (ClassCastException e){
			console.display("Object of non-String type sent from " + client);
		}
		//Handles log in attempt from user.
		if(client.getInfo("loginID") == null){
			handleLogin(message, client);
			//Handles if the user sends "#login" to the server a second time.
		} else if (message.length() > 5 && message.substring(0,6).equals("#login")){
			try {
				client.sendToClient("Cannot log in again.");
			} catch (IOException e){
				console.display("User tried to login again. Error thrown.");
			}
		} else {
			//We noticed that if one client disconnects unexpectedly,
			//the server would send the exception to other clients;
			//these other clients would promptly disconnect.

			//Updated from System.out.println to console.display to handle other UIs
			console.display("Message received: " + msg + " from " + client.getInfo("loginID") + "@" + client);
			if (message.length() > 0 && message.substring(0,1).equals("#")){
				handleCommandFromUser(message, client);
			} else {
				this.sendToAllClients(client.getInfo("loginID").toString() + "> " + msg);
			}
		}
	}
	
	/**
	 * Private method for handling the actual log in process for users.
	 * 
	 * @param message First message passed to server from client
	 * @param client The connection from which the message originated.
	 */
	private void handleLogin(String message, ConnectionToClient client){
		//Handles the case where the user's first message was not a #login command.
		if(!(message.substring(0,6).equals("#login"))){
			try {
				client.sendToClient("Must log in with #login <username>.");
				client.close();
			} catch (IOException e){
				console.display("User did not log in properly. Error thrown.");
			}
			//Handles the cases where user logs in properly.
		} else {
			String argument = message.substring(message.indexOf(' ') + 1);
			client.setInfo("loginID", argument);
			sendToAllClients(client.getInfo("loginID") + " has joined the chat.");
			try {
				client.sendToClient("Type #help for help.");
			} catch (IOException e){
				console.display("Could not help new user.");
			}
		}
	}
	
	// No Commands set up by default. Hook method.
	protected void handleCommandFromUser(String message, ConnectionToClient client){}

	/**
	 * This method handles any messages received from the console.
	 *
	 * @param msg The message received from the console.
	 */
	public void handleMessageFromServerConsole(String message){
		//Checking for user command and handling appropriately.
		if (message.length() != 0 && message.substring(0,1).equals("#")){
			handleCommand(message);
		} else {
			console.display("SERVER MSG> " + message);
			this.sendToAllClients("SERVER MSG> " + message);
		}
	}

	/**
	 * List of all possible commands from console.
	 */
	private enum Command{
		QUIT, STOP, CLOSE, SETPORT, START, GETPORT, HELP
	}

	/**
	 * Method for handling all possible commands from the user
	 * 
	 * @param message The message from the UI.
	 */
	private void handleCommand(String message){
		String argument;
		Command command = null;
		//Checking to see if command from user has any arguments.
		//Checks for a space to determine if an argument is present.
		if (message.indexOf(' ') == -1){
			argument = message.substring(1);
		} else {
			argument = message.substring(1, message.indexOf(' '));
		}
		//Handling cases where user inputs invalid command.
		try {
			command = Command.valueOf(argument.toUpperCase());
		} catch (IllegalArgumentException e){
			console.display("Bad Command. Try again.");
			return;
		}
		//Handles each valid command individually.
		//TODO Setup individual methods for each command.
		switch (command) {
		case QUIT: 
			try {
				close();
			} catch (IOException e){
				console.display("Could not close server.");
			}
			System.exit(0);
			break;
		case STOP: 
			stopListening();
			sendToAllClients("WARNING - The server has stopped listening to connections");
			break;
		case CLOSE: 
			try {
				sendToAllClients("SERVER SHUTTING DOWN. DISCONNECTING!");
				close();
			} catch (IOException e){
				console.display("Could not close server.");
			}
			break;
		case SETPORT:
			if (!isListening() && getNumberOfClients() == 0){
				try {
					int port = Integer.parseInt(message.substring(message.indexOf(' ')+1));
					setPort(port);
					console.display("Port set to: " + port);
				} catch (NumberFormatException e){
					console.display("Port must be a number.");
				}
			} else {
				console.display("Must close server before setting a new port!");
			}
			break;
		case START:
			if (!isListening()){
				try {
					listen();
				} catch (IOException e){
					console.display("ERROR - Could not listen for clients!");
				}
			} else {
				console.display("Must not be listening to start listening!");
			}
			break;
		case GETPORT: console.display("Port: " + getPort()); break;
		case HELP:
			String line;
			try{
				BufferedReader in = new BufferedReader(new FileReader("docs/shelp.txt"));
				try{
					while ((line = in.readLine()) != null){
						console.display(line);
					}
				} catch (IOException e){
					console.display("Error reading help file");
				}
				try {in.close();} catch(IOException e){};
			} catch (FileNotFoundException e){
				console.display("Help file not found");
			}
			break;
		}
	}

	
// Old main method. Run GameServer instead.
//	public static void main(String[] args) 
//	{
//		int port = 0; //Port to listen on
//
//		try
//		{
//			port = Integer.parseInt(args[0]); //Get port from command line
//		}
//		catch(Throwable t)
//		{
//			port = DEFAULT_PORT; //Set port to 5555
//		}
//
//		Server server = new Server(port);
//
//		try 
//		{
//			server.listen(); //Start listening for connections
//		} 
//		catch (Exception ex) 
//		{
//			System.out.println("ERROR - Could not listen for clients!");
//		}
//	}
	
}
