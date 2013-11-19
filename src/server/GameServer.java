package server;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import java.io.*;
import java.util.*;
import general.*;
import chess.*;

public class GameServer extends AbstractServer {
	
	List<Game> game;
	
	final public static int DEFAULT_PORT = 5555;
	final public static String newline = System.getProperty("line.separator");
	
	public GameServer(int port){
		super(port);
		
		game = new ArrayList<Game>();
	}
	
	
	//////////////// HOOK METHODS ////////////////////

	  /**
	   * Hook method called each time a new client connection is
	   * accepted. The default implementation does nothing.
	   * @param client the connection connected to the client.
	   */
	  protected void clientConnected(ConnectionToClient client) {}

	  /**
	   * Hook method called each time a client disconnects.
	   * The default implementation does nothing. The method
	   * may be overridden by subclasses but should remains synchronized.
	   *
	   * @param client the connection with the client.
	   */
	  synchronized protected void clientDisconnected(
	    ConnectionToClient client) {}

	  /**
	   * Hook method called each time an exception is thrown in a
	   * ConnectionToClient thread.
	   * The method may be overridden by subclasses but should remains
	   * synchronized.
	   *
	   * @param client the client that raised the exception.
	   * @param Throwable the exception thrown.
	   */
	  synchronized protected void clientException(
	    ConnectionToClient client, Throwable exception) {}

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
	   * Hook method called when the server starts listening for
	   * connections.  The default implementation does nothing.
	   * The method may be overridden by subclasses.
	   */
	  protected void serverStarted() {
		  System.out.println("Listening for connections on port " + getPort());
	  }

	  /**
	   * Hook method called when the server stops accepting
	   * connections.  The default implementation
	   * does nothing. This method may be overridden by subclasses.
	   */
	  protected void serverStopped() {}

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
		  
		  if (message.substring(0,1).equals("#")){
			  handleCommandFromUser(message, client);
		  }



	  }
	  
	  
	//////////////// NEEDED COMMANDS /////////
	//move <xy> to <xy>
	//startgame
	//join game
	//restart
	
	private enum Command{
		JOIN, MOVE, NEWGAME
	}

	/**
	 * Method for handling all possible commands from the user
	 * 
	 * @param message The message from the UI.
	 */
	private void handleCommandFromUser(String message, ConnectionToClient client){
		String argument;
		String parameter = "";
		Command command = null;
		
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
			command = Command.valueOf(argument.toUpperCase());
		} catch (IllegalArgumentException e){
			try {
				client.sendToClient("Bad Command. Try again.");
			} catch (IOException e2){
				System.out.println("Could not send message to client: Bad command.");
			}
			
			//return; 
		}
		
		switch (command) {
		case JOIN:
			
		case MOVE:
			//handleMove(command);
			break;
		case NEWGAME:
			startNewGame(parameter, client);
			break;
			default:
				//no default.
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
	
	

	

	
	private void handleMove(){
		
	}
	
	private void startNewGame(String name, ConnectionToClient client){
		if (name.equals("")){
			try{
				client.sendToClient("Invalid game name");
				return;
			} catch (IOException e){
				System.out.println("Unable to send game name error to client");
			}
		}
		
		//Currently only capable of handling chess games.
		Game theGame = new ChessGame(name);
		Player thePlayer = new Player();
		game.add(theGame);
		
		client.setInfo("Game", theGame);
		client.setInfo("Player", thePlayer);
		
		try {
			client.sendToClient("Game started successfully." + newline + 
					"Waiting for other player.");
		} catch (IOException e){
			System.out.println("Could not send message to client: Game start.");
		}
		
	}

}
