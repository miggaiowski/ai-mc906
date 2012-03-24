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
import algoritmo.environment.element.MazeElement;
import algoritmo.environment.element.Saver;

public class PlanGoToBank extends Plan
{
	private void setup()
	{
		
		state = State.ASTAR;
		bump = false;
		
		
		try
		{
			goto_factory = new GoToBankFactory( saver );
			
			MazeFactory maze_factory = new MazeFactory( saver );
			
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
				state = State.STOP;
				search_agent = null;
			}
			
		} catch( Exception e )
		{
			state = State.STOP;
			search_agent = null;
		}
		
	}

	public PlanGoToBank( Saver saver )
	{
		super(saver);
		
		this.saver = saver;
		
		setup();
	}
	
	public boolean is_executable()
	{
		boolean executable = false;
		
		switch( state )
		{
			case ASTAR:
				if ( astar_done( ) )
					state = State.BUMP;
				executable = true;
				break;
				
			case BUMP:
				if ( bump )
					state = State.STOP;
				executable = true;
				break;
				
			case STOP:
				executable = false;
				break;
		}
		
		return executable;
	}

	private boolean astar_done()
	{
		return ( search_agent == null || search_agent.isDone() );
	}
	
	public int execute( SensoresPoupador sensor )
	{
		super.update( sensor);
		
		if ( is_executable( ) )
		{
			MazeAction maze_action = get_next_action( );
			
			if ( maze_action != null )
			{
				Maze maze = Maze.get_instance( );
				maze.move( saver, maze_action );				
				return maze_action.get_action( );
			}
		}

		return MazeAction.NO_MOVE;

	}
	
	private MazeAction get_next_action()
	{
		
		MazeAction maze_action = new MazeAction( MazeAction.NO_MOVE );
		switch( state )
		{
			case ASTAR: 
				
				Action action =	search_agent.execute( null );
				
				if(!action.isNoOp())
					maze_action = (MazeAction) action;
				
				break;
				
			case BUMP:
				maze_action = get_bump( );
				bump = true;
				break;
		}
		
		return maze_action;
	}
		
	private enum State{ ASTAR, BUMP, STOP };
	private boolean bump;
	
	private State state;
	private Saver saver;
	private SearchAgent search_agent;
	private GoToBankFactory goto_factory;
}
