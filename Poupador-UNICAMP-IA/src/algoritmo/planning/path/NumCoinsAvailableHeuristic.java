package algoritmo.planning.path;

import algoritmo.*;

import java.util.List;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;
import algoritmo.environment.Maze;
import algoritmo.environment.element.MazeElement;
import algoritmo.environment.element.Saver;

/**
 * Heuristic: ManathanDistance between agent and goal
 * @author Gui
 *
 */
public final class NumCoinsAvailableHeuristic implements HeuristicFunction
{
	public NumCoinsAvailableHeuristic( MazeElement agent )
	{
		this.agent = agent;
	}
	
	@Override
	public double h( Object state )
	{
		Maze maze = (Maze) state;
		int num_coins = 0;
		for (int i = 0; i < Maze.WIDTH; i++) {
			for (int j = 0; j < Maze.HEIGHT; j++) {
				if (maze.what_is(new XYLocation(i,j)).get_type() == MazeElement.MONEY) {
					num_coins++;
				}
			}
		}
		return (double) num_coins;
		
	}
	
	private MazeElement agent;
	
	public static int getDistance(XYLocation a1, XYLocation a2)
	{
		return Math.abs(a1.x() - a2.x()) + Math.abs(a1.y() - a2.y());
	}
	
	/**
	 * gets the shortest distance between source and all the locations provided
	 * @param source
	 * @param locations
	 * @return
	 */
	public static int getShortest(XYLocation source, List<XYLocation> locations)
	{
		int response = 0;
		for(int i = 0; i < locations.size(); i++)
		{
			int newDistance = getDistance(source, locations.get(i));
			if(response == 0 || response > newDistance)
				response = newDistance;
		}
		return response;
	}
}
