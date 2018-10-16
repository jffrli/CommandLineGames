public class Board<T> {
	public T[][] board;

	final static int CAP = 8;
	final static String[][] PositionNotation = {{"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8"},
												{"B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8"},
												{"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8"},
												{"D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8"},
												{"E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8"},
												{"F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8"},
												{"G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8"},
												{"H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8"}};
												//[column][row]

	public Board() {
		@SuppressWarnings("unchecked") T[][] array = (T[][])new Object[CAP][CAP];
		board = array;
	}

	public static Position noteToIndex(String note) {
		Position ret;
		for(int i = 0; i < CAP; i++) {
			for (int j = 0; j < CAP; j++) {
				if (note.equals(PositionNotation[j][i])) {
					ret = new Position(i, j);
					return ret;
				}
			}
		}
		return null;
	}

	public boolean occupied(String note) {
		Position ind = noteToIndex(note);
		return occupied(ind);
	}

	public boolean occupied(Position ind) {
		return occupied(ind.getRow(), ind.getCol());
	}

	public boolean occupied(int row, int col) {
		return (board[col][row] != null);
	}

	public String toString() {
		String ret = "";
		//ret = "-----------------------------------------------------------------------\n";
		ret = ret + "\t|A\t|B\t|C\t|D\t|E\t|F\t|G\t|H\n       ------------------------------------------------------------------\n";
		for (int i = CAP-1; i >= 0; i--) {
			ret = ret + "      " + (i+1) + "\t|";
			for (int j = 0; j < CAP; j++) {
				if (occupied(i,j)) {
					ret = ret + board[j][i] + "\t|";
				}
				else {
					ret = ret + "\t|";
				}
			}
			ret = ret + "\n       ------------------------------------------------------------------\n";
		}
		//ret = ret + "-----------------------------------------------------------------------\n";
		return ret;
	}

	public T itemAtPos(String note) {
		Position ind = noteToIndex(note);
		return itemAtPos(ind);
	}

	public T itemAtPos(Position ind) {
		return itemAtPos(ind.getRow(), ind.getCol());
	}

	public T itemAtPos (int row, int col) {
		return board[col][row];
	}

	public void place(String note, T item) {
		Position ind = noteToIndex(note);
		place(ind, item);
	}

	public void place(Position ind, T item) {
		place(ind.getRow(),ind.getCol(), item);
	}

	public void place(int row, int col, T item) {
		this.board[col][row] = item;
	}

	public static void main(String[] args) {
		Board<Integer> test = new Board<Integer>();
		
		test.place("G5", 999);
		System.out.println(test);

		for (int m = 0; m < 8; m++) {
			for (int n = 0; n < 8; n++) {
				test.place(m,n, (m+n));
			}
		}
		System.out.println(test);

		Board<String> stest = new Board<String>();

		stest.place("A2", "Hi");
		stest.place("A6", "Bye");
		stest.place("H7", "Hello");
		System.out.println(stest);
		System.out.println(stest.itemAtPos("A6"));
		System.out.println(stest.occupied("H7") + "\t" + stest.occupied("G5"));
		System.out.println(stest.itemAtPos("G5"));
	}

	

}