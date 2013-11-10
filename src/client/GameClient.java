package client;

import ocsf.client.AbstractClient;
import java.io.*;

public class GameClient extends AbstractClient {
	
	  public GameClient(String host, int port) 
	    throws IOException 
	  {
	    super(host, port); //Call the superclass constructor
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
	  
	  
	  
	  
	  

	@Override
	protected void handleMessageFromServer(Object msg) {
		// TODO Auto-generated method stub

	}

}
