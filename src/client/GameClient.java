package client;

import ocsf.client.AbstractClient;
import java.io.*;
import general.*;

public class GameClient extends AbstractClient {
	
	//----------------------------
	// MEMBER VARIABLLES
	//----------------------------
	
	//ASSOCIATIONS
	private GameIF clientUI;
	private String loginID;
	
	final public static String newline = System.getProperty("line.separator");
	
	//----------------------------
	// CONSTRUCTORS
	//----------------------------
	public GameClient(String loginID, String host, int port, GameIF clientUI) 
	{
		super(host, port); //Call the superclass constructor
		this.loginID = loginID;
		this.clientUI = clientUI;

		try {
			openConnection();
		} catch (IOException e){
			clientUI.displayMessage("Unable to open connection. Awaiting command.");
		}
	}

	//////////////// HOOK METHODS ////////////////////

	/**
	 * Hook method called after the connection has been closed.
	 */
	protected void connectionClosed() {
		clientUI.displayMessage("Connection closed.");
	}

	/**
	 * Prints a message to the client screen when the server unexpectedly disconnects
	 * @param exception
	 *            the exception raised.
	 */
	protected void connectionException(Exception exception) {
		clientUI.displayMessage("Abnormal termination of connection. Waiting for command.");
	}

	/**
	 * Hook method called after a connection has been established. 
	 * Sends login information to server.
	 */
	protected void connectionEstablished() {
		try {
			sendToServer("#login " + loginID);
			clientUI.displayMessage("Successfully connected as " + loginID);
		} catch(IOException e) {
			clientUI.displayMessage
			("Could not connect to server. Awaiting command.");
		}
	}

	///////////////// END HOOK METHODS ///////////////////////

	/**
	 * This method handles all data coming from the UI            
	 *
	 * @param message The message from the UI.    
	 */
	public void handleMessageFromClientUI(String message)
	{
		//Checking for user command and handling appropriately.
		if (message.length() != 0 && message.substring(0,1).equals("#")){
			handleCommand(message);
		} else {
			try
			{
				sendToServer(message);
			}
			catch(IOException e)
			{
				clientUI.displayMessage
				("Could not send message to server.  Terminating client.");
				quit();
			}
		}
	}

	/**
	 * Enum constants for commands from the user.
	 */
	private enum Command{
		QUIT, LOGOUT, LOGOFF, SETHOST, SETPORT, LOGIN, LOGON, GETHOST, GETPORT, HELP, TEST
	}

	/**
	 * Method for handling all possible commands from the user
	 * 
	 * @param message The message from the UI.
	 */

	private void handleCommand(String message){
		String commandString;
		String argument = "";
		boolean hasArgument = false;
		Command command = null;
		//Checking to see if command from user has any arguments.
		//Checks for a space to determine if an argument is present.
		if (message.indexOf(' ') == -1){
			commandString = message.substring(1);
		} else {
			commandString = message.substring(1, message.indexOf(' '));
			argument = message.substring(message.indexOf(' ')+1);
			hasArgument = true;
		}
		//Handling cases where user inputs invalid command.
		try {
			command = Command.valueOf(commandString.toUpperCase());
		} catch (IllegalArgumentException e){
			try
			{
				sendToServer(message);
			}
			catch(IOException e2)
			{
				clientUI.displayMessage
				("Could not send message to server.");
			}
			return;
		}
		//Handles each valid command individually.
		switch (command) {
		case QUIT: quit(); break;
		case LOGOUT: //Runs same syntax as LOGOFF
		case LOGOFF: 
			try {
				closeConnection();
			} catch (IOException e) {}
			break;
		case SETHOST:
			if (!isConnected() && hasArgument){
				setHost(argument);
				clientUI.displayMessage("Host set to: " + getHost());
			} else if (!hasArgument){
				clientUI.displayMessage("Must supply a host name!");
			} else {
				clientUI.displayMessage("Must logoff before setting a new host!");
			}
			break;
		case SETPORT:
			if (!isConnected() && hasArgument){
				try {
					int port = Integer.parseInt(argument);
					setPort(port);
					clientUI.displayMessage("Port set to: " + getPort());
				} catch (NumberFormatException e){
					clientUI.displayMessage("Port must be a number.");
				}
			} else if (!hasArgument){
				clientUI.displayMessage("Must supply a port number!");
			} else {
				clientUI.displayMessage("Must logoff before setting a new port!");
			}
			break;
		case LOGIN: // Runs same syntax as LOGON.
		case LOGON:
			if (!isConnected()){
				//Updating loginID if a new one is provided.
				if (hasArgument){loginID = argument;}
				try {
					openConnection();
				} catch (IOException e){
					clientUI.displayMessage("Unable to connect to specified location. Please try again.");
				}
			} else {
				clientUI.displayMessage("Must logoff before trying to connect!");
			}
			break;
		case GETHOST: clientUI.displayMessage("Host: " + getHost()); break;
		case GETPORT: clientUI.displayMessage("Port: " + getPort()); break;
		case HELP:
			help(argument); break;
		case TEST:
			try {
				test();
			} catch (Exception e){
				clientUI.displayMessage("Error in testing: " + e);
			}
			 break;
		}
		
	}
	
