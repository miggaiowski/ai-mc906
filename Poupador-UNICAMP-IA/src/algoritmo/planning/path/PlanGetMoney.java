package algoritmo.planning.path;

import aima.core.agent.Action;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import algoritmo.SensoresPoupador;
import algoritmo.environment.Maze;
import algoritmo.environment.MazeAction;
import algoritmo.environment.MazeFactory;
import algoritmo.environment.element.Saver;

public class PlanGetMoney extends Plan
{

	public PlanGetMoney( Saver saver, int money_count )
	{
		super(saver);
		this.saver = saver;
		
		try
		{
			money_factory = new MoneyGoalFactory( saver, money_count );
			if (money_factory.TotalCoinsInMaze < money_count ) {
				money_factory = new MoneyGoalFactory(saver, money_factory.TotalCoinsInMaze);
			}
			
			MazeFactory maze_factory = new MazeFactory( saver );
			
			Maze maze = Maze.get_instance( );
			
			Problem problem = new Problem( 
					maze,
					maze_factory.get_actions_function( ),
					maze_factory.get_result_function( ),
					money_factory.getGoalFunction( ));
			
			Search search = new AStarSearch(
					new GraphSearch( ),
					money_factory.getHeuristicFunction( ) );
			
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
	
	public int execute(SensoresPoupador sensor)
	{
		super.update( sensor);
		
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
		return  search_agent != null && !search_agent.isDone( ); 
	}
	
	private Saver saver;
	private SearchAgent search_agent;
	private MoneyGoalFactory money_factory;
}
