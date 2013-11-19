package client;

import ocsf.client.AbstractClient;
import java.io.*;

public class GameClient extends AbstractClient {
	
	GameIF clientUI;

	public GameClient(String host, int port, GameIF clientUI) 
	throws IOException 
	{
		super(host, port); //Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
	}

	//////////////// HOOK METHODS ////////////////////
	/**
	 * Hook method called after the connection has been closed. The default
	 * implementation does nothing. The method may be overriden by subclasses to
	 * perform special processing such as cleaning up and terminating, or
	 * attempting to reconnect.
	 */
	protected void connectionClosed() {
	}

	/**
	 * Hook method called each time an exception is thrown by the client's
	 * thread that is waiting for messages from the server. The method may be
	 * overridden by subclasses.
	 * 
	 * @param exception
	 *            the exception raised.
	 */
	protected void connectionException(Exception exception) {
	}

	/**
	 * Hook method called after a connection has been established. The default
	 * implementation does nothing. It may be overridden by subclasses to do
	 * anything they wish.
	 */
	protected void connectionEstablished() {
	}

	///////////////// END HOOK METHODS ///////////////////////

	/**
	 * This method handles all data coming from the UI            
	 *
	 * @param message The message from the UI.    
	 */
	public void handleMessageFromClientUI(String message)
	{
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
		clientUI.displayMessage(msg);
	}

}
