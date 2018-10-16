import java.util.*;

public class DeckOfCards {
	private ArrayList<Card> deck;

	private static final int MAX_CARDS = 52;
	private static final int HIGHEST_CARD = 13;
	private static final int NUM_SUITS = 4;


	/**
	 * Creates a new full deck of cards (without jokers)
	*/
	public DeckOfCards() {
		deck = new ArrayList<Card>();
		for (int i = 1, j = 0; i <= HIGHEST_CARD; i++) {
			for (int k = 0; k < NUM_SUITS; k++, j++) {
				deck.add(new Card(i, k));
			}
		}
	}

	//****************************************************************************************************************************************
	//****************************************************************************************************************************************
	//****************************************************************************************************************************************

	/**
	 * Randomly chooses a shuffle algorithm
	 */
	public void shuffle() {
		Random rnd = new Random();
		int types = 2; //Types of shuffles that exist
		int rand = rnd.nextInt();
		if (rand < 0) {
			rand *= -1;
		}
		rand = rand%(types);
		switch(rand) {
			case 0:
				primLoop();
				break;
			case 1:
				simpleLoop();
				break;
			default:
				primLoop();
		}
	}

	public void simpleShuffle() {
		ArrayList<Card> temp = new ArrayList<Card>();
		Random rnd = new Random();
		while (!deck.isEmpty()) {
			int c = deck.size() - 1;
			if (c < 0) {
				c *= -1;
			}
			if (c > 0) {
				int r = rnd.nextInt(c);	
				if (r  < 0) {
					r *= -1;
				}
				temp.add(deck.remove(r));
			}
			else {
				temp.add(deck.remove(0));
			}
		}
		deck = temp;
	}

	public void simpleLoop() {
		Random rnd = new Random();
		int rand = rnd.nextInt(100);
		if (rand < 0) {
			rand *= -1;
		}
		if (rand == 0) {
			rand++;
		}
		simpleLoop(rand);
	}

	/**
	 * Repeats the shuffle some amount of times.
	 * @param times The amount of times the shuffle is repeated
	 */
	public void simpleLoop(int times) {
		if (times < 0) {
			times *= -1;
		}
		for (int i = 0; i < times; i++) {
			simpleShuffle();
		}

		//Debugging
		//System.out.println("Shuffled " + times + " times.");
	}

	//Primative Shuffle
	public void primativeShuffle() {
		Random rnd = new Random();
		int rand = rnd.nextInt(100);
		if (rand < 0) {
			rand *= -1;
		}
		if (rand == 0) {
			rand++;
		}
		primativeShuffle(rand);
	}

	/**
	 * Shuffles the deck using a primative method
	 * @param fac The degree of randomness
	 */
	public void primativeShuffle(int fac) {
		if (fac <= 0) {
			return;
		}

		ArrayList<Stack<Card>> shf = new ArrayList<Stack<Card>>();
		for (int m = 0; m < fac; m++) {
			shf.add(new Stack<Card>());
		}

		while (!deck.isEmpty()) {
			Random rnd = new Random();
			int n = rnd.nextInt(fac);

			if (n < 0) {
				n = n * -1;
			}

			shf.get(n).push(deck.remove(0));
		}
		this.deck = new ArrayList<Card>();
		for (int k = 0; k < fac; k++) {
			while (!shf.get(k).isEmpty()) {
				deck.add(shf.get(k).pop());
			}
		}
	}

	public void primLoop() {
		Random rnd = new Random();
		int rand = rnd.nextInt(100);
		if (rand < 0) {
			rand *= -1;
		}
		if (rand == 0) {
			rand++;
		}
		primLoop(rand);
	}

	/**
	 * Repeats the shuffle some amount of times.
	 * @param times The amount of times the shuffle is repeated
	 */
	public void primLoop(int times) {
		if (times < 0) {
			times *= -1;
		}
		for (int i = 0; i < times; i++) {
			primativeShuffle();
		}

		//Debugging
		//System.out.println("Shuffled " + times + " times.");
	}

	//****************************************************************************************************************************************
	//****************************************************************************************************************************************
	//****************************************************************************************************************************************
	//Playing tools

	public Card deal() {
		return deal(0);
	}

	/**
	 * Removes the top or bottom card in the deck.
	 */
	public Card deal(int side) {
		if (side != 0) { //bottom
			return deck.remove(deck.size() - 1);
		} 
		return deck.remove(0); //top
	}

	public int cardSearch(int number, String suit) {
		return cardSearch(new Card(number,suit));
	}

	public int cardSearch(int number, int suit) {
		return cardSearch(new Card(number,suit));
	}

	/**
	 * Returns the index of a given card
	 * @param tofind The card to find
	 * @return the index of the card, or -1 if not found
	 */
	public int cardSearch(Card tofind) {
		for (int i = 0; i < deck.size(); i++) {
			if (deck.get(i).compareTo(tofind) == 0) {
				return i;
			}
		}
		return -1;
	}

	public void stuffTop(Card toplace) {
		stuff(toplace, 0);
	}
	public void stuffBot(Card toplace) {
		deck.add(toplace);
	}
	public void stuff(Card toplace) {
		Random rnd = new Random();
		if (deck.size() == 0) {
			stuff(toplace, 0);
			return;
		}
		int rand = rnd.nextInt(deck.size());
		stuff(toplace, rand);
	}
	public void stuff(Card toplace, int index) {
		deck.add(index, toplace);
	}

	public boolean isEmpty() {
		return deck.isEmpty();
	}

	//****************************************************************************************************************************************
	//****************************************************************************************************************************************
	//****************************************************************************************************************************************
	//Debugging tools

	public void dumpDeck() {
		dumpDeck(1);
	}

	/**
	 * Prints all contents of deck in order they appear.
	 * @param type The form in which output is displayed, shown in the dumpCard method in the Card class
	 */
	public void dumpDeck(int type) {
		if (type != 0) {
			System.out.println("Number\tSuit");
		}
		for (int m = 0; m < deck.size(); m++) {
			deck.get(m).dumpCard(type);
		}
	}

	/**
	 * Checks for any duplicates inside the deck
	 * @return true if duplicates exist, false otherwise
	 */
	public boolean checkDup() {
		for (int i = 0; i < deck.size(); i++) {
			for (int j = 0; j < deck.size(); j++) {
				if (i == j) {
					continue;
				}
				if (deck.get(i).compareTo(deck.get(j)) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks how many cards before the first null
	 * @return the number of cards before the first null
	 */
	public int checkNum() {
		int i = 0;
		while (i < deck.size()) {
			if (deck.get(i) == null) {
				break;
			}
			i++;
		}
		return i;
	}

	//****************************************************************************************************************************************
	//****************************************************************************************************************************************
	//****************************************************************************************************************************************
	/**
	 * Used for internal testing purposes
	 * @param args Not used
	*/
	public static void main (String[] args) {
		DeckOfCards test1 = new DeckOfCards();
		test1.dumpDeck();

		System.out.println("\n\n");
		test1.primativeShuffle();
		test1.dumpDeck(1);

		System.out.println("\n\n");
		test1.primativeShuffle();
		test1.dumpDeck(1);
		System.out.println(test1.checkDup() + " " + test1.checkNum());

		System.out.println("\n\n");
		test1.primLoop();
		test1.dumpDeck(1);
		System.out.println(test1.checkDup() + " " + test1.checkNum());
	}

}