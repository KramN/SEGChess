package general;

import ocsf.server.*;
import java.io.*;

public class Player implements Serializable {
	
	private static final long serialVersionUID = 6734234661870795872L;
	// INSTANCE VARIABLES

	private Colour colour;
	private Game game;
	private ConnectionToClient client;
	
	
	
	//CONSTRUCTORS
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

	public void sendToPlayer(Object msg){
		try{
			client.sendToClient(msg);
		} catch (IOException e){
			System.out.println("Unable to send message to player.");
		}
	}
	
	public void move(String move){
		
	}
}
