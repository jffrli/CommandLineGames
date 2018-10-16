public class Position {
	int row;
	int col;

	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}

	public boolean isEqual(Position other) {
		boolean rows = false;
		boolean cols = false;
		if (this.row == other.getRow()) {
			rows = true;
		}
		if (this.col == other.getCol()) {
			cols = true;
		}
		return (rows && cols);
	}

}