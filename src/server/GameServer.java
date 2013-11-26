package server;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import java.io.*;
import java.util.*;

import general.*;
import chess.*;

public class GameServer extends AbstractServer {

	ServerConsole console; // UI for console interaction.

	List<Game> game;

	final public static int DEFAULT_PORT = 5555;
	final public static String newline = System.getProperty("line.separator");

	public GameServer(int port){
		super(port);
		console = new ServerConsole(this);
		game = new ArrayList<Game>();

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
	public GameServer(int port, ServerConsole console) 
	{
		super(port);
		// Associates the EchoServer with a console UI.
		this.console = console;
		game = new ArrayList<Game>();
	}


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
			System.out.println("Object of non-String type sent from " + client);
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
			if (message.substring(0,1).equals("#")){
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

	/**
	 * This method handles any messages received from the user.
	 *
	 * @param msg The message received from the user.
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
	 * List of all possible commands from user.
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

	public static void main(String[] args) 
	{
		int port = 0; //Port to listen on

		try
		{
			port = Integer.parseInt(args[0]); //Get port from command line
		}
		catch(Throwable t)
		{
			port = DEFAULT_PORT; //Set port to 5555
		}

		GameServer server = new GameServer(port);

		try 
		{
			server.listen(); //Start listening for connections
		} 
		catch (Exception ex) 
		{
			System.out.println("ERROR - Could not listen for clients!");
		}
	}

	/**
	 * Finds the index of a game based on the string name.
	 * 
	 * @param name Name of game to be found.
	 * @return index of game. Returns '-1' if game not found.
	 */
	private int getIndexOfGame(String name){
		int index = -1;

		for (int i = 0; i < game.size(); i++){
			//TODO Fix this line below. Too many sub-calls.
			if (game.get(i).getName().equals(name)){
				index = i;
			}
		}

		return index;
	}

	private enum ClientCommand{
		JOIN, MOVE, NEWCHESS, DISPLAYBOARD, RESET, TEST
	}

	/**
	 * Method for handling all possible commands from the user
	 * 
	 * @param message The message from the UI.
	 */
	private void handleCommandFromUser(String message, ConnectionToClient client){
		String argument;
		String parameter = "";
		ClientCommand command = null;

		//Checking to see if command from user has any arguments.
		//Checks for a space to determine if an argument is present.
		if (message.indexOf(' ') == -1){
			argument = message.substring(1);
		} else {
			argument = message.substring(1, message.indexOf(' '));
		}

		//Getting parameter if one exists.
		if (message.indexOf(' ') != -1){
			parameter = message.substring(message.indexOf(' ')+1);
		}

		//Handling cases where user inputs invalid command.
		try {
			command = ClientCommand.valueOf(argument.toUpperCase());
		} catch (IllegalArgumentException e){
			try {
				client.sendToClient("Bad Command. Try again.");
			} catch (IOException e2){
				System.out.println("Could not send message to client: Bad command.");
			}
			return;
		}

		switch (command) {
		case JOIN:
			joinGame(parameter, client);
			break;
		case MOVE:
			try {
				move(parameter, client);
			} catch (IOException e){
				console.display("Unable to send " + client + " invalid move message");
			}
			break;
		case NEWCHESS:
			newChessGame(parameter, client);
			break;
		case DISPLAYBOARD:
			displayBoard(client);
			break;
		case RESET:
			reset(client);
			break;
		case TEST:
			//TODO Create object output stream and update all passing of game string stuff.
			try{
				client.sendToClient(new ChessGame("Debug"));
			} catch (IOException e){
				System.out.println("TESTING FAILED");
			}
			break;
		}
	}
	
	/**
	 * Tests whether the client is assigned to a game.
	 * TODO Modify it to return only a boolean value and not send client message.
	 * 
	 * @param game
	 * @param client
	 * @return returns whether the user has a game or not.
	 */
	private boolean hasGame(Game game, ConnectionToClient client){
		if (game == null || !game.isStarted()){
			try{
				client.sendToClient("No current game.");
			} catch (IOException e){
				console.display("Unable to send null game error to " + client);
			}
			return false;
		} else {
			return true;
		}
			
	}
	
	private void reset(ConnectionToClient client){
		//TODO Catch issues where there is no game started.
		Game theGame = (Game) client.getInfo("Game");
		if (hasGame(theGame, client)){
			theGame.start();
			displayAllBoard(theGame);
		}
	}
	
	private void displayBoard(ConnectionToClient client){
		Game theGame = (Game) client.getInfo("Game");
		if (hasGame(theGame, client)){
			try{
				client.sendToClient(theGame.toString());
			} catch (IOException e){
				console.display("Unable to send game state to " + client);
			}
		}
	}

	private void joinGame(String name, ConnectionToClient client){

		int index = getIndexOfGame(name);

		if (index == -1){
			try{
				client.sendToClient("Game does not exist.");
				return;
			} catch (IOException e){
				System.out.println("Unable to send non-existant game name error to client");
			}
		}

		Game theGame = game.get(index);
		Player thePlayer = new Player(theGame, client);


		boolean added = theGame.addPlayer(thePlayer);
		if (added){
			client.setInfo("Game", theGame);
			client.setInfo("Player", thePlayer);
			messageGame(theGame, client.getInfo("loginID") + " has joined " + theGame.getName() + ".");
			if (theGame.isReadyToStart()){
				theGame.start();
				displayAllBoard(theGame);
			}
		} else {
			try {
				client.sendToClient("Unable to join game. Likely full.");
			} catch(IOException e){
				System.out.println("Unable to send join game error to client");
			}
		}

	}

	private void move(String move, ConnectionToClient client) throws IOException{
		Game game = (Game) client.getInfo("Game");
		if (hasGame(game, client)){
			Player player = (Player)client.getInfo("Player");
			boolean wasMoved = false;

			try {
				wasMoved = game.move(move, player);
				if (wasMoved){
					displayAllBoard(game); 
				} else {
					client.sendToClient("Invalid Move.");
				}
			} catch (OutsideBoardException e){
				client.sendToClient("Move was outside board. Try again.");
			} catch (NoPieceException e){
				client.sendToClient("No piece to move.");
			}
		}
	}


	private void newChessGame(String name, ConnectionToClient client){
		console.display("New game requested by " + client);
		if (name.equals("")){
			try{
				client.sendToClient("Invalid game name");
				return;
			} catch (IOException e){
				System.out.println("Unable to send game name error to client");
			}
		}

		//Checks to see if game name already taken.
		if (game.contains(name)){
			try{
				client.sendToClient("Game name already taken.");
				return;
			} catch (IOException e){
				System.out.println("Unable to send duplicate game name error to client");
			}
		}

		Game theGame = new ChessGame(name);
		Player thePlayer = new Player(theGame, client);

		game.add(theGame);
		theGame.addPlayer(thePlayer);

		client.setInfo("Game", theGame);
		client.setInfo("Player", thePlayer);

		try {
			client.sendToClient("Game started successfully." + newline + 
			"Waiting for other player.");
		} catch (IOException e){
			System.out.println("Could not send message to client: Game start.");
		}

	}
	
	private void displayAllBoard(Game game){
		messageGame(game, game.toString());
	}
	
	private void messageGame(Game game, Object msg){
		List<Player> players = game.getPlayers();
		for (Player p : players){
			p.sendToPlayer(msg);
		}
	}

}
