NOTES:

FOR DECEMBER 2ND:

Implement test cases.
Revise assignment 5 document.
Comment code.
Implement Getters/Setters.
Attempt to pass objects from server to client.
Include comments on how to start/run system.
Add comment about how associations are used for later expansion
	Player -- SpecificPiece
	Class square could be eliminated but may also be useful for other game variants where extending Square could be useful for storing properties.

Add Move class to UML diagram?

ERROR: Move can disconnect (Fixed?)


IMPROVE SYSTEM:
URGENT: Display board method currents sends string representation of board.
URGENT: Handle promotion.

Move all #help commands to server side.
Separate #help commands for GameServer and Server.

Limit one game to user.

Lower player count on a game when someone leaves.
Currently the player who sets up the game is white.

Split Server/Client commands into separate methods.

Can currently move "##?##???????????" with valid move.

If currently in a game, ask before starting a new game. Also lower the number of players in the old game.

Split out UI from all the chess classes as much as possible.
