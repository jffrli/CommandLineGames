import java.util.*;

public class EOSBoard {
	private int[] upperchange;
	private int[] lowerchange;
	private int[] surfacechange;
	private int upper;
	private int lower;
	private int surface;
	private int time;
	private boolean day;

	EOSBoard() {
		this.initchange();
		this.initval();
	}

	private void initchange() {
		this.upperchange = new int[28];
		this.lowerchange = new int[28];
		this.surfacechange = new int[28];
		for(int i = 0; i < 28; i++){
			if (i > 25) {
				upperchange[i] = 6;
				lowerchange[i] = 6;
				surfacechange[i] = 7;
			}
			else if (i > 22) {
				upperchange[i] = 5;
				lowerchange[i] = 5;
				surfacechange[i] = 6;
			}

			else if (i > 18) {
				upperchange[i] = 4;
				lowerchange[i] = 4;
				surfacechange[i] = 5;
			}

			else if (i > 13) {
				upperchange[i] = 3;
				lowerchange[i] = 3;
				surfacechange[i] = 4;
			}

			else if (i > 7) {
				upperchange[i] = 2;
				lowerchange[i] = 2;
				surfacechange[i] = 3;
			}
			else {
				upperchange[i] = 1;
				lowerchange[i] = 1;
				surfacechange[i] = 2;	
			}
		}
	}

	private void initval() {
		Scanner input = new Scanner(System.in);
		while (true) {
			System.out.print("Please the starting value for upper atmosphere temperature: ");
			if (input.hasNextInt()) {
				int up = input.nextInt();
				if (up > 28 || up <= 0) {
					System.out.print("Please enter a number between 1 and 28\n");
					input.next();
					continue;
				}
				this.setUpper(up);
				System.out.println();
				input.nextLine();
				break;
			}
			else {
				System.out.print("Please enter a number between 1 and 28\n");
				input.nextLine();
			}
		}

		while (true) {
			System.out.print("Please the starting value for lower atmosphere temperature: ");
			if (input.hasNextInt()) {
				int low = input.nextInt();
				if (low > 28 || low <= 0) {
					System.out.print("Please enter a number between 1 and 28\n");
					input.nextLine();
					continue;
				}
				this.setLower(low);
				System.out.println();
				input.nextLine();
				break;
			}
			else {
				System.out.print("Please enter a number between 1 and 28\n");
				input.next();
			}
		}
		
		while (true) {
			System.out.print("Please the starting value for surface temperature: ");
			if (input.hasNextInt()) {
				int sur = input.nextInt();
				if (sur > 28 || sur <= 0) {
					System.out.print("Please enter a number between 1 and 28\n");
					input.nextLine();
					continue;
				}
				this.setSurface(sur);
				System.out.println();
				input.nextLine();
				break;
			}
			else {
				System.out.print("Please enter a number between 1 and 28\n");
				input.next();
			}
		}

		while (true) {
			System.out.print("Please enter day (0) or night (1) to start: ");
			String cp = input.nextLine();
			cp = cp.toLowerCase();
				if (cp.equals("day") || cp.equals("0")) {
					this.setDay(true);
					break;
				}
				else if (cp.equals("night") || cp.equals("1")) {
					this.setDay(false);
					break;
				}
				else {
					System.out.println("Please enter \"day\" (0) or \"night\" (1).");
			}
		
		}
		this.resetTime();
	}

	private void setUpper(int val) {
		this.upper = val;
	}
	private void setLower(int val) {
		this.lower = val;
	}
	private void setSurface(int val) {
		this.surface = val;
	}
	private int getUpper() {
		return this.upper;
	}
	private int getLower() {
		return this.lower;
	}
	private int getSurface() {
		return this.surface;
	}

	private void setDay(boolean val) {
		this.day = val;
	}
	private boolean getDay() {
		return this.day;
	}

	private int getTime() {
		return this.time;
	}
	private void resetTime() {
		this.time = 1;
	}

	private void inctime() {
		this.time++;
		if (this.time > 3) {
			this.time = 1;
			this.setDay(!this.getDay());
		}
	}

	private int getsc() {
		int ind = getSurface() - 1;
		return this.surfacechange[ind];
	}
	private int getlc() {
		int ind = getLower() - 1;
		return this.lowerchange[ind];
	}
	private int getuc() {
		int ind = getUpper() - 1;
		return this.upperchange[ind];
	}


	private void turnincs() {
		int change;
		change = this.getsc();
		this.setSurface(getSurface() - change);
		this.setLower(getLower() + change);

		change = this.getlc();
		this.setSurface(getSurface() + change);
		this.setLower(getLower() - 2*change);
		this.setUpper(getUpper() + change);

		change = this.getuc();
		this.setLower(getLower() + change);
		this.setUpper(getUpper() - 2*change);
	}

	private String srep() {
		String res = "Day (";
		if (!day) {
			res = "Night (";
		}
		res += getTime();
		res += ")\nUpper Atmosphere: ";
		res += getUpper();
		res += "\nLower Atmosphere: ";
		res += getLower();
		res += "\nSurface: ";
		res += getSurface();
		res += "\n\n";
		return res;
	}

	public String toString() {
		return this.playxturns(3);
	}

	public String playxturns(int turns) {
		String ret = "";
		turns *= 3;
		for (int k = 0; k < turns; k++) {
			if (getDay()) {
				setSurface(getSurface() + 3);
			}
			this.turnincs();
			ret += this.srep();
			this.inctime();
		}
		return ret;
	}

	public static void main(String[] args) {
		EOSBoard test = new EOSBoard();
		System.out.println(test.playxturns(5));
	}

}