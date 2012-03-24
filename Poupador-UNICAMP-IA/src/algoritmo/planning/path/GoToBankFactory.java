package algoritmo.planning.path;

import aima.core.search.framework.GoalTest;
import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

import algoritmo.environment.Maze;
import algoritmo.environment.element.MazeElement;
import algoritmo.environment.element.Person;

public class GoToBankFactory
{

	public GoToBankFactory( Person actor ) throws ImpossibleGoalException
	{
		Maze maze = Maze.get_instance( );
		XYLocation bank_loc = maze.where_is( MazeElement.BANK );
		
		if ( bank_loc == null )
			throw new ImpossibleGoalException("Bank not found!");

		heuristic_function = new ManathanHeuristic( actor, bank_loc );
		goal_function = new GetToGoal( actor, bank_loc );
	}
	
	public HeuristicFunction getHeuristicFunction( )
	{	
		return heuristic_function;
	}
	
	public GoalTest getGoalFunction( )
	{
		return goal_function;
	}
	
	
	private class GetToGoal implements GoalTest
	{
		public GetToGoal( MazeElement actor, XYLocation goal )
		{
			this.actor = actor;
			this.bank_loc = goal;
			expanded_nodes = 0;
		}
		
		public boolean isGoalState( Object state )
		{
            expanded_nodes++;
            if ( expanded_nodes > MAX_NODES )
                    return true;
            
			Maze maze = (Maze) state;
			
			XYLocation agent_pos = maze.where_is( actor );
			
			return	bank_loc.up( ).equals( agent_pos )
				||	bank_loc.down( ).equals( agent_pos )
				||	bank_loc.left( ).equals( agent_pos )
				||	bank_loc.right( ).equals( agent_pos );
		}
		
		private MazeElement actor;
		private XYLocation bank_loc;
		
		private static final int MAX_NODES = 500;
	    private int expanded_nodes;

	}

	private HeuristicFunction heuristic_function;
	private GoalTest goal_function;
	
    
	
}
