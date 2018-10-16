import java.util.*;

public class Game {
	private static final String[] gamelist = {"Go Fish", "Crazy Eights"};
	private static final String[] lowerlist = {"gofish", "crazyeights"};
	private static final String[] nospace = {"go fish", "crazy eights"};

	public static ArrayList<String> gamelist() {
		ArrayList<String> ret = new ArrayList<String>();
		for (String game: gamelist) {
			ret.add(game);
		}
		return ret;
	}

	public static boolean play(int game) {
		switch(game) {
			case 0: 
				GoFish gameplay = new GoFish();
				break;
			case 1:
				CrazyEights gameplay2 = new CrazyEights();
				break;
			default:
				return false;
		}
		return true;
	}

	public static boolean play(String game) {
		for (int i = 0; i < gamelist.length; i++) {
			if (game.equals(gamelist[i]) || game.equals(lowerlist[i]) || game.equals(nospace[i])) {
				return play(i);
			}
		}
		return false;
	}
	
	public static void main (String[] args) {
		ArrayList<String> games = gamelist();
		System.out.println("Game List:" );
		for (int i = 1; i <= games.size(); i++) {
			System.out.println("Game #" + i + ": " + games.get(i-1));
		}

		Scanner in = new Scanner(System.in);
		while(true) {
			System.out.println("\nPlease select a game.");
			if (in.hasNextInt()) {
				int ch = in.nextInt();
				if (play(ch - 1)) {
					break;
				}
				else {
					System.out.println("Please pick the index given in the game list.");
					in.nextLine();
				}
			}
			else {
				String ch = in.nextLine();
				ch = ch.toLowerCase();
				if (play(ch)) {
					break;
				}
				else {
					System.out.println("Please enter the name of the game or its corresponding number.");
					in.nextLine();
				}
			}

		}
	}

}