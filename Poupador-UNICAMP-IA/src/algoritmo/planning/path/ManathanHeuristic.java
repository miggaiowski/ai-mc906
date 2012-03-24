package algoritmo.planning.path;

import java.util.List;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;
import algoritmo.environment.Maze;
import algoritmo.environment.element.MazeElement;

/**
 * Heuristic: ManathanDistance between agent and goal
 * @author Gui
 *
 */
public final class ManathanHeuristic implements HeuristicFunction
{
	public ManathanHeuristic( MazeElement agent, XYLocation goal )
	{
		this.agent = agent;
		this.goal = goal;
	}
	
	@Override
	public double h( Object state )
	{
		Maze maze = (Maze) state;
		XYLocation agent_pos = maze.where_is( agent );
		
		return (double) getDistance( agent_pos, goal, false );
		
	}
	
	private MazeElement agent;
	private XYLocation goal;

	public static int getDistance(XYLocation a1, XYLocation a2, boolean force)
	{
		int baseDist = Math.abs(a1.x() - a2.x()) + Math.abs(a1.y() - a2.y());
		
		if(force) return baseDist;
		
/*		switch(Maze.get_instance().what_is(a1).get_type())
		{
		case MazeElement.R1:
		case MazeElement.R2:
		case MazeElement.R3:
		case MazeElement.R4:
			return baseDist * 8;
		case MazeElement.POWER_UP:
			return baseDist / 3;
		}
		
		List<XYLocation> robbers = Maze.get_instance().where_are_all_robbers();
		if(robbers.size() > 0){
			XYLocation closest = getClosest(a1, robbers);
			int mult = Math.abs(a1.x() - closest.x()) + Math.abs(closest.y() - closest.y());
			switch(mult)
			{
			case 1: return baseDist * 4;
			case 2: return baseDist * 2;
			}
		}
	*/	
		return baseDist;
	}
	
	/**
	 * gets the shortest distance between source and all the locations provided
	 * @param source
	 * @param locations
	 * @return the shortest distance point
	 */
	public static int getShortest(XYLocation source, List<XYLocation> locations)
	{
		int response = Integer.MAX_VALUE;
		for (XYLocation loc : locations) {
			
			int newDistance = getDistance(source, loc, true);
			
			if( response > newDistance )
			{
				response = newDistance;
			}
		}
		
		return response;
	}
	
	/**
	 * gets the shortest distance between source and all the locations provided
	 * @param source
	 * @param locations
	 * @return the shortest distance point
	 */
	public static XYLocation getClosest(XYLocation source, List<XYLocation> locations)
	{
		int response = Integer.MAX_VALUE;
		XYLocation best = null;
		for (XYLocation loc : locations) {
			
			int newDistance = getDistance(source, loc, true);
			
			if( response > newDistance )
			{
				response = newDistance;
				best = new XYLocation(loc.x(),loc.y());
			}
		}
		
		return best;
	}
	
	public static int getClosestIndex(XYLocation source, List<XYLocation> locations)
	{
		int response = Integer.MAX_VALUE;
		int best = 0;
		int index = 0;
		for (XYLocation loc : locations) {
			
			int newDistance = getDistance(source, loc, true);
			if( response > newDistance )
			{
				response = newDistance;
				best = index;
			}
			index++;
		}
		
		return best;
	}
}
