NOTES:

FOR DECEMBER 2ND:

Revise assignment 5 document.
	Discuss Extra Classes like MOVE/COLOUR/SQUARE
	Include comments on how to start/run system.
	Add comment about how associations are used for later expansion
		Player -- SpecificPiece
	Class square could be eliminated but may also be useful for other game variants where extending Square could be useful for storing properties.

Comment code.
Implement Getters/Setters.
Attempt to pass objects from server to client.
Go through TODO lines.


IMPROVE SYSTEM:
URGENT: Display board method currents sends string representation of board.
URGENT: Handle promotion/en pessant/castling.
#start currently restarts game if already started.
Split out UI from all the chess classes as much as possible.

Move all #help commands to server side.
Separate #help commands for GameServer and Server.
Split Server/Client commands into separate methods.

If currently in a game, ask before starting a new game. Also lower the number of players in the old game.

See about lower player count in other circumstances.
Currently the player who sets up the game is white.
Can currently move "##?##???????????" with valid move.