	/**
	 * Sends the help document to the UI to display. Allows optional arguments.
	 * 
	 * @param argument help file to access.
	 */
	
	public void help(String argument){
		String line;
		String doc;
		argument = argument.toUpperCase();
		if (argument.equals("CHESS")){
			doc = "chess";
		} else {
			doc = "chelp";
		}
		try{
			BufferedReader in = new BufferedReader(new FileReader("docs/" + doc + ".txt"));
			try{
				while ((line = in.readLine()) != null){
					clientUI.displayMessage(line);
				}
			} catch (IOException e){
				clientUI.displayMessage("Error reading help file");
			}
			try {in.close();} catch(IOException e){};
		} catch (FileNotFoundException e){
			clientUI.displayMessage("Help file not found. Try moving /docs to /bin or to root directory.");
		}
	}

	/**
	 * This method terminates the client.
	 */
	public void quit()
	{
		try
		{
			closeConnection();
		}
		catch(IOException e) {}
		System.exit(0);
	}


	// Responsible for handling messages from server.
	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg instanceof Board){ //TODO Change this send board objects to UI.
			clientUI.displayBoard((Board) msg);
		} else {
			clientUI.displayMessage(msg.toString());
		}
	}
	
	//Test method to be called once connected to server using #TEST.
	private void test() throws IOException, InterruptedException {
		
		clientUI.displayMessage("TESTING #HELP.");
		handleCommand("#help");
		
		clientUI.displayMessage(newline);
		clientUI.displayMessage("TESTING #HELP CHESS.");
		handleCommand("#HELP CHESS");
		
		clientUI.displayMessage(newline);
		clientUI.displayMessage("SENDING VARIOUS TEST STRINGS.");
		sendToServer("Hello World!");
		sendToServer("");
		
		Thread.sleep(1000);
		clientUI.displayMessage(newline);
		clientUI.displayMessage("SENDING BAD COMMAND.");
		sendToServer("#");
		
		Thread.sleep(1000);
		clientUI.displayMessage(newline);
		clientUI.displayMessage("TESTING METHODS WITH NO GAME.");
		sendToServer("#displayboard");
		sendToServer("#join Test");
		sendToServer("#start");
		sendToServer("#reset");
		Thread.sleep(1000);
		clientUI.displayMessage("SHOULD BE ALL WARNING MESSAGES ABOVE.");
		
		clientUI.displayMessage(newline);
		clientUI.displayMessage("CREATING NEW GAME.");
		sendToServer("#newchess Test");
		
		Thread.sleep(1000);
		clientUI.displayMessage(newline);
		clientUI.displayMessage("ATTEMPTING TO CREATE EXISTING GAME.");
		sendToServer("#newchess Test");
		
		Thread.sleep(1000);
		clientUI.displayMessage(newline);
		clientUI.displayMessage("RUNNING METHODS WITH NOT ENOUGH PLAYERS");
		sendToServer("#displayboard");
		sendToServer("#start");
		sendToServer("#reset");		
		
		Thread.sleep(1000);
		clientUI.displayMessage(newline);
		clientUI.displayMessage("JOINING AND STARTING CREATED GAME");
		sendToServer("#join Test");
		sendToServer("#start");
		
		Thread.sleep(1000);
		clientUI.displayMessage(newline);
		clientUI.displayMessage("TESTING VARIOUS MOVES" + newline +
				"NOTE: BOARD WILL DISPLAY TWICE PER MOVE.");
		sendToServer("#move 14 34");
		sendToServer("#move 64 44");
		sendToServer("#move 06 25");
		sendToServer("#move 71 52");
		
		Thread.sleep(1000);
		clientUI.displayMessage(newline);
		clientUI.displayMessage("TESTING RESET BOARD");
		sendToServer("#reset");
		
		Thread.sleep(1000);
		clientUI.displayMessage(newline);
		clientUI.displayMessage("TESTING INVALID MOVES - CURRENTLY WORK.");
		clientUI.displayMessage("ADD MORE TEST CASES FOR EACH PIECE ONCE IMPLEMENTED");
		sendToServer("#move 00 77");
		sendToServer("#move 74 04");
		
		Thread.sleep(1000);
		clientUI.displayMessage("RESETTING BOARD");
		sendToServer("#reset");
		Thread.sleep(1000);
		clientUI.displayMessage(newline);
		clientUI.displayMessage("TESTING INVALID MOVES");
		sendToServer("#move 00 88");
		sendToServer("#move 20 77");
		sendToServer("#move 20 88");
		
		
		
		//sendToServer("#test");
	}

}
