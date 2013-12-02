package general;

import ocsf.server.*;
import java.io.*;

public class Player implements Serializable {
	
	private static final long serialVersionUID = 6734234661870795872L;
	
	//------------------------
	// MEMBER VARIABLES
	//------------------------
	
	// Player Associations
	private Colour colour;
	private Game game;
	private ConnectionToClient client;
	
	
	//------------------------
	// CONSTRUCTOR
	//------------------------
	public Player(Game game, ConnectionToClient client){
		this.game = game;
		this.client = client;
	}
	
	// GETTERS
	public Game getGame(){
		return game;
	}
	// SETTERS
	public void setColour(Colour colour){
		this.colour = colour;
	}
	
	// INSTANCE METHODS
	
	/**
	 * Sends a message to the player
	 */
	public void sendToPlayer(Object msg){
		try{
			client.sendToClient(msg);
		} catch (IOException e){
			System.out.println("Unable to send message to player.");
		}
	}
	
}
