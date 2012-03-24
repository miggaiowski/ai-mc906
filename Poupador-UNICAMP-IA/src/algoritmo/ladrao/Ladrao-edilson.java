package algoritmo;

import java.util.Scanner;

public class Ladrao extends ProgramaLadrao {

	private class ACT {
		// TODO need review
		private static final int NOACTION = 0;
		private static final int UP = 1;
		private static final int DOWN = 2;
		private static final int LEFT = 3;
		private static final int RIGHT = 4;
	}

	// aux function to read from stdin
	private int getHumanAction() {
		int i = 0;
		Scanner scan = new Scanner (System.in);
		while (true) {
			if (!scan.hasNextInt()) {
				scan.next(); // flushes the value
				continue;
			}
			i = scan.nextInt();

			if (i == ACT.UP || i == ACT.DOWN || i == ACT.LEFT || i == ACT.RIGHT || i == ACT.NOACTION) {
				scan.close();
				return i;
			}
		}
	}


	public int acao() {
		return getHumanAction();
	}

	
}