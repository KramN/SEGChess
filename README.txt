NOTES:

Must handle displayBoard() method in ServerConsole.

Method for sending messages to clients in a game not ideal.

Colour Class? Probably.

Need to consider restart game. Idea: board.clearBoard() then board.init().

Currently the player who sets up the game is white.

Law of Demeter broken in getIndexOfGame in GameServer class.

Set instance variables in Game to private and generate methods for subclasses.

We may want to have an assocation from Game to PieceType to store all the PieceTypes somewhere..

Split Client commands into separate methods.


Low Priority:
Test the "return" line in the beginning of handleCommandFromUser.