package server;

import ocsf.server.ConnectionToClient;

import java.io.*;
import java.util.*;

import general.*;
import chess.*;
import chess.piece.Pawn;

public class GameServer extends Server {

	List<Game> game;

	final public static String newline = System.getProperty("line.separator");

	public GameServer(int port){
		super(port);
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
	}
	
	//Initialize game list.
	@Override
	protected void init(){
		game = new ArrayList<Game>();
	}

	/**
	 * Hook method called each time a client disconnects.
	 * Updated to see if player currently in game and lower the # of players in that game.
	 *
	 * @param client the connection with the client.
	 */
	synchronized protected void clientDisconnected(
			ConnectionToClient client) {
		super.clientDisconnected(client);
		
		Game game;
		Player player;
		if ((game = getClientGame(client)) != null){
			player = getClientPlayer(client);
			game.removePlayer(player);
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

	// Enum for storing possible client commands.
	private enum ClientCommand{
		JOIN, MOVE, NEWCHESS, DISPLAYBOARD, RESET, START, TEST
	}
	
	private ClientCommand getClientCommand(String message){
		ClientCommand command;
		String argument;
		
		//Checking to see if command from user has any arguments.
		//Checks for a space to determine if an argument is present.
		if (message.indexOf(' ') == -1){
			argument = message.substring(1);
		} else {
			argument = message.substring(1, message.indexOf(' '));
		}
		
		command = ClientCommand.valueOf(argument.toUpperCase());
		
		return command;
	}

	/**
	 * Method for handling all possible commands from the user
	 * 
	 * @param message The message from the UI.
	 */
	@Override
	protected void handleCommandFromUser(String message, ConnectionToClient client){
		String parameter = "";
		ClientCommand command = null;

		try {
			command = getClientCommand(message);
		} catch (IllegalArgumentException e){
			try {
				client.sendToClient("Bad Command. Try again.");
			} catch (IOException e2){
				console.display("Could not send message to client: Bad command.");
			}
			return;
		}

		//Getting parameter if one exists.
		if (message.indexOf(' ') != -1){
			parameter = message.substring(message.indexOf(' ')+1);
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
		case START:
			startGame(client);
			break;
		case TEST:
			test(parameter, client);
			break;
		default:
			break;
		}
	}
	
	// Checks if the user has a running game.
	// Used when user sends commands that depend on started game.
	private boolean hasGame(Game game, ConnectionToClient client){
		return (game != null && game.isStarted());	
	}
	
	// Sends game state to all players of game.
	// TODO Update to send Object instead of String.
	private void displayAllBoard(Game game){
		messageGame(game, game.toString());
	}
	
	// Sends message to all players in a game.
	private void messageGame(Game game, Object msg){
		List<Player> players = game.getPlayers();
		for (Player p : players){
			p.sendToPlayer(msg);
		}
	}
	
	// Returns a reference to the client's current game.
	private Game getClientGame(ConnectionToClient client){
		return (Game) client.getInfo("Game");
	}
	
	// Returns a reference to the client's current player object.
	private Player getClientPlayer(ConnectionToClient client){
		return (Player) client.getInfo("Player");
	}
	
	// Starts the game if possible.
	private void startGame(ConnectionToClient client){
		Game theGame = getClientGame(client);
		if (theGame == null){ //Checks if the client has a game.
			try {
				client.sendToClient("Unable to start game. See #HELP CHESS for starting new game.");
			} catch (IOException e){
				console.display("Unable to send start game error to " + client);
			}
			return;
		}
		
		if (theGame.isReadyToStart()){ //Checks if the game is ready to start before starting.
			theGame.start();
			this.messageGame(theGame, theGame.getBoard());//sends all players a first state copy of the board
		} else {
			try {
				client.sendToClient("Unable to start game. Check if right amount of players.");
			} catch (IOException e){
				console.display("Unable to send start game error to " + client);
			}
		}
	}
	
	// Resets the client's current game.
	private void reset(ConnectionToClient client){
		Game theGame = getClientGame(client);
		if (hasGame(theGame, client)){
			theGame.start();
			displayAllBoard(theGame);
		} else {
			try {
				client.sendToClient("No current game to reset.");
			} catch (IOException e){
				console.display("Unable to send " + client + " reset error message.");
			}
		}
	}
	
	// Sends user the current state of the game.
	// TODO Update to send Object instead of Strings.
	private void displayBoard(ConnectionToClient client){
		Game theGame = getClientGame(client);
		if (hasGame(theGame, client)){
			try{
				Board theBoard = theGame.getBoard();
				client.sendToClient(theBoard);
			} catch (IOException e){
				console.display("Unable to send game state to " + client);
			}
		} else {
			try {
				client.sendToClient("No current game to display.");
			} catch (IOException e){
				console.display("Unable to send " + client + " displayboard error message.");
			}
		}
	}
	
	
	// Allows a player to join a game ready to start.
	// Starts the game if ready.
	private void joinGame(String name, ConnectionToClient client){

		int index = getIndexOfGame(name);

		if (index == -1){
			try{
				client.sendToClient("Game does not exist.");
				return;
			} catch (IOException e){
				console.display("Unable to send non-existant game name error to client");
			}
		}

		Game theGame = game.get(index);
		Player thePlayer = new Player(theGame, client);


		boolean added = theGame.addPlayer(thePlayer);
		if (added){
			client.setInfo("Game", theGame);
			client.setInfo("Player", thePlayer);
			messageGame(theGame, client.getInfo("loginID") + " has joined " + theGame.getName() + ".");
		} else {
			try {
				client.sendToClient("Unable to join game. Likely full.");
			} catch(IOException e){
				console.display("Unable to send join game error to client");
			}
		}

	}

	// Handles the move command. Current expected syntax : #move <xy> <xy>
	private void move(String move, ConnectionToClient client) throws IOException{
		Game game = (Game) client.getInfo("Game");
		if (hasGame(game, client)){
			Player player = getClientPlayer(client);
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
		} else {
			try {
				client.sendToClient("Cannot move pieces in non-existant game!");
			} catch (IOException e){
				console.display("Unable to send " + client + "move error.");
			}
		}
	}

	// Sets up a new chess game based on name given by user.
	private void newChessGame(String name, ConnectionToClient client){
		console.display("New game requested by " + client);
		if (name.equals("")){
			try{
				client.sendToClient("Invalid game name");
				return;
			} catch (IOException e){
				console.display("Unable to send game name error to client");
			}
		}

		//Checks to see if game name already taken.
		if (getIndexOfGame(name) != -1){
			try{
				client.sendToClient("Game name already taken.");
				return;
			} catch (IOException e){
				console.display("Unable to send duplicate game name error to client");
			}
		}

		Game theGame = new ChessGame(name);
		Player thePlayer = new Player(theGame, client);

		game.add(theGame);
		theGame.addPlayer(thePlayer);

		client.setInfo("Game", theGame);
		client.setInfo("Player", thePlayer);

		try {
			this.sendToAllClients(client.getInfo("loginID") + " has created new game: " + name);
			client.sendToClient("Game started successfully." + newline + 
			"Waiting for other players.");
		} catch (IOException e){
			console.display("Could not send message to client: Game start.");
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

	//Client-cmd to run test method below using #TEST.
	public void test(String parameter, ConnectionToClient client){
		//TODO Create object output stream and update all passing of game string stuff.
		Game debug = new ChessGame("Debug");
		TestObject test = new TestObject2("Meh");
		
		try{
			client.sendToClient(debug);
		} catch (IOException e){
			console.display("TESTING FAILED");
		}
	}
	
}
