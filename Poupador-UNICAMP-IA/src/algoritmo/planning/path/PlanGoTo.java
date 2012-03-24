package algoritmo.planning.path;

import aima.core.agent.Action;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import aima.core.util.datastructure.XYLocation;
import algoritmo.SensoresPoupador;
import algoritmo.environment.Maze;
import algoritmo.environment.MazeAction;
import algoritmo.environment.MazeFactory;
import algoritmo.environment.element.Saver;

public class PlanGoTo extends Plan
{

	public PlanGoTo( Saver actor, XYLocation goal )
	{
		super(actor);
		
		this.saver = actor;
		
		try
		{
			goto_factory = new GoToFactory( actor, goal );
			
			MazeFactory maze_factory = new MazeFactory( actor );
			
			Maze maze = Maze.get_instance( );
			
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
				search_agent = null;
			}
			
		} catch( Exception e )
		{
			search_agent = null;
		}
		
	}
	
	public int execute( SensoresPoupador sensor )
	{
		super.update( sensor );
		
		if ( is_executable( ) )
		{
				
			Action action = search_agent.execute( null );
			
			if ( action != null && !action.isNoOp( ) )
			{
				MazeAction maze_action = (MazeAction) action;
				
				Maze maze = Maze.get_instance( );
				maze.move( saver, maze_action );
				
				return maze_action.get_action( );	
			}

		}

		return MazeAction.NO_MOVE;

	}
	
	public boolean is_executable()
	{
		return  search_agent !=null && !search_agent.isDone( );
	}
	
	private Saver saver;
	private SearchAgent search_agent;
	private GoToFactory goto_factory;
}
