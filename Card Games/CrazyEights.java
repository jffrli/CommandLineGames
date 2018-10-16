import java.util.*;

public class CrazyEights{
	private CEplayer[] players;
	//private boolean[] cpu;
	private int pnum;
	private int turn;
	private Stack<Card> discard;
	private DeckOfCards deck;
	private int draws; //for card draws
	private int skips; //for turn skip
	private boolean wild8;
	private boolean[] cpu;

	public CrazyEights() {
		Scanner prompt = new Scanner(System.in);
		this.deck = new DeckOfCards();
		deck.shuffle();
		discard = new Stack<Card>();
		this.turn = 0;
		this.draws = 0;
		this.skips = 0;
		this.wild8 = false;
		setup();
		deal();
		discard.push(deck.deal());
		System.out.println("Press Enter to start the game");
		prompt.nextLine();
		turns();
	}

	private void setup() {
		Scanner input = new Scanner(System.in);
		while (true) {
			System.out.print("Please enter the number of players: ");
			if (input.hasNextInt()) {
				this.pnum = input.nextInt();
				if (pnum > 6) {
					System.out.println("Too many players. This game allows a maximum of 6. Try again.");
				}
				else if (pnum <= 1) {
					System.out.println("You need at least 2 players to play this game. Try again.");
				}
				else {
					System.out.println();
					input.nextLine();
					break;
				}
			}
			else {
				System.out.println(" Invalid input.");
				input.next();
			}
		}
		players = new CEplayer[pnum];
		/*
		//no bots--------------------------------------------------------------------------------------
		for (int i = 0; i < pnum; i++) {
			System.out.print("Please enter Player " + (i+1) + "\'s name (or leave it empty): ");
			String s = input.nextLine();
			if (s.equals("")) {
				players[i] = new CEplayer();
				System.out.println(players[i].name());
			}
			else {
				players[i] = new CEplayer(s);
				System.out.println(players[i].name());
			}
		}
		//------------------------------------------------------------------------------------------------
		*/
		// For bots
		cpu = new boolean[pnum];
		int bc = 1;
		for (int i = 0; i < pnum; i++) {
			
			while(true) {
				System.out.print("Is player " + (i+1) + " a human (0) or a bot (1)? ");
				String cp = input.nextLine();
				cp = cp.toLowerCase();
				if (cp.equals("bot") || cp.equals("1")) {
					cpu[i] = true;
					break;
				}
				else if (cp.equals("human") || cp.equals("0")) {
					cpu[i] = false;
					break;
				}
				else {
					System.out.println("Please enter \"human\" (0) or \"bot\" (1).");
				}
			}		
			if (cpu[i]) {
				String na = "Bot " + bc++;
				players[i] = new CEplayer(na);
			}
			else {
				System.out.print("Please enter Player " + (i+1) + "\'s name (or leave it empty): ");
				String s = input.nextLine();
				if (s.equals("")) {
					players[i] = new CEplayer();
					System.out.println(players[i].name());
				}
				else {
					players[i] = new CEplayer(s);
					System.out.println(players[i].name());
				}
			}
		}
	}

	private void deal() {
		int startingnum;
		if (pnum > 2) {
			startingnum = 8;
		}
		else {
			startingnum = 7;
		}
		for (int i = 0; i < startingnum; i++) {
			for (CEplayer player: players) {
				player.draw(deck);
			}
		}
	}

	private void reshuffle() {
		Card temp = discard.pop();
		while (!discard.isEmpty()) {
			deck.stuff(discard.pop());
		}
		deck.shuffle();
		discard.push(temp);
		System.out.println("Discard was reshuffled back into the deck.");
	}

