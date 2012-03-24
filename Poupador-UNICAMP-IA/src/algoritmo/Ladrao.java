package algoritmo;

import java.util.ArrayList;
import java.util.Scanner;

import algoritmo.environment.MazeAction;

public class Ladrao extends ProgramaLadrao {

	public int acao() {
		return (int) (Math.random() * 5);
	}

	
}