package server;

import ocsf.server.ConnectionToClient;

import java.io.*;
import java.util.*;

import general.*;
import chess.*;

public class GameServer extends Server {

	List<Game> game;

	final public static String newline = System.getProperty("line.separator");

	public GameServer(int port){
		super(port);
		game = new ArrayList<Game>();
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
		game = new ArrayList<Game>();
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
	@Override
	protected void handleCommandFromUser(String message, ConnectionToClient client){
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
