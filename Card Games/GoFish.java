import java.util.*;

/*
TODO
Write a rules thing
Write a discard pile (who has what)
*/

public class GoFish {
	private GFplayer[] players;
	private DeckOfCards deck;
	private int pnum;
	private boolean tof; //two or 4 cards to match
	private int turn;
	private boolean[] cpu;

	public GoFish() {
		Scanner prompt = new Scanner(System.in);
		this.deck = new DeckOfCards();
		deck.shuffle();
		this.turn = 0;
		setup();
		System.out.println("\n\n");
		deal();
		System.out.println("Press Enter to start the game");
		prompt.nextLine();
		turns();
	}

	private void setup() {
		Scanner input = new Scanner(System.in);
		while (true) {
			System.out.print("Please enter whether you would like to match pairs or sets of four: ");
			if (input.hasNextInt()) {
				int f = input.nextInt();
				if (!(f == 2 || f == 4)) {
					System.out.println("Please enter 2 or 4");
				}
				else {
					System.out.println();
					input.nextLine();
					if (f == 2) {
						tof = false;
					}
					else {
						tof = true;
					}
					break;
				}
			}
			else {
				System.out.println("Please enter 2 or 4");
				input.next();
			}
		}
		while (true) {
			System.out.print("Please enter the number of players: ");
			if (input.hasNextInt()) {
				this.pnum = input.nextInt();
				if (pnum > 10) {
					System.out.println("Too many players. This game allows a maximum of 10. Try again.");
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
		players = new GFplayer[pnum];
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
				players[i] = new GFplayer(na, tof);
			}
			else {
				System.out.print("Please enter Player " + (i+1) + "\'s name (or leave it empty): ");
				String s = input.nextLine();
				if (s.equals("")) {
					players[i] = new GFplayer(tof);
					System.out.println(players[i].name());
				}
				else {
					players[i] = new GFplayer(s, tof);
					System.out.println(players[i].name());
				}
			}
		}
	}

	private void deal() {
		int startingnum;
		if (pnum < 5) {
			startingnum = 7;
		}
		else {
			startingnum = 5;
		}
		for (int i = 0; i < startingnum; i++) {
			for (GFplayer player: players) {
				player.fish(deck);
			}
		}
		for (GFplayer player: players) {
			if (player.match()) {
				System.out.println(player.getMessage());
				player.resetMsg();
			}
		}
	}


	private void turns() {
		boolean cont = false;
		do {
			if (cpu[turn]) {
				this.botTurn(players[turn]);
			}
			else {
				this.playerTurn(players[turn]);
			}

			cont = true;

			//check for game end
			if (deck.isEmpty()) {
				for (GFplayer player: players) {
					if (player.isEmpty()) {
						cont = false;
					}
				}
			}

			turn++;
			if (turn == pnum) {
				turn = 0;
			}

		} while(cont);
		scores();
		winners();

	}

	private void winners() {
		ArrayList<Integer> ind = new ArrayList<Integer>();
		int max = 0;
		for (int i = 0; i < pnum; i++) {
			if (players[i].getScore() > max) {
				max = players[i].getScore();
				ind = new ArrayList<Integer>();
				ind.add(i);
			}
			else if (players[i].getScore() == max) {
				ind.add(i);
			}
			else{}
		}
		if (ind.size() == 1) {
			System.out.println("" + players[ind.get(0)] + " wins with a score of " + max + "!");
		}
		else {
			String s = "";
			String pr = "";
			for (Integer i: ind) {
				pr = pr + s + players[i];
				s = ", ";
			}
			pr = pr + " tied with a score of " + max + "!";
			System.out.println(pr);
		}
	}

	private void scores() {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nFinal Scores:");
		for (GFplayer player: players) {
			System.out.println(player + ":\t" + player.getScore());
		}
	}

	/*
	Displays the messages of all players in order (from oldest to newest)
	*/
	private void MessageBoard() {
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
		System.out.println("Deck has " + deck.checkNum() + " card(s) left.");
	}

	/*
	a single player's turn
	*/
	private void playerTurn(GFplayer player) {
		player.resetMsg();
		if (player.isEmpty()) {
			player.emptyDraw(deck);
			player.match();
			return;
		}
		Scanner pin = new Scanner(System.in);
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println(player + ", press Enter to start your turn.");
		pin.nextLine();
		this.MessageBoard();

		player.view();
		int target;
		while (true) {
			System.out.print("Please choose a player to ask: ");
			if (pin.hasNextInt()) {
				int tar = pin.nextInt();
				if (tar < 0 || tar >= pnum) {
					System.out.println("Invalid target. Please enter a valid player number.");
				}
				else if (tar == turn) {
					System.out.println("You can't target yourself!");
				}
				else if (players[tar].isEmpty()) {
					System.out.println("That player has no cards left.");
				}
				else {
					pin.nextLine();
					target = tar;
					break;
				}
			}
			else {
				System.out.println("Invalid target. Please enter a valid player number.");
				pin.next();
			}
		}
		boolean success;
		while (true) {
			System.out.print("Please choose a number to ask for: ");
			if (pin.hasNextInt()) {
				int tar = pin.nextInt();
				if (tar <= 0 || tar > 13) {
					System.out.println("Invalid number. Please enter a number between 1 and 13 (inclusive).");
				}
				else {
					pin.nextLine();
					success = player.take(players[target], tar);
					break;
				}
			}
			else {
				System.out.println("Invalid number. Please enter a number between 1 and 13 (inclusive).");
				pin.next();
			}
		}
		if (!success) {
			if (!deck.isEmpty()) {
				player.fish(deck);
			}
		}
		player.match();
		player.emptyDraw(deck);
		System.out.println(player.selfMessage());
		System.out.println("Press Enter to end your turn");
		pin.nextLine();
	}

	private void botTurn(GFplayer bot) {
		bot.resetMsg();
		if (bot.isEmpty()) {
			bot.emptyDraw(deck);
			bot.match();
			return;
		}
		Random rand = new Random();
		int rnd = rand.nextInt(bot.ccount());
		int tarn = bot.viewCard(rnd).getNumber();
		while (true) {
			rnd = rand.nextInt(pnum);
			if (rnd != turn && !players[rnd].isEmpty()) {
				break;
			}
		}
		boolean success;
		success = bot.take(players[rnd], tarn);
		if (!success) {
			if (!deck.isEmpty()) {
				bot.fish(deck);
			}
		}
		bot.match();
		bot.emptyDraw(deck);
		System.out.println(bot.getMessage());
		System.out.println(bot.pdata());
	}
	
	public static void main(String[] args) {
		GoFish test = new GoFish();
	}
}