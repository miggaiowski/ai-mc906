package algoritmo;

import java.util.Random;

import aima.core.util.datastructure.XYLocation;
import algoritmo.environment.Maze;
import algoritmo.environment.MazeAction;
import algoritmo.environment.element.MazeElement;
import algoritmo.environment.element.Saver;
import algoritmo.planning.path.Plan;
import algoritmo.planning.path.PlanGetMoney;
import algoritmo.planning.path.PlanGoTo;
import algoritmo.planning.path.PlanGoToBank;



public class Poupador extends ProgramaPoupador
{	
	public void Explore()
	{
		if ( !map_explored_flag )
		{
			map_explored_flag = true;
			Maze.get_instance( ).reveal_all( Ambiente.matrizSimulacao );
		}
		
	}
	
	public void FSM()
	{
		if ( plan == null || !plan.is_executable( ) )
		{
			if ( id == MazeElement.S1 )
			{
				if ( state == 0 )
				{
					plan = new PlanGetMoney( saver, 4 );
					state = 1;
				}
				else if ( state == 1 )
				{
					plan = new PlanGoToBank( saver );
					state = 0;
				}	
			}
			else
			{
				int x = random.nextInt( Maze.WIDTH );
				int y = random.nextInt( Maze.HEIGHT );
				XYLocation goal = new XYLocation(x,y);
				plan = new PlanGoTo( saver, goal );
			}	
		}

	}
	
	public int acao()
	{	
	
		Explore( );
		
		FSM( );
					
		return plan.execute( sensor );
	 
	}
		
	Poupador () 
	{
		state = 0;
		
		random = new Random( );
		
		// define who am I
		id = (id_flag) ? MazeElement.S1 : MazeElement.S2;
		id_flag = !id_flag;
		
		saver = new Saver( id );

		// Reset maze when gui hit stop
		if ( id == MazeElement.S1 )
		{
			map_explored_flag = false;
			Maze.reset_instance( );
		}
	}

	private static Random random;
	
	private Saver saver;
	
	// Plan FSM
	private Plan plan;
	private int state;
	
	
	// Exploration
	private static boolean map_explored_flag = false;
	
	// Id stuff
	private int id;
	private static boolean id_flag = true;
	
}
