public class Card {
	private int number;
	private String suit;

	protected final static String[] suits = {"spades", "hearts", "clubs", "diamonds"};


	Card(int number, String suit) throws InvalidCardException {
		suit = suit.toLowerCase();
		if (!isSuit(suit)) {
			throw new InvalidCardException("Invalid Suit");
		}
		if (number <=0 || number > 13) {
			throw new InvalidCardException("Invalid Number (must be between 1 and 13)");
		}
		this.number = number;
		this.suit = suit;
	}

	Card(int number, int intsuit) throws InvalidCardException {
		if (intsuit < 0 || intsuit > 3) {
			throw new InvalidCardException("Invalid Suit");
		}
		if (number <= 0 || number > 13) {
			throw new InvalidCardException("Invalid Number (must be between 1 and 13)");
		}
		String strsuit = suits[intsuit];
		this.number = number;
		this.suit = strsuit;
	}

	/**
	 * Checks if suit entered is valid.
	 * @param check The suit to check
	*/
	private boolean isSuit(String check) {
		for (String com: suits) {
			if (check.equals(com)) {
				return true;
			}
		}
		return false;
	}

	public int getNumber() {
		return this.number;
	}

	public String getSuit() {
		return this.suit;
	}

	public int suitNum() {
		for (int i = 0; i < suits.length; i++) {
			if (this.suit.equals(suits[i])) {
				return i;
			}
		}
		return -1;
	}

	public void dumpCard() {
		dumpCard(1);
	}

	public void dumpCard(int type) {
		switch(type) {
			case 0: 
				System.out.println("Number: " + this.number);
				System.out.println("Suit: " + this.suit);
				System.out.println();
				break;
			default:
				System.out.println(number + "\t" + suit);
		}

	}

	public String toString() {
		String s = number + " of " + suit;
		return s;
	}

	public int compareTo(Card other) {
		if (this.number != other.getNumber()) {
			return this.number - other.getNumber();
		}
		else {
			return other.suitNum() - this.suitNum();
		}
	}

	public int compareNum(Card other) {
		return this.number - other.getNumber();
	}

	public int compareSuit(Card other) {
		return other.suitNum() - this.suitNum();
	}

}