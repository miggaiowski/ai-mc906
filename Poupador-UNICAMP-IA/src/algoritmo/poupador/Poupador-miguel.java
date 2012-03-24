package algoritmo;



import java.awt.List;
import java.util.ArrayList;

import javax.swing.JWindow;

import aima.core.util.datastructure.XYLocation;
import algoritmo.environment.Maze;
import algoritmo.environment.MazeAction;
import algoritmo.environment.element.MazeElement;
import algoritmo.environment.element.Saver;
import algoritmo.planning.path.ManathanHeuristic;
import algoritmo.planning.path.PlanGetMoney;
import algoritmo.planning.path.PlanGoTo;



public class Poupador extends ProgramaPoupador
{	
	public void Explore()
	{
		if ( !map_explored_flag )
		{
			map_explored_flag = true;
//			Maze.get_instance( ).reveal_all( Ambiente.matrizSimulacao );
		}
		
	}
	
	public void FSM()
	{
		if( plan_goto == null || !plan_goto.is_executable( ) )
		{

			if ( state == 0 )
				{	
				Maze maze = Maze.get_instance();
				int mapped = 0;
//				while (mapped < 600) {
//					mapped = 900;
					int menor = 100000;
					XYLocation mypos = maze.where_is(MazeElement.S2);
					XYLocation closest = new XYLocation(0,0);
					for (int i = 0; i < Maze.WIDTH; i++) {
						for (int j = 0; j < Maze.HEIGHT; j++) {
							if (maze.what_is(new XYLocation(i, j)).get_type() == MazeElement.NO_VISION) {
//								mapped--;
								XYLocation pos = new XYLocation(i, j);
								int dist = ManathanHeuristic.getDistance(pos, mypos);
								if (dist < menor) {
									menor = dist;
									closest = new XYLocation(i,j);
								}
							}
						}
					}
					XYLocation north, south, east, west;
					north = new XYLocation(closest.x(), closest.y()-1);
					south = new XYLocation(closest.x(), closest.y()+1);
					east = new XYLocation(closest.x()+1, closest.y());
					west = new XYLocation(closest.x()-1, closest.y());
					
					if (maze.what_is(north).get_type() != MazeElement.NO_VISION) {
						plan_goto = new PlanGoTo( new Saver( id ), north );
					}
					else if (maze.what_is(south).get_type() != MazeElement.NO_VISION) {
						plan_goto = new PlanGoTo( new Saver( id ), south );
					}
					else if (maze.what_is(east).get_type() != MazeElement.NO_VISION) {
						plan_goto = new PlanGoTo( new Saver( id ), east);
					}
					else if (maze.what_is(west).get_type() != MazeElement.NO_VISION) {
						plan_goto = new PlanGoTo( new Saver( id ), west );
					}
//					plan_goto = new PlanGoTo( new Saver( id ), closest);
					plan_goto = new PlanGoTo( new Saver( id ), new XYLocation(26,29));
				}
//					plan_getMoney = new PlanGetMoney(new Saver(id), 2);
//				}
//			if ( state == 0 )
//			{	
//				plan_goto = new PlanGoTo( new Saver( id ), new XYLocation(29,0) );
//			}
//			else if ( state == 1 )
//			{
//				plan_goto = new PlanGoTo( new Saver( id ), new XYLocation(0,0) );
//			}
//			else if ( state == 2 )
//			{
//				plan_goto = new PlanGoTo( new Saver( id ), new XYLocation(0,29) );
//			}
//			else if ( state == 3 )
//			{
//				plan_goto = new PlanGoTo( new Saver( id ), new XYLocation(29,29) );
//			}
			
			state++;
			if ( state > 0 )
				state = 0;
			
		}
	}
	
	public int acao()
	{	
		
		if ( id == MazeElement.S2 )
		{
			Explore( );
			Maze.get_instance().update_poupador(sensor, id);
			Maze.get_instance().print();
			FSM( );
			
//			return plan_getMoney.execute();
			return plan_goto.execute( );
//				
		}
	
		return MazeAction.NO_MOVE;
	 
	}
		
	Poupador () 
	{
		state = 0;
		
		// define who am I
		id = (id_flag) ? MazeElement.S1 : MazeElement.S2;
		id_flag = !id_flag;

		// Reset maze when gui hit stop
		if ( id == MazeElement.S1 )
		{
			map_explored_flag = false;
			Maze.reset_instance( );
		}
	}

	// Plan FSM
	private int state;	
	private PlanGoTo plan_goto;
	private PlanGetMoney plan_getMoney;
	
	// Exploration
	private static boolean map_explored_flag = false;
	private static int init_count = 0;
	
	// Id stuff
	private int id;
	private static boolean id_flag = true;
	
}
