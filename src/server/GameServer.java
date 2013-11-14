package server;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class GameServer extends AbstractServer {
	
	final public static int DEFAULT_PORT = 5555;
	
	public GameServer(int port){
		super(port);
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
		// TODO Auto-generated method stub

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

}
