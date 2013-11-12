NOTES:

I think we should do Array of array to simplify the board structure... (or arraylist of arraylist)

I realized that all our Pawn, Rook, Knight ... classes should've been extending PieceType and not SpecificPiece.. I've attempted to implement this and alter all the changes necessary to realize this...

We may want to have an assocation from Game to PieceType to store all the PieceTypes somewhere..
