import java.util.*;

public class PlayerHand {
	private String playername;
	private ArrayList<Card> hand;
	private static int num = 1;


	/**
	 * Creates a new player hand
	 * @param name The player's name
	 */
	public PlayerHand(String name) {
		this.playername = name;
		hand = new ArrayList<Card>();
		num++;
	}

	public PlayerHand() {
		playername = "Player " + Integer.toString(num++);
		hand = new ArrayList<Card>();
	}

	/**
	 * Prints every card in the player's hand
	*/
	public void view() {
		selectionSort();
		System.out.println(playername + "\'s hand:");
		for (int n = 0; n < hand.size(); n++) {
			System.out.print(n + ": ");
			hand.get(n).dumpCard();
		}
	}

	public void view(boolean meh) {
		selectionSort();
		System.out.println(playername + "\'s hand:");
		for (int n = 0; n < hand.size(); n++) {
			hand.get(n).dumpCard();
		}
	}

	private int indexOfLargest(int size) {
		int indexSoFar = 0;
		for (int i = 1; i < size; i++) {
			if (hand.get(i).compareTo(hand.get(indexSoFar)) > 0) {
				indexSoFar = i;
			}
		}
		return indexSoFar;
	}

	private void selectionSort() {
		int n = hand.size();
		for (int last = n-1; last >= 1; last--) {
			int largest = indexOfLargest(last+1);
			Card temp = hand.get(largest);
			hand.set(largest, hand.get(last));
			hand.set(last, temp);
		}
	}

	/**
	 * Adds a card to the player's hand
	 * @param toadd The card to add
	 */
	public void draw(Card toadd) {
		hand.add(toadd);
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
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).compareTo(tofind) == 0) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Searches for the first occuring card with the given number
	 * @param number The number to find
	 * @return The index of the first occurance of that number, -1 if not found
	 */
	public int numSearch(int number) {
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getNumber() == number) {
				return i;
			}
		}
		return -1;
	}

	public int suitSearch(String suit) {
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getSuit().equals(suit)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Views the card at an index without removing it
	 * @param index The index of which to look for the card
	 * @return the card at the given index
	 */
	public Card viewCard(int index) throws InvalidCardException{
		if (index < 0 || index >= hand.size()) {
			throw new InvalidCardException("Invalid index");
		}
		return hand.get(index);
	}

	public Card playCard(int number, String suit) throws InvalidCardException {
		try {
			return playCard(cardSearch(number,suit));
		}
		catch (InvalidCardException ice) {
			throw new InvalidCardException("Card does not exist");
		}
	}
	public Card playCard(int number, int suit) throws InvalidCardException {
		try {
			return playCard(cardSearch(number,suit));
		}
		catch (InvalidCardException ice) {
			throw new InvalidCardException("Card does not exist");
		}
	}
	public Card playCard(Card toplay) throws InvalidCardException {
		try {
			return playCard(cardSearch(toplay));
		}
		catch (InvalidCardException ice) {
			throw new InvalidCardException("Card does not exist");
		}
	}
	/**
	 * Plays card at certain index and removes it from the hand
	 * @param index The index of the card
	 * @return the card played
	 */
	public Card playCard(int index) throws InvalidCardException {
		if (index < 0 || index >= hand.size()) {
			throw new InvalidCardException("Invalid index");
		}
		return hand.remove(index);
	}

	/**
	 * Returns the number of cards in the hand
	 * @return the number of cards in the hand
	*/
	public int ccount() {
		return hand.size();
	}

	/**
	 * Returns the player's name
	 * @return the playername
	*/
	public String getName() {
		return this.playername;
	}

	/**
	 * Used for internal testing purposes
	 * @param args Not used
	*/
	public static void main(String[] args) {
		DeckOfCards deck = new DeckOfCards();
		deck.shuffle();
		PlayerHand test = new PlayerHand();
		for (int i = 0; i < 5; i++) {
			test.draw(deck.deal());
		}
		test.view();
		System.out.println(test.playCard(0));
		PlayerHand p2 = new PlayerHand();
		for (int i = 0; i < 5; i++) {
			p2.draw(deck.deal());
		}
		p2.view();
	}
}