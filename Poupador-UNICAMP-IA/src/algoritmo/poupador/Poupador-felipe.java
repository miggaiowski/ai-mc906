package algoritmo;



import java.util.List;


import aima.core.util.datastructure.XYLocation;
import algoritmo.environment.Maze;
import algoritmo.environment.MazeAction;
import algoritmo.environment.MazeCell;
import algoritmo.environment.element.MazeElement;
import algoritmo.environment.element.Saver;
import algoritmo.planning.path.ManathanHeuristic;
import algoritmo.planning.path.Plan;
import algoritmo.planning.path.PlanExplore;
import algoritmo.planning.path.PlanGetMoney;
import algoritmo.planning.path.PlanGoTo;
import algoritmo.planning.path.PlanGoToBank;



public class Poupador extends ProgramaPoupador
{	

	
	public void FSM()
	{
		
		if( plan == null || !plan.is_executable( ) )
		{
			Maze maze = Maze.get_instance();

			if (maze.where_is(MazeElement.BANK) != null && 
				( sensor.getNumeroDeMoedas() > money_to_gotobank ) || ( maze.where_is_all( MazeElement.MONEY ).size() == 0 ))
			{
//				System.out.println("Indo pro banco");
				plan = new PlanGoToBank(saver);
			}
			else if (maze.where_is_all( MazeElement.NO_VISION ).size() < max_novision_to_getmoney)
			{
//				System.out.println("Pegando Dinheiro");
				plan = new PlanGetMoney(saver, 1);
			}
			else {
//				System.out.println("Explorando");
				plan = new PlanExplore(saver,sensor);
			}
		}
	}
	
	public int acao()
	{	
		Maze maze = Maze.get_instance();
		assert(((Saver)maze.what_is(maze.where_is(id))).consult() == sensor.getNumeroDeMoedas());
//		if ( id == MazeElement.S2 )
		if ( true )
		{
//			Maze.get_instance().update_poupador(sensor, id);
			FSM( );
			Maze.get_instance().print();
			return plan.execute( sensor );
		}
	
		return MazeAction.NO_MOVE;
	 
	}
		
	Poupador () 
	{
		state = 0;
		
		// define who am I
		id = (id_flag) ? MazeElement.S1 : MazeElement.S2;
		id_flag = !id_flag;

		saver = new Saver(id);
		
		// Reset maze when gui hit stop
		if ( id == MazeElement.S1 )
			Maze.reset_instance( );
	}
	
	private Saver saver;  //it`s me, Mario!

	// Plan FSM
	private int state;
	private Plan plan;
	private static final int max_novision_to_getmoney = 400;
	private static final int money_to_gotobank = 30;
	
	
	// Id stuff
	private int id;
	private static boolean id_flag = true;
	
	
}
