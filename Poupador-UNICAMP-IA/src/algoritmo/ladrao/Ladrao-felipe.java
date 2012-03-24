package algoritmo;

import java.util.ArrayList;
import java.util.Scanner;

import algoritmo.environment.MazeAction;

public class Ladrao extends ProgramaLadrao {

	private class ACT {
		private static final int NOACTION = 0;
		private static final int UP = 1;
		private static final int DOWN = 2;
		private static final int RIGHT = 3;
		private static final int LEFT = 4;
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
	
	private boolean flag = true;
	private ArrayList<Integer> actions;

	private void insertInActions(int direction, int times) {
		for (int i = 1; i <= times; i++) {
			actions.add(direction);
		}
	}


	public int acao() {
		if (flag) {
			flag = false;
			this.actions = new ArrayList<Integer>();
//			insertInActions(MazeAction.NO_MOVE, 5);
//			insertInActions(MazeAction.RIGHT, 2);
		}
		
		int action = MazeAction.NO_MOVE;
		if (!actions.isEmpty()) {
			action = actions.remove(0);
		}

		//return getHumanAction();
		//return (int) (Math.random() * 5);
		return action;
	}

	
}