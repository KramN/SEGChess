##STARTING THE SYSTEM:
	Make sure you�re in the /bin directory
	Start server first (java server.GameServer)
	Start client (java client.ClientConsole <id>)
	Enjoy playing chess! Experience is better with two clients. #HELP gets you started.


##IMPROVE SYSTEM FOR FURTHER VERSIONS:
*URGENT*: Display board method currents sends string representation of board.
	Attempt to pass objects from server to client.
URGENT: Handle promotion/en pessant/castling.
#start currently restarts game if already started.
Split out UI from all the chess classes as much as possible

If currently in a game, ask before starting a new game. Also lower the number of players in the old game.

Move all #help commands to server side.
Separate #help commands for GameServer and Server.
Split Server/Client commands into separate methods.

See about lowering player count in other circumstances.
Currently the player who sets up the game is white.
	Also, currently colour has no impact on game.
Can currently move "##?##???????????" with valid move.
