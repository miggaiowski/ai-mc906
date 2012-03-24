package algoritmo;


public class Poupador extends ProgramaPoupador {

	private int table[][] = new int[30][30];
	
	
	private class ACT {
		// TODO need review
		private static final int NOACTION = 0;
		private static final int UP = 1;
		private static final int DOWN = 2;
		private static final int LEFT = 3;
		private static final int RIGHT = 4;
	}

	private Point getQuadranteMaisLonge() {
		
	}
	
	private void printTable() {
		for (int i = 0; i < Ambiente.lin; i++) {
			for (int j = 0; j < Ambiente.col; j++) {
				System.out.printf("%03d ", Ambiente.matrizSimulacao[i][j]);
			}
			System.out.print('\n');
		}
		System.out.print('\n');
	}
	
	private void printOlfatoPoupador() {
		System.out.printf("%d %d\n", sensor.getPosicao().x, sensor.getPosicao().y);
		//		System.out.println(sensor.getAmbienteOlfatoPoupador().length);
		for (int i = 0; i < 3; i++) {
			System.out.printf("%03d ", sensor.getAmbienteOlfatoPoupador()[i]);
		}
		System.out.println();
		System.out.printf("%03d ", sensor.getAmbienteOlfatoPoupador()[3]);
		System.out.printf(" X  ");
		System.out.printf("%03d", sensor.getAmbienteOlfatoPoupador()[4]);
		System.out.println();
		for (int i = 5; i < 8; i++) {
			System.out.printf("%03d ", sensor.getAmbienteOlfatoPoupador()[i]);
		}
		System.out.println();
		System.out.println();		
	}
	
	private void printOlfatoLadrao() {
		System.out.printf("%d %d\n", sensor.getPosicao().x, sensor.getPosicao().y);
		//		System.out.println(sensor.getAmbienteOlfatoPoupador().length);
		for (int i = 0; i < 3; i++) {
			System.out.printf("%03d ", sensor.getAmbienteOlfatoLadrao()[i]);
		}
		System.out.println();
		System.out.printf("%03d ", sensor.getAmbienteOlfatoLadrao()[3]);
		System.out.printf(" X  ");
		System.out.printf("%03d", sensor.getAmbienteOlfatoLadrao()[4]);
		System.out.println();
		for (int i = 5; i < 8; i++) {
			System.out.printf("%03d ", sensor.getAmbienteOlfatoLadrao()[i]);
		}
		System.out.println();
		System.out.println();		
	}

	public int acao() {		
		try {
			Thread.currentThread().sleep(100);
		}
		catch(InterruptedException ie) {
			
		}
		printOlfatoLadrao();
		return (int) (Math.random() * 5);
	}
}