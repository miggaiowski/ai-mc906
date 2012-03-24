package algoritmo.planning.path;

import java.util.List;

import aima.core.agent.Action;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import aima.core.util.datastructure.XYLocation;
import aima.core.util.datastructure.XYLocation.Direction;
import algoritmo.SensoresPoupador;
import algoritmo.environment.Maze;
import algoritmo.environment.MazeAction;
import algoritmo.environment.MazeFactory;
import algoritmo.environment.element.MazeElement;
import algoritmo.environment.element.Saver;

public class PlanGoToExplore extends Plan
{
	private void calculate(SensoresPoupador sensor, XYLocation loc)
	{
		super.update( sensor );

		try
		{
			Maze maze = Maze.get_instance();
			
			goto_factory = new GoToFactory( saver, loc );
			
			MazeFactory maze_factory = new MazeFactory( saver );
						
			Problem problem = new Problem( 
					maze,
					maze_factory.get_actions_function( ),
					maze_factory.get_result_function( ),
					goto_factory.getGoalFunction( ) );
			
			Search search = new AStarSearch(
					new GraphSearch( ),
					goto_factory.getHeuristicFunction( ) );
			
			try
			{
				search_agent = new SearchAgent( problem, search );
				
			} catch( Exception e )
			{
				e.printStackTrace( );
			}
			
		} catch( Exception e )
		{
			search_agent = null;
		}		
	}

	public PlanGoToExplore( Saver saver, SensoresPoupador sensor, XYLocation loc )
	{
		super(saver);
		this.saver = saver;
		this.target = loc;
		
		calculate(sensor, loc);
	}
	
	public int execute( SensoresPoupador sensor )
	{
		
		super.update(sensor);
		
		MazeAction bumpAction = should_bump(sensor);
		
		if (bumpAction.get_action() != MazeAction.NO_MOVE)
			return bumpAction.get_action();
		
		if ( is_executable( ) )
		{
			Action action = search_agent.execute( null );

			if ( action != null && !action.isNoOp( ) )
			{
				MazeAction maze_action = (MazeAction) action;
			
				Maze maze = Maze.get_instance( );
				if(maze.can_move(saver, maze_action.get_direction()))
				{
					maze.move( saver, maze_action );
					
					return maze_action.get_action( );
				}
				else
				{
					// invalid move! re-calculate it
					calculate(sensor, target);
					return execute(sensor);
				}
			}
			else search_agent = null;
		}

		return MazeAction.NO_MOVE;

	}
	
	private MazeAction should_bump(SensoresPoupador sensor)
	{
		if(sensor.getNumeroDeMoedas() > 0)
		{
			if(Maze.get_instance().is_known_position(MazeElement.BANK))
			{
				return get_bump();
			}
		}
		return new MazeAction( MazeAction.NO_MOVE );
	}
	
	public boolean is_executable()
	{
		return  search_agent != null && !search_agent.isDone( ); 
	}
	
	private Saver saver;
	private SearchAgent search_agent;
	private GoToFactory goto_factory;
	private XYLocation target;
}
