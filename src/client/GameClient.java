package client;

import ocsf.client.AbstractClient;
import java.io.*;
import general.*;

public class GameClient extends AbstractClient {

	GameIF clientUI;
	private String loginID;

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
		QUIT, LOGOFF, SETHOST, SETPORT, LOGIN, GETHOST, GETPORT, HELP
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
				("Could not send message to server.  Terminating client.");
				quit();
			}
			return;
		}
		//Handles each valid command individually.
		switch (command) {
		case QUIT: quit(); break;
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
		case LOGIN:
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
			String line;
			try{
				BufferedReader in = new BufferedReader(new FileReader("docs/chelp.txt"));
				try{
					while ((line = in.readLine()) != null){
						clientUI.displayMessage(line);
					}
				} catch (IOException e){
					clientUI.displayMessage("Error reading help file");
				}
				try {in.close();} catch(IOException e){};
			} catch (FileNotFoundException e){
				clientUI.displayMessage("Help file not found");
			}
			break;
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




	@Override
	protected void handleMessageFromServer(Object msg) {
		if (!(msg instanceof String)){ //TODO Change this to displaying Boards once objects figured out.
			clientUI.displayMessage(msg.toString());
		}
		clientUI.displayMessage(msg);
	}

}
