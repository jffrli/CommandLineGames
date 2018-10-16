import java.util.*;

public class CEplayer {
	private PlayerHand phand;
	private int id;
	private static int pc = 0;
	private String msg; //message for other players


	public CEplayer(String name) {
		phand = new PlayerHand(name);
		id = pc++;
		msg = "";	}

	public CEplayer() {
		phand = new PlayerHand();
		id = pc++;
		msg = "";
	}

	/**
	 * Returns the player ID of the player
	 * @return the player id.
	*/
	public int getID() {
		return id;
	}

	/**
	 * Views the contents of the player's hand
	*/
	public void view() {
		phand.view(true);
	}

	/**
	 * Checks if the player has any cards left
	 * @return true if the player has no cards, false otherwise
	 */
	public boolean isEmpty() {
		return (phand.ccount() == 0);
	}

	/**
	 * Returns the player's name as a string
	 * @return name + (ID)
	 */
	public String name() {
		return phand.getName() + " (" + this.getID() + ")";
	}

	public String toString() {
		return phand.getName();
	}

	/**
	 * Returns the number of cards in the hand
	 * @return the number of cards in the hand
	 */
	public int ccount() {
		return phand.ccount();
	}

	public String pdata() {
		return this.name() + " has " + this.ccount() + " card(s) left.";
	}	

	public String sdata() {
		return "You have " + this.ccount() + " card(s) left.";
	}

	public void resetMsg() {
		this.msg = "";
	}

	public void msgAdd(String toadd) {
		this.msg = msg + "\n" + toadd;
	}

	public String getMessage() {
		return this.msg;
	}


	public boolean emptyDraw(Card top, DeckOfCards deck) {
		int i = 0;
		while (phand.numSearch(top.getNumber()) == -1 && phand.suitSearch(top.getSuit()) == -1 && phand.numSearch(8) == -1) {

			if (deck.isEmpty()) {
				return false;
			}

			this.draw(deck);
			i++;
		}
		if (i > 0) {
			this.msg = msg + "\n" + this.name() + " had no playable cards and drew " + i + " cards from the deck.";
			System.out.println("You have no playable cards and drew " + i + " cards from the deck.");
		}
		return true;
	}

	protected boolean canPlay(int num, Card top) {
		Queue<Card> temp = new Queue<Card>();
		while (phand.numSearch(num) != -1) {
			int pos = phand.numSearch(num);
			temp.enqueue(phand.playCard(pos));
		}
		boolean ret = false;

		//return them back to player hand
		while (!temp.isEmpty()) {
			Card store = temp.dequeue();
			if (canPlay(store, top)) {
				ret = true;
			}
			phand.draw(store);
		}
		return ret;
	}

	protected boolean canPlay(Card test, Card top) {
		if (test.compareNum(top) == 0 || test.compareSuit(top) == 0 || test.getNumber() == 8) {
			return true;
		}
		return false;
	}



	public void draw(DeckOfCards deck) {
		phand.draw(deck.deal());
	}

	public ArrayList<Card> numPlay(int num) {
		ArrayList<Card> tem = new ArrayList<Card>();
		while (phand.numSearch(num) != -1) {
			int pos = phand.numSearch(num);
			tem.add(phand.playCard(pos));
		}
		return tem;
	}

	//maybe return number of cards played? for the sake of wildcards
	public Queue<Card> play(Card top) {
		Scanner choice = new Scanner(System.in);
		int temp;
		while (true) {
			System.out.print("Pick a number to play: ");
			if (choice.hasNextInt()) {
				temp = choice.nextInt();
				if (phand.numSearch(temp) == -1) {
					System.out.println("Please pick a number you own.");
				}
				if (canPlay(temp, top)) {
					System.out.println();
					choice.nextLine();
					break;
				}
				else {
					System.out.println("Please pick a number in which you have a playable card (matches number or suit).");
				}
			}
			else {
				System.out.println("Please pick a number you own.");
				choice.nextLine();
			}
		}//end while

		//store possibilities into something we can read?
		ArrayList<Card> tem = numPlay(temp);
		Queue<Card> ret = new Queue<Card>();
		while (true) {
			if (tem.isEmpty()) {
				break;
			}
			System.out.println("\nPick a card to play. Top is currently " + top);
			for(int k = 0; k < tem.size();k++) {
				System.out.println(k + ": " + tem.get(k));
			}
			if (choice.hasNextInt()) {
				int c = choice.nextInt();
				if (c < 0 || c >= tem.size()) {
					System.out.println("Please pick an index corresponding to the card to play.");
				}
				else {
					if (canPlay(tem.get(c),top)) {
						Card p = tem.remove(c);
						ret.enqueue(p);
						top = p;
						msg = msg + "\n" + this.name() + " played the " + p;
						System.out.println(p + " played.");
						choice.nextLine();
					}
					else {
						System.out.println("Please play a card that matches the suit or number of the top, or is an 8.");
					}
				}
			}
			else {
				System.out.println("Please pick a number corresponding to the card to play.");
				choice.nextLine();
			}
		}
		return ret;
		//let the player choose the order to play the cards
	}

	public static void main(String[] args) {
		DeckOfCards td = new DeckOfCards();
		CEplayer test = new CEplayer();

		for (int i = 0; i < 6; i++) {
			test.draw(td);
		}
		test.view();
		Card tri = new Card(1, "hearts");
		Queue<Card> res = test.play(tri);
		System.out.println();
		while (!res.isEmpty()) {
			System.out.println(res.dequeue());
		}
		System.out.println(test.getMessage());
		tri = new Card(7, "diamonds");
		test.resetMsg();
		test.emptyDraw(tri, td);
		System.out.println(test.getMessage());
	}

}