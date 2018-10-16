public class ChessPiece {
	ChessType type;
	boolean hasmoved;
	int sfm;
	boolean side;//false is black, true is white

	public ChessPiece(ChessType type, boolean side) {
		this.type = type;
		this.hasmoved = false;
		this.side = side;
		this.sfm = 0;
	}

	public void moved() {
		if (!this.hasmoved) {
			this.sfm = 0;
		}
		this.hasmoved = true;
	}

	public boolean sameSide(ChessPiece other) {
		return (this.side && other.side);
	}

	public void sfmc() {
		this.sfm++;
	}

	public boolean canMove(Position curr, Position tar, Board<ChessPiece> board) {
		boolean ret = false;
		int coldiff = curr.getCol() - tar.getCol();
		if (coldiff < 0) {
			coldiff *= -1;
		}
		int rowdiff = curr.getRow() - tar.getRow();
		if (coldiff < 0) {
			rowdiff *= -1;
		}

		if (side) { //white pieces (upwards)
			switch(type) {
				case PAWN:

					//too many columns apart
					if (coldiff > 1) {
						ret = false;
						break;
					}
					
					//normal capture or enpassant
					else if (coldiff == 1) {
						//must move forward when capturing
						if (rowdiff != 1) {
							ret = false;
							break;
						}
						//normal capture
						else if (board.occupied(tar)) {
							if (this.(sameSide(tar.itemAtPos(tar)))) {
								ret = true;
								break;
							}
							else {
								ret = false;
								break;
							}
						}
						
					}

					//en passant capture...
					else if () {

					}



					break;
	
				case ROOK:
					break;
	
				case KNIGHT:
					break;
	
				case BISHOP:
					break;
	
				case QUEEN:
					break;
	
				case KING:
					break;
	
				default:
			}
		}

		else { //black pieces (downwards)
			switch(type) {
				case PAWN:
					break;
	
				case ROOK:
					break;
	
				case KNIGHT:
					break;
	
				case BISHOP:
					break;
	
				case QUEEN:
					break;
	
				case KING:
					break;
	
				default:
			}
		}

		return ret;
	}

	public String toString() {
		switch(type) {
			case PAWN:
				return "P";
			case ROOK:
				return "R";
			case KNIGHT:
				return "N";
			case BISHOP:
				return "B";
			case QUEEN:
				return "Q";
			case KING:
				return "K";
			default:
				return null;
		}
	}

}

/*NOTE TO SELF
Store positions in the board
This class only checks if a move is possible
	Take into account other pieces getting in the way
In main game, use chess notation i.e. G5 to H3


*/