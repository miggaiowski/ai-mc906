package algoritmo;



import java.util.List;


import aima.core.util.datastructure.XYLocation;
import algoritmo.environment.Maze;
import algoritmo.environment.MazeAction;
import algoritmo.environment.MazeCell;
import algoritmo.environment.element.MazeElement;
import algoritmo.environment.element.Saver;
import algoritmo.planning.DecisionMaker;
import algoritmo.planning.path.ManathanHeuristic;
import algoritmo.planning.path.Plan;
import algoritmo.planning.path.PlanExplore;
import algoritmo.planning.path.PlanGetMoney;
import algoritmo.planning.path.PlanGoTo;
import algoritmo.planning.path.PlanGoToBank;



public class Poupador extends ProgramaPoupador
{	

	
	public int acao()
	{	
		if(_decisionMaker==null)
			_decisionMaker = new DecisionMaker(id, sensor);
		return _decisionMaker.move(sensor);
	}
		
	Poupador () 
	{
		// define who am I
		id = (id_flag) ? MazeElement.S1 : MazeElement.S2;
		id_flag = !id_flag;

		saver = new Saver(id);
		
		// Reset maze when gui hit stop
		if ( id == MazeElement.S1 )
			Maze.reset_instance( );
		
	}
	
	private Saver saver;  //it`s me, Mario!

	private DecisionMaker _decisionMaker;
	
	// Id stuff
	private int id;
	private static boolean id_flag = true;
	
	
}
