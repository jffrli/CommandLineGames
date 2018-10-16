/* TODO List
	Messages for self
	Empty draw
	Test stuff
*/


public class GFplayer {
	private PlayerHand phand;
	private int id;
	private int score;
	private boolean full; //false for pairs, true for 4 of a kind
	private static int pc = 0;
	private String msg; //message for other players
	private String sm; //message for you

	/**
	 * Creates a new instance of GFplayer
	 * @param name The name of the player
	 * @param full Whether the game matches pairs or sets of 4
	*/
	public GFplayer(String name, boolean full) {
		phand = new PlayerHand(name);
		this.id = pc++;
		this.score = 0;
		this.full = full;
		msg = "";
		sm = "";
	}

	public GFplayer(boolean full) {
		phand = new PlayerHand();
		this.id = pc++;
		this.full = full;
		this.score = 0;
		msg = "";
		sm = "";
	}

	public GFplayer(String name) {
		phand = new PlayerHand(name);
		this.id = pc++;
		this.full = false;
		this.score = 0;
		msg = "";
		sm = "";
	}

	public GFplayer() {
		phand = new PlayerHand();
		this.id = pc++;
		this.full = false;
		this.score = 0;
		msg = "";
		sm = "";
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
		phand.view();
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

	/**
	 * Returns the player's score
	 * @return the score
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Returns the player's current data as a string for the message board
	 * @return the string form
	 */
	public String pdata() {
		String d = this.name() + " has " + phand.ccount() + " card(s) and " + score + " point(s)";
		return d;
	}
	/**
	 * Returns the player's current data as a string for the player
	 * @return the string form
	 */
	public String sdata() {
		String d = "You have " + phand.ccount() + " card(s) and " + score + " point(s)";
		return d;
	}


	/**
	 * Finds the first occurance of a card with a given number in the player's hand.
	 * @param tofind The number we're looking for
	 * @return the index of the first card with that number
	*/
	protected int search(int tofind) {
		return phand.numSearch(tofind);
	}

	/**
	 * Draws a card from the deck and adds a message
	 * @param deck The deck to draw from
	*/
	public void fish(DeckOfCards deck) {
		Card temp = deck.deal();
		phand.draw(temp);
		/*String m = this.name() + " drew a card from the deck.\n";
		this.msg = m;*/
		String n = "You draw a " + temp + " from the deck.\n";
		this.sm = sm + n;
	}

	public void resetMsg() {
		this.msg = "";
		this.sm = "";
	}

	public String getMessage() {
		return this.msg;
	}

	public String selfMessage() {
		return this.sm;
	}

	protected Card viewCard(int index) {
		try {
			return phand.viewCard(index);
		}
		catch (InvalidCardException ice) {
			System.out.println("Invalid index, try again");
			return null;
		}
	}
	protected Card loss(int index) {
		return phand.playCard(index);
	}
	public boolean take(GFplayer target, Card findit) {
		return take(target, findit.getNumber());
	}

	public boolean take(GFplayer target, int tofind) {
		int tar = target.search(tofind);

		String m = this.name() + " searched " + target.name() + " for " + tofind + "s ";
		String n = "You search " + target.name() + " for " + tofind + "s ";

		int c = 0;
		if (tar < 0) {

			m = m + "but found nothing.\n";
			n = n + "but found nothing. Go fish!\n";
			this.msg = m;
			this.sm = n;

			return false;
		}
		phand.draw(target.loss(tar));
		c++;
		int tar2 = target.search(tofind);
		if (tar2 >= 0) {
			phand.draw(target.loss(tar2));
			c++;
		}
		int tar3 = target.search(tofind);
		if (tar3 >= 0) {
			phand.draw(target.loss(tar3));
			c++;
		}

		m = m + "and took " + c + " card";
		n = n + "and took " + c + " card";

		if (c > 1) {
			m = m + "s.\n";
			n = n + "s.\n";
		}
		else {
			m = m + ".\n";
			n = n + ".\n";
		}

		this.msg = m;
		this.sm = n;

		return true;
	}

	public boolean match() {
		int before = this.score;
		if (full) {
			for (int m = 0; m < phand.ccount(); m++) {
				int k = 0;
				int[] ind = new int[3];
				for (int n = 0; n < phand.ccount(); n++) {
					if (m == n) {
						continue;
					}
					if (phand.viewCard(m).compareNum(phand.viewCard(n)) == 0) {
						ind[k++] = n;
					}
				}
				if (k >= 3) {

					String s = this.name() + " has a set of " + phand.viewCard(m).getNumber() + "s.\n";
					this.msg = msg + s;
					String r = "You have a set of " + phand.viewCard(m).getNumber() + "s.\n";
					this.sm = sm + r;

					for (int h = phand.ccount() - 1; h >= 0; h--) {
						if (h == m || h == ind[0] || h == ind[1] || h == ind[2]) {
							phand.playCard(h);
						}
					}
					score++;
				}
			}
		}
		else {
			for (int i = 0; i < phand.ccount(); i++) {
				for (int j = 0; j < phand.ccount(); j++) {
					if (i == j) {
						continue;
					}
					if (phand.viewCard(i).compareNum(phand.viewCard(j)) == 0) {

						String s = this.name() + " has a pair of " + phand.viewCard(i).getNumber() + "s.\n";
						this.msg = msg + s;
						String r = "You have a pair of " + phand.viewCard(i).getNumber() + "s.\n";
						this.sm = sm + r;

						for (int h = phand.ccount() - 1; h >= 0; h--) {
							if (h == i || h == j) {
								phand.playCard(h);
							}
						}
						score++;
					}
				}
			}
		}
		int diff = score-before;

		String st = this.name() + " earned " + diff + " point(s) during their turn.";
		this.msg = msg + st;
		String rt = "You earned " + diff + " point(s) this turn.";
		this.sm = sm + rt;

		return (diff != 0);
	}

	public void emptyDraw(DeckOfCards deck) {
		if (this.isEmpty()) {
			String temp = this.selfMessage();
			int times = 5; //Subject to change
			for (int i = 0; i < times; i++) {
				if (deck.isEmpty()) {
					times = i;
					continue;
				}
				this.fish(deck);
			}
			this.sm = temp + "You ran out of cards! You draw " + times + " more cards from the deck.";
		}
	}


	/**
	 * Used for internal testing purposes
	 * @param args Not used
	*/
	public static void main(String[] args) {
		GFplayer test = new GFplayer();
		DeckOfCards deck = new DeckOfCards();
		for (int i = 0; i < 5; i++) {
			test.fish(deck);
		}
		System.out.println(test.pdata());
		test.match();
		System.out.println(test.getMessage());
		System.out.println(test.pdata());
		GFplayer p2 = new GFplayer();
		for (int i = 0; i < 5; i++) {
			p2.fish(deck);
		}
		p2.take(test,p2.viewCard(0));
		System.out.println(p2.getMessage());
		System.out.println("\n\n");

		GFplayer un = new GFplayer("Uno", true);
		GFplayer deux = new GFplayer("Dos", true);
		DeckOfCards pile = new DeckOfCards();
		for (int i = 0; i < 5; i++) {
			un.fish(pile);
			deux.fish(pile);
		}
		un.view();
		un.take(deux,un.viewCard(0));
		un.match();
		System.out.println(un.selfMessage());

	}
}