	private void turns() {
		boolean cont = true;
		while (cont) {
			while (skips > 0) {
				skips--;
				players[turn].resetMsg();
				players[turn].msgAdd(players[turn] + "'s turn was skipped.");
				turn++;
				if (turn == pnum) {
					turn = 0;
				}
			}
			if (cpu[turn]) {
				this.botTurn(players[turn]);
			}
			else {
				this.playerTurn(players[turn]);
			}
			turn++;
			if (turn == pnum) {
				turn = 0;
			}
			for (CEplayer player: players) {
				if (player.isEmpty()) {
					System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + player + " wins!");
					cont = false;;
				}
			}
		}


	}

	public void MessageBoard() {
		for (int k = turn + 1; k != turn; k++) {
			if (k == pnum) {
				k = 0;
			}
			if (k == turn) {
				break;
			}
			System.out.println(players[k].getMessage());
			System.out.println(players[k].pdata());
			System.out.println();
		}
		System.out.println(players[turn].sdata());
	}
	

	private void playerTurn(CEplayer player) {
		player.resetMsg();

		Scanner pin = new Scanner(System.in);
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println(player + ", press Enter to start your turn.");

		String deg = pin.nextLine();
		if (deg.equals("debug")) {
			discard.debug();
		}
		this.MessageBoard();

		for (int i = 1; i <= draws; i++) {
			if (deck.isEmpty()) {
				reshuffle();
			}
			if (deck.isEmpty()) {
				player.msgAdd("Deck empty, no additional cards could be drawn.");
				System.out.println("Deck empty, no additional cards could be drawn.");

				player.msgAdd(player.name() + " drew " + i + " card(s) from the deck.");
				System.out.println("You drew " + i + " card(s) from the deck because of twos.");

				break;
			}
			player.draw(deck);
			if (i == draws) {
				player.msgAdd(player.name() + " drew " + i + " card(s) from the deck.");
				System.out.println("You drew " + i + " card(s) from the deck because of twos.");
			}
		}

		while(!player.emptyDraw(discard.peek(), deck)) {
			reshuffle();
			if (deck.isEmpty()) {
				break;
			}
		}
		Queue<Card> cds = new Queue<Card>();

		player.view();
		System.out.println("\nTop card is " + discard.peek());

		cds = player.play(discard.peek());

		if (wild8) {
			discard.pop();
			wild8 = false;
		}

		int see = cds.peek().getNumber();
		int wild = 0;

		if (see == 2) {
			wild = 1;
		}
		else if (see == 11) {
			wild = 2;
		}
		else if (see == 8) {
			wild = 3;
		}

		switch(wild) {
			case 1:
				while (!cds.isEmpty()) {
					discard.push(cds.dequeue());
					draws += 2;
				}
				skips = 0;
				wild8 = false;
				break;
			case 2:
				while (!cds.isEmpty()) {
					discard.push(cds.dequeue());
					skips++;
				}
				draws = 0;
				wild8 = false;
				break;
			case 3:
				while (!cds.isEmpty()) {
					discard.push(cds.dequeue());
				}
				draws = 0;
				skips = 0;
				wild8 = true;

				while(true) {
					System.out.println("Please pick a suit to change to: spades (0), hearts (1), clubs (2), diamonds (3).");
					String s = pin.next();
					if (s.equals("spades") || s.equals("hearts") || s.equals("clubs") || s.equals("diamonds")) {
						Card ghost = new Card(8, s);
						discard.push(ghost);
						player.msgAdd(player + " changed the suit to " + s + ".");
						break;
					}
					else if (s.equals("0") || s.equals("1") || s.equals("2") || s.equals("3")) {
						int i = Integer.parseInt(s);
						if (i==0) {
							s = "spades";
						}
						else if (i==1) {
							s = "hearts";
						}
						else if (i==2) {
							s = "clubs";
						}
						else if (i==3) {
							s = "diamonds";
						}
						Card ghost = new Card(8, s);
						discard.push(ghost);
						player.msgAdd(player + " changed the suit to " + s + ".");
						break;
					}
					else {
						System.out.println("Please input a valid suit or its corresponding number.");
						pin.nextLine();
					}
				}

				break;
			default:
				draws = 0;
				skips = 0;
				wild8 = false;
				while (!cds.isEmpty()) {
					discard.push(cds.dequeue());
				}
		}//end switch

		System.out.println("Press Enter to end your turn");
		deg = pin.nextLine();
		if (deg.equals("debug")) {
			discard.debug();
		}
	}

	private void botTurn(CEplayer bot) {
		bot.resetMsg();

		for (int i = 1; i <= draws; i++) {
			if (deck.isEmpty()) {
				reshuffle();
			}
			if (deck.isEmpty()) {
				bot.msgAdd("Deck empty, no additional cards could be drawn.");

				bot.msgAdd(bot.name() + " drew " + i + " card(s) from the deck.");

				break;
			}
			bot.draw(deck);
			if (i == draws) {
				bot.msgAdd(bot.name() + " drew " + i + " card(s) from the deck.");
			}
		}

		while(!bot.emptyDraw(discard.peek(), deck)) {
			reshuffle();
			if (deck.isEmpty()) {
				break;
			}
		}

		int pn;
		Random rand = new Random();
		while(true) {
			pn = rand.nextInt(13) + 1;
			if (bot.canPlay(pn, discard.peek())) {
				break;
			}
		}

		ArrayList<Card> tem = bot.numPlay(pn);

		Queue<Card> plays = new Queue<Card>();
		Card c = discard.peek();
		while (!tem.isEmpty()) {
			pn = rand.nextInt(tem.size());
			if (bot.canPlay(tem.get(pn), c)) {
				Card p = tem.remove(pn);
				bot.msgAdd(bot.name() + " played the " + p);
				plays.enqueue(p);
				c = p;
			}
		}


		if (wild8) {
			discard.pop();
			wild8 = false;
		}

		int see = plays.peek().getNumber();
		int wild = 0;

		if (see == 2) {
			wild = 1;
		}
		else if (see == 11) {
			wild = 2;
		}
		else if (see == 8) {
			wild = 3;
		}

		switch(wild) {
			case 1:
				while (!plays.isEmpty()) {
					discard.push(plays.dequeue());
					draws += 2;
				}
				skips = 0;
				wild8 = false;
				break;
			case 2:
				while (!plays.isEmpty()) {
					discard.push(plays.dequeue());
					skips++;
				}
				draws = 0;
				wild8 = false;
				break;
			case 3:
				while (!plays.isEmpty()) {
					discard.push(plays.dequeue());
				}
				draws = 0;
				skips = 0;
				this.wild8 = true;

				pn = rand.nextInt(4);
				String s;

				if (pn==0) {
					s = "spades";
				}
				else if (pn==1) {
					s = "hearts";
				}
				else if (pn==2) {
					s = "clubs";
				}
				else {
					s = "diamonds";
				}
				Card ghost = new Card(8, s);
				discard.push(ghost);
				bot.msgAdd(bot + " changed the suit to " + s + ".");
		
				break;

			default:
				draws = 0;
				skips = 0;
				wild8 = false;
				while (!plays.isEmpty()) {
					discard.push(plays.dequeue());
				}
		}//end switch

	}

	public static void main(String[] args) {
		CrazyEights test = new CrazyEights();
	}

}