package algoritmo.planning.path;


import aima.core.search.framework.GoalTest;
import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

import algoritmo.environment.Maze;
import algoritmo.environment.element.MazeElement;
import algoritmo.environment.element.Person;

public class MoneyGoalFactory
{

	public MoneyGoalFactory( Person actor, int money_count ) throws ImpossibleGoalException
	{
		Maze maze = Maze.get_instance( );
				
		TotalCoinsInMaze = 0;
		for (int i = 0; i < Maze.WIDTH; i++) {
			for (int j = 0; j < Maze.HEIGHT; j++) {
				if (maze.what_is(new XYLocation(i,j)).get_type() == MazeElement.MONEY) {
					TotalCoinsInMaze++;
				}
			}
		}
		
		if ( TotalCoinsInMaze >= money_count )
		{
			heuristic_function = new NumCoinsAvailableHeuristic( actor );
			goal_function = new GetToGoal( actor, TotalCoinsInMaze - money_count );
			
		}
		else
		{
			throw new ImpossibleGoalException( 
					"You want more coins (" + money_count + ") than are available ("
					+ TotalCoinsInMaze + ")" );
		}
		
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
		public GetToGoal( MazeElement actor, int goal )
		{
			randommness = -50 + (int)(Math.random() * ((50 - (-50)) + 1));
			expanded_nodes = 0;
		}
		
		public boolean isGoalState( Object state )
		{
			
			expanded_nodes++;
			if ( expanded_nodes > MAX_NODES + randommness)
				return true;
			Maze maze = (Maze) state;
			
			int num_coins = 0;
			for (int i = 0; i < Maze.WIDTH; i++) {
				for (int j = 0; j < Maze.HEIGHT; j++) {
					if (maze.what_is(new XYLocation(i,j)).get_type() == MazeElement.MONEY) {
						num_coins++;
					}
				}
			}
			if (num_coins < this.goal) {
				return true;
			}
			return false;
		}
		
		private int goal;
		
		private static final int MAX_NODES = 800;
		private int expanded_nodes;

	}
	

	private int randommness;
	public int TotalCoinsInMaze;
	private HeuristicFunction heuristic_function;
	private GoalTest goal_function;
	

	
}
